package concurrency;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author infear
 */
public class MyBlockingQueue<T> {
    private LinkedList<T> queues;
    private int capacity;
    private Lock lock = new ReentrantLock();
    private Condition full = lock.newCondition();
    private Condition empty = lock.newCondition();

    public MyBlockingQueue(int capacity) {
        this.capacity = capacity;
        queues = new LinkedList<>();
    }

    public static void main(String args[]) {
        int outer = 0, inner = 0;
        for (double longitude = -10; longitude <= 10; longitude += 0.1) {
            outer++;
            for (double latitude = -2.5; latitude <= 2.5; latitude += 0.1) {
                System.out.println("latitude:" + latitude);
                inner++;
            }
            System.out.println("inner:" + inner);
            inner = 0;
        }
        System.out.println("outer:" + outer);

    }

    public T take() {
        T result = null;
        lock.lock();
        try {
            while (queues.size() == 0) {
                empty.await();
            }
            result = queues.pollFirst();
            full.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return result;
    }

    public void put(T task) {
        lock.lock();
        try {
            while (queues.size() == capacity) {
                full.wait();
            }
            queues.push(task);
            empty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
