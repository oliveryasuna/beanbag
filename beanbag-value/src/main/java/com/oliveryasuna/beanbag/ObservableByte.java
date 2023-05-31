package com.oliveryasuna.beanbag;

import java.util.Optional;

public class ObservableByte extends AbstractObservableValue<Byte, ObservableByte> {

  // Constructors
  //--------------------------------------------------

  public ObservableByte(final byte value) {
    super(value);
  }

  // Methods
  //--------------------------------------------------

  // Value
  //

  public byte get() {
    return Optional.ofNullable(getValue())
        .orElse((byte)0);
  }

  public void set(final byte value) {
    setValue(value);
  }

  // Miscellaneous
  //

  @Override
  protected ObservableByte getThis() {
    return this;
  }

}
