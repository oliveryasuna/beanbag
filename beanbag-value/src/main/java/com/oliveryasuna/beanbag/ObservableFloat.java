package com.oliveryasuna.beanbag;

public class ObservableFloat extends AbstractObservableValue<Float, ObservableFloat> {

  // Constructors
  //--------------------------------------------------

  public ObservableFloat(final float value) {
    super(value);
  }

  // Methods
  //--------------------------------------------------

  @Override
  protected ObservableFloat getThis() {
    return this;
  }

}
