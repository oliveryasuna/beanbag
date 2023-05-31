package com.oliveryasuna.beanbag;

public class ObservableInt extends AbstractObservableValue<Integer, ObservableInt> {

  // Constructors
  //--------------------------------------------------

  public ObservableInt(final int value) {
    super(value);
  }

  // Methods
  //--------------------------------------------------

  @Override
  protected ObservableInt getThis() {
    return this;
  }

}
