package com.oliveryasuna.beanbag.event;

import com.oliveryasuna.beanbag.AbstractObservableCollection;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;

public class ElementsRemovedEvent<T, COL extends Collection<T>, SRC extends AbstractObservableCollection<T, COL, SRC>> extends CollectionEvent<T, COL, SRC> {

  // Constructors
  //--------------------------------------------------

  public ElementsRemovedEvent(final SRC source, final Collection<T> elements) {
    super(source);

    this.elements = elements;
  }

  // Fields
  //--------------------------------------------------

  private final Collection<T> elements;

  // Getters/setters
  //--------------------------------------------------

  public Collection<T> getElements() {
    return elements;
  }

  // Object methods
  //--------------------------------------------------

  @Override
  public boolean equals(final Object other) {
    if(this == other) return true;
    if(other == null || getClass() != other.getClass()) return false;

    final ElementsRemovedEvent<?, ?, ?> otherCasted = (ElementsRemovedEvent<?, ?, ?>)other;

    return new EqualsBuilder()
        .appendSuper(super.equals(other))
        .append(getElements(), otherCasted.getElements())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(getElements())
        .toHashCode();
  }

}
