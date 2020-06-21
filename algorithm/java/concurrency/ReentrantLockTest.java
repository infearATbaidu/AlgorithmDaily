package concurrency;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author infear
 * <p>
 * 通过自旋锁说明可重入锁
 */
public class ReentrantLockTest {
    // 错误实现
    class SpinLock {
        private AtomicReference<Thread> owner = new AtomicReference<>();

        // 同一线程调用lock两次，会导致第二次阻塞，从而死锁
        public void lock() {
            Thread current = Thread.currentThread();
            while (!owner.compareAndSet(null, current)) {
            }
        }

        public void unlock() {
            Thread current = Thread.currentThread();
            owner.compareAndSet(current, null);
        }
    }

    // 正确实现
    class SpinLock1 {
        private AtomicReference<Thread> owner = new AtomicReference<>();
        private int count = 0;

        public void lock() {
            // 先判断锁是不是当前线程获得，如果是，计数+1并返回；否则则等待；
            Thread current = Thread.currentThread();
            if (current == owner.get()) {
                count++;
                return;
            }

            while (!owner.compareAndSet(null, current)) {

            }
        }

        public void unlock() {
            Thread current = Thread.currentThread();
            // 只有当前线程持有锁时才需要释放，并将计数减一
            if (current == owner.get()) {
                if (count != 0) {
                    count--;
                } else {
                    owner.compareAndSet(current, null);
                }

            }

        }
    }

}
