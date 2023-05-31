package com.oliveryasuna.beanbag.event;

import com.oliveryasuna.beanbag.Observable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Event<T, SRC extends Observable<T, SRC>> {

  // Constructors
  //--------------------------------------------------

  public Event(final SRC source) {
    super();

    this.source = source;
  }

  // Fields
  //--------------------------------------------------

  private final SRC source;

  // Getters/setters
  //--------------------------------------------------

  public SRC getSource() {
    return source;
  }

  // Object methods
  //--------------------------------------------------

  @Override
  public boolean equals(final Object other) {
    if(this == other) return true;
    if(other == null || getClass() != other.getClass()) return false;

    final Event<?, ?> otherCasted = (Event<?, ?>)other;

    return new EqualsBuilder()
        .append(getSource(), otherCasted.getSource())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(getSource())
        .toHashCode();
  }

}
