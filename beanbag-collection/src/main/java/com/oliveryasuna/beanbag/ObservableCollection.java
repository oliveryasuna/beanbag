package com.oliveryasuna.beanbag;

import java.util.Collection;

public class ObservableCollection<T> extends AbstractObservableCollection<T, Collection<T>, ObservableCollection<T>> {

  // Constructors
  //--------------------------------------------------

  public ObservableCollection(final Collection<T> collection) {
    super(collection);
  }

  // Methods
  //--------------------------------------------------

  @Override
  protected ObservableCollection<T> getThis() {
    return this;
  }

}
