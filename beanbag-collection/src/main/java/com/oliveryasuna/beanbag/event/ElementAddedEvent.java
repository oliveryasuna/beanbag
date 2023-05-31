package com.oliveryasuna.beanbag.event;

import com.oliveryasuna.beanbag.AbstractObservableCollection;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;

public class ElementAddedEvent<T, COL extends Collection<T>, SRC extends AbstractObservableCollection<T, COL, SRC>> extends CollectionEvent<T, COL, SRC> {

  // Constructors
  //--------------------------------------------------

  public ElementAddedEvent(final SRC source, final T element) {
    super(source);

    this.element = element;
  }

  // Fields
  //--------------------------------------------------

  private final T element;

  // Getters/setters
  //--------------------------------------------------

  public T getElement() {
    return element;
  }

  // Object methods
  //--------------------------------------------------

  @Override
  public boolean equals(final Object other) {
    if(this == other) return true;
    if(other == null || getClass() != other.getClass()) return false;

    final ElementAddedEvent<?, ?, ?> otherCasted = (ElementAddedEvent<?, ?, ?>)other;

    return new EqualsBuilder()
        .appendSuper(super.equals(other))
        .append(getElement(), otherCasted.getElement())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(getElement())
        .toHashCode();
  }

}
