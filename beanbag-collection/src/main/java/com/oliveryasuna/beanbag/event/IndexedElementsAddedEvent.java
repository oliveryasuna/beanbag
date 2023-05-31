package com.oliveryasuna.beanbag.event;

import com.oliveryasuna.beanbag.AbstractObservableCollection;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.Map;

public class IndexedElementsAddedEvent<T, COL extends Collection<T>, SRC extends AbstractObservableCollection<T, COL, SRC>>
    extends CollectionEvent<T, COL, SRC> {

  // Constructors
  //--------------------------------------------------

  public IndexedElementsAddedEvent(final SRC source, final Map<Integer, T> indexElementMap) {
    super(source);

    this.indexElementMap = indexElementMap;
  }

  // Fields
  //--------------------------------------------------

  private final Map<Integer, T> indexElementMap;

  // Getters/setters
  //--------------------------------------------------

  public Map<Integer, T> getIndexElementMap() {
    return indexElementMap;
  }

  // Object methods
  //--------------------------------------------------

  @Override
  public boolean equals(final Object other) {
    if(this == other) return true;
    if(other == null || getClass() != other.getClass()) return false;

    final IndexedElementsAddedEvent<?, ?, ?> otherCasted = (IndexedElementsAddedEvent<?, ?, ?>)other;

    return new EqualsBuilder()
        .appendSuper(super.equals(other))
        .append(getIndexElementMap(), otherCasted.getIndexElementMap())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(getIndexElementMap())
        .toHashCode();
  }

}
