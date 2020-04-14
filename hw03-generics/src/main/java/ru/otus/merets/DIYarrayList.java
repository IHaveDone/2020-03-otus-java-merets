package ru.otus.merets;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class DIYarrayList<E> implements List<E> {
    private static final int DEFAULT_EXTENDING_STEP = 10;
    private static final int DEFAULT_CAPACITY = 10;

    private Object[] internalArray;
    private int size;
    private int capacity;

    public DIYarrayList() {
        this.init();
    }

    private void init() {
        this.capacity = DIYarrayList.DEFAULT_CAPACITY;
        this.size = 0;
        this.internalArray = new Object[this.capacity];
    }

    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public int size() {
        return this.size;
    }

    private class DIYiterator implements Iterator<E> {
        protected int counter;
        protected int lastCounter = -1;

        @Override
        public boolean hasNext() {
            return (counter != (size));
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastCounter = counter;
            counter++;
            return (E) internalArray[counter - 1];
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new DIYiterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return this.listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new DIYlistIterator(index);
    }

    public class DIYlistIterator extends DIYiterator implements ListIterator<E> {
        @Override
        public boolean hasPrevious() {
            return (this.counter >= 1 && this.counter < size);
        }

        public DIYlistIterator(int count) {
            this.counter = count;
        }

        @Override
        public E previous() {
            if (hasPrevious()) {
                counter--;
                return (E) DIYarrayList.this.internalArray[lastCounter = counter];
            }
            throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            if (this.counter < (size - 1)) {
                return this.counter + 1;
            }
            return -1;
        }

        @Override
        public int previousIndex() {
            return (this.counter - 1);
        }

        @Override
        public void remove() {
            DIYarrayList.this.remove(this.lastCounter);
            this.counter = this.lastCounter;
            this.lastCounter = -1;
        }

        @Override
        public void set(E e) {
            DIYarrayList.this.set(lastCounter, e);
        }

        @Override
        public void add(E e) {
            DIYarrayList.this.add(this.counter, e);
            this.counter++;
            this.lastCounter--;
        }
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Spliterator<E> spliterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        throw new UnsupportedOperationException();

    }

    @Override
    public Stream<E> stream() {
        throw new UnsupportedOperationException();

    }

    @Override
    public Stream<E> parallelStream() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public boolean contains(Object o) {
        return (this.indexOf(o) >= 0);
    }

    @Override
    public Object[] toArray() {
        Object[] returnArray = new Object[this.size];
        System.arraycopy(this.internalArray, 0, returnArray, 0, this.size);
        return returnArray;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < this.size)
            return (T[]) Arrays.copyOfRange(this.internalArray, 0, this.size, a.getClass());
        System.arraycopy(this.internalArray, 0, a, 0, this.size);
        if (a.length > this.size)
            a[this.size] = null;
        return a;
    }

    private void extendSize(int newSize) {
        Object[] newInternalArray = new Object[newSize];
        newInternalArray = Arrays.copyOf(this.internalArray, newSize);
        this.internalArray = newInternalArray;
        this.capacity = newSize;
    }

    private boolean needMoreCapacity(int newSize) {
        return (newSize >= this.capacity);
    }

    @Override
    public boolean add(E e) {
        if (this.needMoreCapacity(this.size + 1)) {
            this.extendSize(this.capacity + DIYarrayList.DEFAULT_EXTENDING_STEP);
        }
        this.internalArray[this.size] = e;
        this.size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = this.indexOf(o);
        this.remove(index);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        int newSize = this.size + c.size();
        if (this.needMoreCapacity(newSize)) {
            this.extendSize(newSize);
        }
        Object[] newInternalArray = new Object[this.capacity];
        System.arraycopy(this.internalArray, 0, newInternalArray, 0, this.size);
        System.arraycopy(c, 0, newInternalArray, this.size, newSize);
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void clear() {
        this.init();
    }

    @Override
    public E get(int index) {
        if (index < this.size && index >= 0) {
            return (E) this.internalArray[index];
        } else {
            throw new ArrayIndexOutOfBoundsException(String.format("Index %d is out of the array. Real size is %d, capacity is %d", index, this.size, this.capacity));
        }
    }

    @Override
    public void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);
        ListIterator<E> i = this.listIterator();
        for (Object e : a) {
            if (i.hasNext()) {
                i.next();
                i.set((E) e);
            }
        }
    }

    @Override
    public E set(int index, E element) {
        if (index >= this.size || index < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("Index %d is out of the array. Real size is %d, capacity is %d", index, this.size, this.capacity));
        } else {
            E temp = (E) this.internalArray[index];
            this.internalArray[index] = element;
            return temp;
        }
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();

    }

    @Override
    public E remove(int index) {
        if (index >= 0 && index <= this.size && this.size>0) {
            E element = this.get(index);
            Object[] newInternalArray = new Object[this.capacity-1];
            for (int i =0 ; i < index; i++) {
                newInternalArray[i] = this.internalArray[i];
            }
            for(int i=index; i<this.size-1; i++){
                newInternalArray[i] = this.internalArray[i+1];
            }
            this.internalArray = newInternalArray;
            this.size--;
            return element;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public int indexOf(Object o) {
        int counter = 0;
        for (Object current : this.internalArray) {
            if (current.equals(o)) {
                return counter;
            }
            counter++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = (this.size - 1); i >= 0; i--) {
            if (this.internalArray[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }


}
