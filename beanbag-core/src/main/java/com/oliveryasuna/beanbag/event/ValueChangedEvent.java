package com.oliveryasuna.beanbag.event;

import com.oliveryasuna.beanbag.Observable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ValueChangedEvent<T, SRC extends Observable<T, SRC>> extends Event<T, SRC> {

  // Constructors
  //--------------------------------------------------

  public ValueChangedEvent(final SRC source, final T newValue, final T oldValue) {
    super(source);

    this.newValue = newValue;
    this.oldValue = oldValue;
  }

  // Fields
  //--------------------------------------------------

  private final T newValue;

  private final T oldValue;

  // Getters/setters
  //--------------------------------------------------

  public T getNewValue() {
    return newValue;
  }

  public T getOldValue() {
    return oldValue;
  }

  // Object methods
  //--------------------------------------------------

  @Override
  public boolean equals(final Object other) {
    if(this == other) return true;
    if(other == null || getClass() != other.getClass()) return false;

    final ValueChangedEvent<?, ?> otherCasted = (ValueChangedEvent<?, ?>)other;

    return new EqualsBuilder()
        .appendSuper(super.equals(other))
        .append(getNewValue(), otherCasted.getNewValue())
        .append(getOldValue(), otherCasted.getOldValue())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(getNewValue())
        .append(getOldValue())
        .toHashCode();
  }

}
