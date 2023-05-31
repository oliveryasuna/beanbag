package com.oliveryasuna.beanbag;

import java.util.Set;

public class ObservableSet<T> extends AbstractObservableSet<T, Set<T>, ObservableSet<T>> {

  // Constructors
  //--------------------------------------------------

  public ObservableSet(final Set<T> set) {
    super(set);
  }

  // Methods
  //--------------------------------------------------

  @Override
  protected ObservableSet<T> getThis() {
    return this;
  }

}
