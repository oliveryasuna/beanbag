package com.oliveryasuna.beanbag.event;

import com.oliveryasuna.beanbag.AbstractObservableCollection;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;

public class IndexedElementChangedEvent<T, COL extends Collection<T>, SRC extends AbstractObservableCollection<T, COL, SRC>>
    extends CollectionEvent<T, COL, SRC> {

  // Constructors
  //--------------------------------------------------

  public IndexedElementChangedEvent(final SRC source, final int index, final T newElement, final T oldElement) {
    super(source);

    this.index = index;
    this.newElement = newElement;
    this.oldElement = oldElement;
  }

  // Fields
  //--------------------------------------------------

  private final int index;

  private final T newElement;

  private final T oldElement;

  // Getters/setters
  //--------------------------------------------------

  public int getIndex() {
    return index;
  }

  public T getNewElement() {
    return newElement;
  }

  public T getOldElement() {
    return oldElement;
  }

  // Object methods
  //--------------------------------------------------

  @Override
  public boolean equals(final Object other) {
    if(this == other) return true;
    if(other == null || getClass() != other.getClass()) return false;

    final IndexedElementChangedEvent<?, ?, ?> otherCasted = (IndexedElementChangedEvent<?, ?, ?>)other;

    return new EqualsBuilder()
        .appendSuper(super.equals(other))
        .append(getIndex(), otherCasted.getIndex())
        .append(getNewElement(), otherCasted.getNewElement())
        .append(getOldElement(), otherCasted.getOldElement())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(getIndex())
        .append(getNewElement())
        .append(getOldElement())
        .toHashCode();
  }

}
