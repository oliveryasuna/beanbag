package com.oliveryasuna.beanbag.event;

import com.oliveryasuna.beanbag.AbstractObservableCollection;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;

public class IndexedElementRemovedEvent<T, COL extends Collection<T>, SRC extends AbstractObservableCollection<T, COL, SRC>>
    extends CollectionEvent<T, COL, SRC> {

  // Constructors
  //--------------------------------------------------

  public IndexedElementRemovedEvent(final SRC source, final int index, final T element) {
    super(source);

    this.index = index;
    this.element = element;
  }

  // Fields
  //--------------------------------------------------

  private final int index;

  private final T element;

  // Getters/setters
  //--------------------------------------------------

  public int getIndex() {
    return index;
  }

  public T getElement() {
    return element;
  }

  // Object methods
  //--------------------------------------------------

  @Override
  public boolean equals(final Object other) {
    if(this == other) return true;
    if(other == null || getClass() != other.getClass()) return false;

    final IndexedElementRemovedEvent<?, ?, ?> otherCasted = (IndexedElementRemovedEvent<?, ?, ?>)other;

    return new EqualsBuilder()
        .appendSuper(super.equals(other))
        .append(getIndex(), otherCasted.getIndex())
        .append(getElement(), otherCasted.getElement())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(getIndex())
        .append(getElement())
        .toHashCode();
  }

}
