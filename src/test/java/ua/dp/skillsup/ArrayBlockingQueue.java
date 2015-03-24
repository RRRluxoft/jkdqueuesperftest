package ua.dp.skillsup;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by User on 24.03.2015.
 */
public class ArrayBlockingQueue<T> implements Queue {
    
    final Object[] items;
    final int size ;
    AtomicInteger head;
    volatile Object one;
    volatile Object two;
    volatile Object three;
    volatile Object four;
    volatile Object five;
    volatile Object six;
    volatile Object seven;
    AtomicInteger tail;
    
    long fHead = 0;
    long fTail;
    
    int index;
    int putIndex;

    public ArrayBlockingQueue(int capacity) {
        this.items = new Object[capacity];
        head = new AtomicInteger(0);
        tail = new AtomicInteger(0);
        size = items.length - 1;
    }

    @Override
    public boolean offer(Object obj) {
        Object t = new Object();
        if ((tail.get() - head.get()) < size) {
            items[tail.getAndIncrement()] = obj;
        } 
        items[getPutIndex()] = obj;
        
        if (tail.get() - head.get() >= items.length)
        
        return false;
    }


    public int getPutIndex() {
        putIndex = head.get() - tail.get();
        if (putIndex > 0) {
            return putIndex %= items.length;
        }
        return putIndex;
    }

    @Override
    public Object poll() {
        
        
        return null;
    }
    
    
    
    
    @Override
    public int size() {
        return items.length;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public T[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }


    @Override
    public Object remove() {
        return null;
    }


    @Override
    public Object element() {
        return null;
    }

    @Override
    public Object peek() {
        return null;
    }
}
