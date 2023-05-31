package com.oliveryasuna.beanbag;

import com.oliveryasuna.beanbag.event.Event;
import com.oliveryasuna.beanbag.event.EventListener;
import com.oliveryasuna.beanbag.event.ValueChangedEvent;
import com.oliveryasuna.commons.language.pattern.Registration;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Observable<T, SUB extends Observable<T, SUB>> {

  // Constructors
  //--------------------------------------------------

  public Observable(final T value) {
    super();

    this.value = value;
  }

  // Fields
  //--------------------------------------------------

  private T value;

  private final Map<Class<? extends Event<T, SUB>>, List<EventListener<?, T, SUB>>> listeners = new HashMap<>();

  // Methods
  //--------------------------------------------------

  // Listeners
  //

  protected Registration addValueChangedListener(final EventListener<ValueChangedEvent<T, SUB>, T, SUB> listener) {
    return addListener(ValueChangedEvent.class, listener);
  }

  protected <EVT extends Event<T, SUB>> List<EventListener<? extends EVT, T, SUB>> getListeners(final Class<? extends EVT> eventType) {
    final List<EventListener<?, T, SUB>> listeners = getListeners().get(eventType);

    if(listeners == null) {
      return new ArrayList<>(0);
    }

    final List<EventListener<? extends EVT, T, SUB>> result = new ArrayList<>(listeners.size());

    for(final EventListener<?, T, SUB> listener : listeners) {
      result.add((EventListener<? extends EVT, T, SUB>)listener);
    }

    return result;
  }

  protected <EVT extends Event<T, SUB>> Registration addListener(final Class<? extends EVT> eventType, final EventListener<? extends EVT, T, SUB> listener) {
    listeners.computeIfAbsent(eventType, ignored -> new ArrayList<>(1))
        .add(listener);

    return Registration.once(() -> removeListener(eventType, listener));
  }

  protected <EVT extends Event<T, SUB>> void removeListener(final Class<? extends EVT> eventType, final EventListener<? extends EVT, T, SUB> listener) {
    final List<EventListener<? extends EVT, T, SUB>> listeners = getListeners(eventType);

    if(listeners.isEmpty()) {
      throw new IllegalArgumentException("No listeners registered for event type: " + eventType);
    }

    listeners.remove(listener);
  }

  public boolean hasListener(final Class<? extends Event<T, SUB>> eventType) {
    return getListeners().containsKey(eventType);
  }

  protected <EVT extends Event<T, SUB>> void fireEvent(final EVT event) {
    final List<EventListener<?, T, SUB>> listeners = getListeners().get(event.getClass());

    if(listeners == null) {
      return;
    }

    for(final EventListener<?, T, SUB> listener : listeners) {
      ((EventListener)listener).onEvent(event);
    }
  }

  // Miscellaneous
  //

  protected abstract SUB getThis();

  // Getters/setters
  //--------------------------------------------------

  protected T getValue() {
    return value;
  }

  protected void setValue(final T value) {
    final T oldValue = this.value;

    this.value = value;

    fireEvent(new ValueChangedEvent<>(getThis(), value, oldValue));
  }

  protected Map<Class<? extends Event<T, SUB>>, List<EventListener<?, T, SUB>>> getListeners() {
    return listeners;
  }

  // Object methods
  //--------------------------------------------------

  @Override
  public boolean equals(final Object other) {
    if(this == other) return true;
    if(other == null || getClass() != other.getClass()) return false;

    final Observable<?, ?> otherCasted = (Observable<?, ?>)other;

    return new EqualsBuilder()
        .append(getValue(), otherCasted.getValue())
        .append(getListeners(), otherCasted.getListeners())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(getValue())
        .append(getListeners())
        .toHashCode();
  }

}
