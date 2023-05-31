package com.oliveryasuna.beanbag;

public class ObservableShort extends AbstractObservableValue<Short, ObservableShort> {

  // Constructors
  //--------------------------------------------------

  public ObservableShort(final short value) {
    super(value);
  }

  // Methods
  //--------------------------------------------------

  @Override
  protected ObservableShort getThis() {
    return this;
  }

}
