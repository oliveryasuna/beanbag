package com.oliveryasuna.beanbag.event;

import com.oliveryasuna.beanbag.AbstractObservableCollection;

import java.util.Collection;

public class CollectionEvent<T, COL extends Collection<T>, SRC extends AbstractObservableCollection<T, COL, SRC>> extends Event<COL, SRC> {

  // Constructors
  //--------------------------------------------------

  public CollectionEvent(final SRC source) {
    super(source);
  }

}
