package com.oliveryasuna.beanbag;

public class ObservableDouble extends AbstractObservableValue<Double, ObservableDouble> {

  // Constructors
  //--------------------------------------------------

  public ObservableDouble(final double value) {
    super(value);
  }

  // Methods
  //--------------------------------------------------

  @Override
  protected ObservableDouble getThis() {
    return this;
  }

}
