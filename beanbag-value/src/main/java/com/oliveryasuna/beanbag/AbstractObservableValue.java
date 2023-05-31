package com.oliveryasuna.beanbag;

import com.oliveryasuna.beanbag.event.EventListener;
import com.oliveryasuna.beanbag.event.ValueChangedEvent;
import com.oliveryasuna.commons.language.pattern.Registration;

public abstract class AbstractObservableValue<T, SUB extends AbstractObservableValue<T, SUB>> extends Observable<T, SUB> {

  // Constructors
  //--------------------------------------------------

  public AbstractObservableValue(final T value) {
    super(value);
  }

  // Methods
  //--------------------------------------------------

  // Listeners
  //

  @Override
  public Registration addValueChangedListener(final EventListener<ValueChangedEvent<T, SUB>, T, SUB> listener) {
    return super.addValueChangedListener(listener);
  }

}
