package com.oliveryasuna.beanbag;

public class ObservableBoolean extends AbstractObservableValue<Boolean, ObservableBoolean> {

  // Constructors
  //--------------------------------------------------

  public ObservableBoolean(final boolean value) {
    super(value);
  }

  // Methods
  //--------------------------------------------------

  // Value
  //

  public boolean get() {
    return Boolean.TRUE.equals(getValue());
  }

  public void set(final boolean value) {
    setValue(value);
  }

  // Miscellaneous
  //

  @Override
  protected ObservableBoolean getThis() {
    return this;
  }

}
