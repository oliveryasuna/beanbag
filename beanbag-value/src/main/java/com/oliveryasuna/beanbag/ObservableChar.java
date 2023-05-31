package com.oliveryasuna.beanbag;

public class ObservableChar extends AbstractObservableValue<Character, ObservableChar> {

  // Constructors
  //--------------------------------------------------

  public ObservableChar(final char value) {
    super(value);
  }

  // Methods
  //--------------------------------------------------

  @Override
  protected ObservableChar getThis() {
    return this;
  }

}
