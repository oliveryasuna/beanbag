package com.oliveryasuna.beanbag;

import java.util.Set;

public abstract class AbstractObservableSet<T, SET extends Set<T>, SUB extends AbstractObservableSet<T, SET, SUB>>
    extends AbstractObservableCollection<T, SET, SUB>
    implements Set<T> {

  // Constructors
  //--------------------------------------------------

  public AbstractObservableSet(final SET set) {
    super(set);
  }

  // Methods
  //--------------------------------------------------

  // Value
  //

  protected SET getSet() {
    return getCollection();
  }

  protected void setSet(final SET set) {
    setCollection(set);
  }

}
