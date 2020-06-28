/*
 * Copyright (C) 2020 Baidu, Inc. All Rights Reserved.
 */
package concurrency;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * @author Zhang Gang (zhanggang02@baidu.com)
 */
public class FizzBuzz {
    Semaphore number = new Semaphore(1);
    Semaphore fi = new Semaphore(0);
    Semaphore bu = new Semaphore(0);
    Semaphore fibu = new Semaphore(0);
    private int n;

    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        for (int i = 3; i <= n; i += 3) {
            if (i % 15 != 0) {
                fi.acquire();
                printFizz.run();
                number.release();
            }
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        for (int i = 5; i <= n; i += 5) {
            if (i % 15 != 0) {
                bu.acquire();
                printBuzz.run();
                number.release();
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for (int i = 15; i <= n; i += 15) {
            fibu.acquire();
            printFizzBuzz.run();
            number.release();
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            number.acquire();
            if (i % 15 == 0) {
                fibu.release();
            } else if (i % 5 == 0) {
                bu.release();
            } else if (i % 3 == 0) {
                fi.release();
            } else {
                printNumber.accept(i);
                number.release();
            }
        }
    }
}
