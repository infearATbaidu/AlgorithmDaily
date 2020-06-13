package concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author infear
 * <p>
 * https://leetcode-cn.com/problems/print-in-order/
 * 按序打印
 */
class Foo {
    private ReentrantLock lock = new ReentrantLock();
    private Condition second = lock.newCondition();
    private Condition third = lock.newCondition();
    private volatile int stage = 1;

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        lock.lock();
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        stage = 2;
        second.signal();
        lock.unlock();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        lock.lock();
        if (stage != 2) {
            second.await();
        }
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        stage = 3;
        third.signal();
        lock.unlock();
    }

    public void third(Runnable printThird) throws InterruptedException {
        lock.lock();
        if (stage != 3) {
            third.await();
        }
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
        lock.unlock();
    }
}
