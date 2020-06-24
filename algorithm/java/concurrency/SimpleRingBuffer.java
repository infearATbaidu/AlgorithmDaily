/*
 * Copyright (C) 2020 Baidu, Inc. All Rights Reserved.
 */
package concurrency;

import java.util.Arrays;

/**
 * @author Zhang Gang (zhanggang02@baidu.com)
 */
public class SimpleRingBuffer {
    private byte buffer[];
    private int start, end;

    public SimpleRingBuffer(int capacity) {
        buffer = new byte[100];
        start = 0;
        end = capacity;
    }

    public boolean push(byte[] bytes) {
        if (!check(bytes)) {
            return false;
        }
        if (start < end) {
            System.arraycopy(bytes, 0, buffer, start, bytes.length);
            start += bytes.length;
        } else {
            System.arraycopy(bytes, 0, buffer, start, buffer.length - start);
            System.arraycopy(bytes, 0 + buffer.length - start, buffer, 0, bytes.length - buffer.length + start);
            start = (start + bytes.length) % buffer.length;
        }
        return true;
    }

    private boolean check(byte[] bytes) {
        if (bytes.length > Math.abs(end - start)) {
            return false;
        }
        return true;
    }

    public byte[] pop(int num) {
        if (start <= end) {
            if (buffer.length - end + start < num) {
                return null;
            } else {
                byte[] copy = new byte[num];
                if (num <= buffer.length - end) {
                    System.arraycopy(buffer, end % buffer.length, copy, 0, num);
                } else {
                    System.arraycopy(buffer, end % buffer.length, copy, 0, buffer.length - end);
                    System.arraycopy(buffer, 0, copy, buffer.length - end, num - buffer.length + end);
                }
                end = (end + num) % buffer.length;
                return copy;
            }
        } else {
            if (start - end < num) {
                return null;
            } else {
                byte[] copy = new byte[num];
                System.arraycopy(buffer, end, copy, 0, num);
                end += num;
                return copy;
            }
        }
    }

    public static void main(String args[]) {
        int capacity = 100;
        SimpleRingBuffer ringBuffer = new SimpleRingBuffer(capacity);
        byte[] firstPart = new byte[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int length = firstPart.length;
        boolean success = ringBuffer.push(firstPart);
        System.out.println(String.format("Push %d bytes %b.", length, success));
        assert success;

        byte[] remain = new byte[capacity - firstPart.length - 10];
        success = ringBuffer.push(remain);
        System.out.println(String.format("Push %d bytes %b.", remain.length, success));
        assert success;

        byte[] tooLong = new byte[11];
        success = ringBuffer.push(tooLong);
        System.out.println(String.format("Push %d bytes %b.", tooLong.length, success));
        assert !success;

        assert Arrays.equals(ringBuffer.pop(length), firstPart);
        System.out.println(String.format("Pop %d bytes.", length));

        remain = new byte[20];
        success = ringBuffer.push(remain);
        System.out.println(String.format("Push %d bytes %b.", remain.length, success));
        assert success;

        remain = new byte[1];
        success = ringBuffer.push(remain);
        System.out.println(String.format("Push %d bytes %b.", remain.length, success));
        assert !success;
    }

}
