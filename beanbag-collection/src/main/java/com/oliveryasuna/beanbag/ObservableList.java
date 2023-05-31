package com.oliveryasuna.beanbag;

import java.util.List;

public class ObservableList<T> extends AbstractObservableList<T, List<T>, ObservableList<T>> {

  // Constructors
  //--------------------------------------------------

  public ObservableList(final List<T> list) {
    super(list);
  }

  // Methods
  //--------------------------------------------------

  @Override
  protected ObservableList<T> getThis() {
    return this;
  }

}
