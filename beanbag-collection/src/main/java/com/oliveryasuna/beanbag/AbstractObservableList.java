package com.oliveryasuna.beanbag;

import com.oliveryasuna.beanbag.event.EventListener;
import com.oliveryasuna.beanbag.event.*;
import com.oliveryasuna.commons.language.pattern.Registration;
import com.oliveryasuna.commons.language.pattern.decorator.ListIteratorDecorator;
import org.apache.commons.lang3.NotImplementedException;

import java.util.*;
import java.util.function.Predicate;

public abstract class AbstractObservableList<T, LST extends List<T>, SUB extends AbstractObservableList<T, LST, SUB>>
    extends AbstractObservableCollection<T, LST, SUB>
    implements List<T> {

  // Constructors
  //--------------------------------------------------

  public AbstractObservableList(final LST list) {
    super(list);
  }

  // Methods
  //--------------------------------------------------

  // Collection API
  //

  @Override
  public boolean add(final T t) {
    final LST list = getList();

    final int index = list.size();
    final boolean modified = list.add(t);

    if(modified) {
      fireEvent(new ElementAddedEvent<>(getThis(), t));
      fireEvent(new IndexedElementAddedEvent<>(getThis(), index, t));
      fireEvent(new ElementsAddedEvent<>(getThis(), new ArrayList<>(Collections.singleton(t))));
      fireEvent(new IndexedElementsAddedEvent<>(getThis(), new LinkedHashMap<>(Collections.singletonMap(index, t))));
    }

    return modified;
  }

  @Override
  public boolean addAll(final Collection<? extends T> c) {
    final LST list = getList();

    int index = list.size();
    final boolean modified = list.addAll(c);

    if(modified) {
      final Map<Integer, T> indexElementMap = new LinkedHashMap<>();

      for(final T addedElement : c) {
        indexElementMap.put(index, addedElement);

        fireEvent(new ElementAddedEvent<>(getThis(), addedElement));
        fireEvent(new IndexedElementAddedEvent<>(getThis(), index, addedElement));

        index++;
      }

      fireEvent(new ElementsAddedEvent<>(getThis(), new ArrayList<>(c)));
      fireEvent(new IndexedElementsAddedEvent<>(getThis(), indexElementMap));
    }

    return modified;
  }

  @Override
  public boolean remove(final Object o) {
    final LST list = getList();

    final int index = list.indexOf(o);
    final boolean modified = list.remove(o);

    if(modified) {
      final T removedElement = (T)o;

      fireEvent(new ElementRemovedEvent<>(getThis(), removedElement));
      fireEvent(new IndexedElementRemovedEvent<>(getThis(), index, removedElement));
      fireEvent(new ElementsRemovedEvent<>(getThis(), new ArrayList<>(Collections.singleton(removedElement))));
      fireEvent(new IndexedElementsRemovedEvent<>(getThis(), new LinkedHashMap<>(Collections.singletonMap(index, removedElement))));
    }

    return modified;
  }

  @Override
  public boolean removeAll(final Collection<?> c) {
    final LST list = getList();

    final List<T> copy = new ArrayList<>(list);

    final boolean modified = list.removeAll(c);

    if(modified) {
      final Map<Integer, T> indexElementMap = new LinkedHashMap<>();

      for(final Object rawRemovedElement : c) {
        final int removedElementIndex = copy.indexOf(rawRemovedElement);

        if(removedElementIndex == -1) {
          continue;
        }

        copy.remove(removedElementIndex);

        final T removedElement = (T)rawRemovedElement;

        indexElementMap.put(removedElementIndex, removedElement);

        fireEvent(new ElementRemovedEvent<>(getThis(), removedElement));
        fireEvent(new IndexedElementRemovedEvent<>(getThis(), removedElementIndex, removedElement));
      }

      fireEvent(new ElementsRemovedEvent<>(getThis(), new ArrayList<>(indexElementMap.values())));
      fireEvent(new IndexedElementsRemovedEvent<>(getThis(), indexElementMap));
    }

    return modified;
  }

  @Override
  public boolean removeIf(final Predicate<? super T> filter) {
    final LST list = getList();

    final List<T> copy = new ArrayList<>(list);

    final boolean modified = list.removeIf(filter);

    if(modified) {
      final Map<Integer, T> indexElementMap = new LinkedHashMap<>();

      for(final ListIterator<T> iterator = copy.listIterator(); iterator.hasNext(); ) {
        final int index = iterator.nextIndex();
        final T element = iterator.next();

        if(filter.test(element)) {
          iterator.remove();

          indexElementMap.put(index, element);

          fireEvent(new ElementRemovedEvent<>(getThis(), element));
          fireEvent(new IndexedElementRemovedEvent<>(getThis(), index, element));
        }
      }

      fireEvent(new ElementsRemovedEvent<>(getThis(), new ArrayList<>(indexElementMap.values())));
      fireEvent(new IndexedElementsRemovedEvent<>(getThis(), indexElementMap));
    }

    return modified;
  }

  @Override
  public boolean retainAll(final Collection<?> c) {
    final LST list = getList();

    final List<T> copy = Collections.unmodifiableList(new ArrayList<>(list));

    final boolean modified = list.retainAll(c);

    if(modified) {
      final Map<Integer, T> indexElementMap = new LinkedHashMap<>();

      for(int i = 0; i < copy.size(); i++) {
        final T element = copy.get(i);

        if(!c.contains(element)) {
          indexElementMap.put(i, element);

          fireEvent(new ElementRemovedEvent<>(getThis(), element));
          fireEvent(new IndexedElementRemovedEvent<>(getThis(), i, element));
        }
      }

      fireEvent(new ElementsRemovedEvent<>(getThis(), new ArrayList<>(indexElementMap.values())));
      fireEvent(new IndexedElementsRemovedEvent<>(getThis(), indexElementMap));
    }

    return modified;
  }

  @Override
  public void clear() {
    final LST list = getList();

    final List<T> copy = Collections.unmodifiableList(new ArrayList<>(list));

    list.clear();

    final Map<Integer, T> indexElementMap = new LinkedHashMap<>();

    for(int i = 0; i < copy.size(); i++) {
      final T element = copy.get(i);

      indexElementMap.put(i, element);

      fireEvent(new ElementRemovedEvent<>(getThis(), element));
      fireEvent(new IndexedElementRemovedEvent<>(getThis(), i, element));
    }

    fireEvent(new ElementsRemovedEvent<>(getThis(), new ArrayList<>(indexElementMap.values())));
    fireEvent(new IndexedElementsRemovedEvent<>(getThis(), indexElementMap));
  }

  @Override
  public Iterator<T> iterator() {
    return new ObservableIterator();
  }

  // List API
  //

  @Override
  public T get(final int index) {
    return getList().get(index);
  }

  @Override
  public T set(final int index, final T element) {
    final T previousElement = getList().set(index, element);

    fireEvent(new IndexedElementChangedEvent<>(getThis(), index, element, previousElement));

    return previousElement;
  }

  @Override
  public void add(final int index, final T element) {
    getList().add(index, element);

    fireEvent(new ElementAddedEvent<>(getThis(), element));
    fireEvent(new IndexedElementAddedEvent<>(getThis(), index, element));
    fireEvent(new ElementsAddedEvent<>(getThis(), new LinkedHashSet<>(Collections.singleton(element))));
    fireEvent(new IndexedElementsAddedEvent<>(getThis(), new LinkedHashMap<>(Collections.singletonMap(index, element))));
  }

  @Override
  public boolean addAll(int index, final Collection<? extends T> c) {
    final boolean modified = getList().addAll(index, c);

    if(modified) {
      final Map<Integer, T> indexElementMap = new LinkedHashMap<>();

      for(final T addedElement : c) {
        indexElementMap.put(index, addedElement);

        fireEvent(new ElementAddedEvent<>(getThis(), addedElement));
        fireEvent(new IndexedElementAddedEvent<>(getThis(), index, addedElement));

        index++;
      }

      fireEvent(new ElementsAddedEvent<>(getThis(), new LinkedHashSet<>(c)));
      fireEvent(new IndexedElementsAddedEvent<>(getThis(), indexElementMap));
    }

    return modified;
  }

  @Override
  public T remove(final int index) {
    final T removedElement = getList().remove(index);

    fireEvent(new ElementRemovedEvent<>(getThis(), removedElement));
    fireEvent(new IndexedElementRemovedEvent<>(getThis(), index, removedElement));
    fireEvent(new ElementsRemovedEvent<>(getThis(), new LinkedHashSet<>(Collections.singleton(removedElement))));
    fireEvent(new IndexedElementsRemovedEvent<>(getThis(), new LinkedHashMap<>(Collections.singletonMap(index, removedElement))));

    return removedElement;
  }

  @Override
  public int indexOf(final Object o) {
    return getList().indexOf(o);
  }

  @Override
  public int lastIndexOf(final Object o) {
    return getList().lastIndexOf(o);
  }

  @Override
  public ListIterator<T> listIterator() {
    return new ObservableListIterator();
  }

  @Override
  public ListIterator<T> listIterator(final int index) {
    return new ObservableListIterator(index);
  }

  @Override
  public List<T> subList(final int fromIndex, final int toIndex) {
    // TODO: Implement.
    throw new NotImplementedException();
  }

  // Listeners
  //

  public Registration addIndexedElementAddedListener(final EventListener<IndexedElementAddedEvent<T, LST, SUB>, LST, SUB> listener) {
    return addListener(IndexedElementAddedEvent.class, listener);
  }

  public Registration addIndexedElementRemovedListener(final EventListener<IndexedElementRemovedEvent<T, LST, SUB>, LST, SUB> listener) {
    return addListener(IndexedElementRemovedEvent.class, listener);
  }

  public Registration addIndexedElementChangedListener(final EventListener<IndexedElementChangedEvent<T, LST, SUB>, LST, SUB> listener) {
    return addListener(IndexedElementChangedEvent.class, listener);
  }

  public Registration addIndexedElementsAddedListener(final EventListener<IndexedElementsAddedEvent<T, LST, SUB>, LST, SUB> listener) {
    return addListener(IndexedElementsAddedEvent.class, listener);
  }

  public Registration addIndexedElementsRemovedListener(final EventListener<IndexedElementsRemovedEvent<T, LST, SUB>, LST, SUB> listener) {
    return addListener(IndexedElementsRemovedEvent.class, listener);
  }

  // Value
  //

  protected LST getList() {
    return getCollection();
  }

  protected void setList(final LST list) {
    setCollection(list);
  }

  // Nested
  //--------------------------------------------------

  protected class ObservableIterator extends AbstractObservableCollection<T, LST, SUB>.ObservableIterator {

    // Constructors
    //--------------------------------------------------

    protected ObservableIterator(final Iterator<T> iterator) {
      super(iterator);
    }

    private ObservableIterator() {
      this(AbstractObservableList.this.getList().iterator());
    }

    // Fields
    //--------------------------------------------------

    private int lastIndex = -1;

    // Methods
    //--------------------------------------------------

    // Iterator API
    //

    @Override
    public T next() {
      final T next = super.next();

      setLastIndex(getLastIndex() + 1);
      setLastElement(next);

      return next;
    }

    @Override
    public void remove() {
      super.remove();

      final int lastIndex = getLastIndex();
      final T lastElement = getLastElement();

      AbstractObservableList.this.fireEvent(new IndexedElementRemovedEvent<>(
          AbstractObservableList.this.getThis(),
          lastIndex,
          lastElement
      ));
      AbstractObservableList.this.fireEvent(new IndexedElementsRemovedEvent<>(
          AbstractObservableList.this.getThis(),
          new LinkedHashMap<>(Collections.singletonMap(lastIndex, lastElement))
      ));
    }

    // Getters/setters
    //--------------------------------------------------

    protected int getLastIndex() {
      return lastIndex;
    }

    protected void setLastIndex(final int lastIndex) {
      this.lastIndex = lastIndex;
    }

  }

  protected class ObservableListIterator extends ListIteratorDecorator<T> {

    // Constructors
    //--------------------------------------------------

    protected ObservableListIterator(final ListIterator<T> iterator) {
      super(iterator);
    }

    private ObservableListIterator() {
      this(AbstractObservableList.this.getList().listIterator());
    }

    private ObservableListIterator(final int index) {
      this(AbstractObservableList.this.getList().listIterator(index));
    }

    // Fields
    //--------------------------------------------------

    private int lastIndex = -1;

    private T lastElement;

    // Methods
    //--------------------------------------------------

    // Iterator API
    //

    @Override
    public T next() {
      final T next = super.next();

      setLastIndex(getLastIndex() + 1);
      setLastElement(next);

      return next;
    }

    @Override
    public void remove() {
      super.remove();

      final int lastIndex = getLastIndex();
      final T lastElement = getLastElement();

      AbstractObservableList.this.fireEvent(new IndexedElementRemovedEvent<>(
          AbstractObservableList.this.getThis(),
          lastIndex,
          lastElement
      ));
      AbstractObservableList.this.fireEvent(new IndexedElementsRemovedEvent<>(
          AbstractObservableList.this.getThis(),
          new LinkedHashMap<>(Collections.singletonMap(lastIndex, lastElement))
      ));
    }

    // ListIterator API
    //

    @Override
    public T previous() {
      final T previous = super.previous();

      setLastIndex(getLastIndex() - 1);
      setLastElement(previous);

      return previous;
    }

    @Override
    public void set(final T t) {
      super.set(t);

      final int lastIndex = getLastIndex();
      final T lastElement = getLastElement();

      AbstractObservableList.this.fireEvent(new IndexedElementChangedEvent<>(
          AbstractObservableList.this.getThis(),
          lastIndex,
          t,
          lastElement
      ));
    }

    @Override
    public void add(final T t) {
      super.add(t);

      final int lastIndex = getLastIndex();

      AbstractObservableList.this.fireEvent(new ElementAddedEvent<>(
          AbstractObservableList.this.getThis(),
          t
      ));
      AbstractObservableList.this.fireEvent(new IndexedElementAddedEvent<>(
          AbstractObservableList.this.getThis(),
          lastIndex,
          t
      ));
      AbstractObservableList.this.fireEvent(new ElementsAddedEvent<>(
          AbstractObservableList.this.getThis(),
          new LinkedHashSet<>(Collections.singleton(t))
      ));
      AbstractObservableList.this.fireEvent(new IndexedElementsAddedEvent<>(
          AbstractObservableList.this.getThis(),
          new LinkedHashMap<>(Collections.singletonMap(lastIndex, t))
      ));
    }

    // Getters/setters
    //--------------------------------------------------

    protected int getLastIndex() {
      return lastIndex;
    }

    protected void setLastIndex(final int lastIndex) {
      this.lastIndex = lastIndex;
    }

    protected T getLastElement() {
      return lastElement;
    }

    protected void setLastElement(final T lastElement) {
      this.lastElement = lastElement;
    }

  }

}
