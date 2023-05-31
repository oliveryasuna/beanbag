package com.oliveryasuna.beanbag;

public class ObservableLong extends AbstractObservableValue<Long, ObservableLong> {

  // Constructors
  //--------------------------------------------------

  public ObservableLong(final long value) {
    super(value);
  }

  // Methods
  //--------------------------------------------------

  @Override
  protected ObservableLong getThis() {
    return this;
  }

}
