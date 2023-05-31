package com.oliveryasuna.beanbag.event;

import com.oliveryasuna.beanbag.Observable;

@FunctionalInterface
public interface EventListener<EVT extends Event<T, SRC>, T, SRC extends Observable<T, SRC>> {

  // Methods
  //--------------------------------------------------

  void onEvent(EVT event);

}
