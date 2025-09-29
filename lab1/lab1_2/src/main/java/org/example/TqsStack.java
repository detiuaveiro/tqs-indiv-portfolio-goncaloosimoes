package org.example;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> {
    private LinkedList<T> list = new LinkedList<>();

    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return list.removeFirst();
    }

    public int size() {
        return list.size();
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return list.getFirst();
    }

    public void push(T t) {
        list.addFirst(t);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public T popTopN(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }

        if (n > size()) {
            throw new NoSuchElementException("n is greater than stack size");
        }

        // pop n-1 elements
        for (int i = 0; i < n-1; i++) {
            pop();
        }
        // return nth element
        return pop();
    }
}