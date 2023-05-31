package com.oliveryasuna.beanbag;

import com.oliveryasuna.beanbag.event.EventListener;
import com.oliveryasuna.beanbag.event.*;
import com.oliveryasuna.commons.language.pattern.Registration;
import com.oliveryasuna.commons.language.pattern.decorator.IteratorDecorator;

import java.util.*;
import java.util.function.Predicate;

public abstract class AbstractObservableCollection<T, COL extends Collection<T>, SUB extends AbstractObservableCollection<T, COL, SUB>>
    extends Observable<COL, SUB>
    implements Collection<T> {

  // Constructors
  //--------------------------------------------------

  public AbstractObservableCollection(final COL collection) {
    super(collection);
  }

  // Methods
  //--------------------------------------------------

  // Collection API
  //

  @Override
  public boolean add(final T t) {
    final boolean modified = getCollection().add(t);

    if(modified) {
      fireEvent(new ElementAddedEvent<>(getThis(), t));
      fireEvent(new ElementsAddedEvent<>(getThis(), new LinkedHashSet<>(Collections.singleton(t))));
    }

    return modified;
  }

  @Override
  public boolean addAll(final Collection<? extends T> c) {
    final COL collection = getCollection();

    final Collection<T> copy = Collections.unmodifiableCollection(new LinkedHashSet<>(collection));

    final boolean modified = collection.addAll(c);

    if(modified) {
      final Collection<T> added = new HashSet<>(collection);

      added.removeAll(copy);

      for(final T addedElement : added) {
        fireEvent(new ElementAddedEvent<>(getThis(), addedElement));
      }

      fireEvent(new ElementsAddedEvent<>(getThis(), added));
    }

    return modified;
  }

  @Override
  public boolean remove(final Object o) {
    final boolean modified = getCollection().remove(o);

    if(modified) {
      final T removedElement = (T)o;

      fireEvent(new ElementRemovedEvent<>(getThis(), removedElement));
      fireEvent(new ElementsRemovedEvent<>(getThis(), new LinkedHashSet<>(Collections.singleton(removedElement))));
    }

    return modified;
  }

  @Override
  public boolean removeAll(final Collection<?> c) {
    final COL collection = getCollection();

    final Collection<T> copy = new HashSet<>(collection);

    final boolean modified = collection.removeAll(c);

    if(modified) {
      copy.removeAll(collection);

      for(final T removedElement : copy) {
        fireEvent(new ElementRemovedEvent<>(getThis(), removedElement));
      }

      fireEvent(new ElementsRemovedEvent<>(getThis(), copy));
    }

    return modified;
  }

  @Override
  public boolean removeIf(final Predicate<? super T> filter) {
    final COL collection = getCollection();

    final Collection<T> copy = new LinkedHashSet<>(collection);

    final boolean modified = collection.removeIf(filter);

    if(modified) {
      copy.removeAll(collection);

      for(final T removedElement : copy) {
        fireEvent(new ElementRemovedEvent<>(getThis(), removedElement));
      }

      fireEvent(new ElementsRemovedEvent<>(getThis(), copy));
    }

    return modified;
  }

  @Override
  public boolean retainAll(final Collection<?> c) {
    final COL collection = getCollection();

    final Collection<T> copy = new ArrayList<>(collection);

    final boolean modified = collection.retainAll(c);

    if(modified) {
      copy.removeAll(collection);

      for(final T removedElement : copy) {
        fireEvent(new ElementRemovedEvent<>(getThis(), removedElement));
      }

      fireEvent(new ElementsRemovedEvent<>(getThis(), copy));
    }

    return modified;
  }

  @Override
  public void clear() {
    final COL collection = getCollection();

    final Collection<T> copy = new LinkedHashSet<>(collection);

    collection.clear();

    if(!copy.isEmpty()) {
      for(final T removedElement : copy) {
        fireEvent(new ElementRemovedEvent<>(getThis(), removedElement));
      }

      fireEvent(new ElementsRemovedEvent<>(getThis(), copy));
    }
  }

  @Override
  public boolean contains(final Object o) {
    return getCollection().contains(o);
  }

  @Override
  public boolean containsAll(final Collection<?> c) {
    return getCollection().containsAll(c);
  }

  @Override
  public int size() {
    return getCollection().size();
  }

  @Override
  public boolean isEmpty() {
    return getCollection().isEmpty();
  }

  @Override
  public Iterator<T> iterator() {
    return new ObservableIterator();
  }

  @Override
  public Object[] toArray() {
    return getCollection().toArray();
  }

  @Override
  public <T1> T1[] toArray(final T1[] a) {
    return getCollection().toArray(a);
  }

  // Listeners
  //

  public Registration addElementAddedListener(final EventListener<ElementAddedEvent<T, COL, SUB>, COL, SUB> listener) {
    return addListener(ElementAddedEvent.class, listener);
  }

  public Registration addElementsAddedListener(final EventListener<ElementsAddedEvent<T, COL, SUB>, COL, SUB> listener) {
    return addListener(ElementsAddedEvent.class, listener);
  }

  public Registration addElementRemovedListener(final EventListener<ElementRemovedEvent<T, COL, SUB>, COL, SUB> listener) {
    return addListener(ElementRemovedEvent.class, listener);
  }

  public Registration addElementsRemovedListener(final EventListener<ElementsRemovedEvent<T, COL, SUB>, COL, SUB> listener) {
    return addListener(ElementsRemovedEvent.class, listener);
  }

  // Value
  //

  protected COL getCollection() {
    return getValue();
  }

  protected void setCollection(final COL collection) {
    setValue(collection);
  }

  // Nested
  //--------------------------------------------------

  protected class ObservableIterator extends IteratorDecorator<T> {

    // Constructors
    //--------------------------------------------------

    protected ObservableIterator(final Iterator<T> iterator) {
      super(iterator);
    }

    private ObservableIterator() {
      this(AbstractObservableCollection.this.getCollection().iterator());
    }

    // Fields
    //--------------------------------------------------

    private T lastElement;

    // Methods
    //--------------------------------------------------

    // Iterator API
    //

    @Override
    public T next() {
      final T next = super.next();

      setLastElement(next);

      return next;
    }

    @Override
    public void remove() {
      super.remove();

      final T lastElement = getLastElement();

      AbstractObservableCollection.this.fireEvent(new ElementRemovedEvent<>(
          AbstractObservableCollection.this.getThis(),
          lastElement
      ));
      AbstractObservableCollection.this.fireEvent(new ElementsRemovedEvent<>(
          AbstractObservableCollection.this.getThis(),
          new LinkedHashSet<>(Collections.singleton(lastElement))
      ));
    }

    // Getters/setters
    //--------------------------------------------------

    protected T getLastElement() {
      return lastElement;
    }

    protected void setLastElement(final T lastElement) {
      this.lastElement = lastElement;
    }

  }

}
