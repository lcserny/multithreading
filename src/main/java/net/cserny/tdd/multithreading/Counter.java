package net.cserny.tdd.multithreading;

public class Counter {
    private int counter;

    public int value() {
        return counter;
    }

    public synchronized void increment() {
        counter++;
    }
}
