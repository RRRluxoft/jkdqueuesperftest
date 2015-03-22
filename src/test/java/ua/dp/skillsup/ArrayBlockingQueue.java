package ua.dp.skillsup;
/**
 * Date: 22.03.2015
 * Time: 21:55
 * To change this template use File | Settings | File Templates.
 */

import java.util.Queue;

/**
 * @author Roman Litvishko
 */
public class ArrayBlockingQueue<Integer> {

    private int limit = 10;
    /** items index for next take, poll, peek or remove */
    int takeIndex;

    /** items index for next put, offer, or add */
    int putIndex;

    /** Number of elements in the queue */
    int count;
    private Object[] array ;

    public ArrayBlockingQueue(int capacity) {
        this.array = new Object[capacity];
    }

    final int inc(int i) {
        return (++i == array.length) ? 0 : i;
    }

    public void put(Integer item) throws InterruptedException {
        while (array.length == limit) {
            wait();
        }
        if (array.length == 0) {
            notifyAll();
        }
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] == null) {
                array[i] = item;
            }
        }
    }

    public Object get() throws InterruptedException {
        while (array.length == 0) {
            wait();
        }
        if (array.length == limit) {
            notifyAll();
        }
        return array[0];
    }

}
