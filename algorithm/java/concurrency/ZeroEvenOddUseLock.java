package concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * @author infear
 * <p>
 * Use "Lock" implmentation.
 */
public class ZeroEvenOddUseLock {
    private int n;
    private Lock lock = new ReentrantLock();
    private Condition zero = lock.newCondition();
    private Condition odd = lock.newCondition();
    private Condition even = lock.newCondition();
    private boolean flag = true;
    private int round;

    public ZeroEvenOddUseLock(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            if (!flag) {
                zero.await();
            }
            flag = false;
            printNumber.accept(0);
            round++;
            if (round % 2 == 1) {
                odd.signalAll();
            } else {
                even.signalAll();
            }
            lock.unlock();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            lock.lock();
            if (round % 2 != 0 || flag) {
                even.await();
            }
            printNumber.accept(i);
            flag = true;
            zero.signalAll();
            lock.unlock();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            lock.lock();
            if (round % 2 != 1 || flag) {
                odd.await();
            }
            printNumber.accept(i);
            flag = true;
            zero.signalAll();
            lock.unlock();
        }
    }
}
