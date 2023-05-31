package com.oliveryasuna.beanbag;

public class ObservableValue<T> extends AbstractObservableValue<T, ObservableValue<T>> {

  // Constructors
  //--------------------------------------------------

  public ObservableValue(final T value) {
    super(value);
  }

  // Methods
  //--------------------------------------------------

  @Override
  protected ObservableValue<T> getThis() {
    return this;
  }

}
