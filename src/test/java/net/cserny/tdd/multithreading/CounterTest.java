package net.cserny.tdd.multithreading;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CyclicBarrier;

public class CounterTest {
    @Test
    public void testBasicFunctionality() throws Exception {
        Counter counter = new Counter();
        Assert.assertEquals(0, counter.value());

        counter.increment();
        Assert.assertEquals(1, counter.value());

        counter.increment();
        Assert.assertEquals(2, counter.value());
    }

    @Test
    public void testForThredSafety() throws Exception {
        final Counter codeUnderTest = new Counter();
        final int numberOfThreads = 20;
        final int incrementsPerThread = 100;

        Runnable runnable = () -> {
            for (int i = 0; i < incrementsPerThread; i++) {
                codeUnderTest.increment();
            }
        };

        for (int i = 0; i < numberOfThreads; i++) {
            new Thread(runnable).start();
        }

        Thread.sleep(500);
        Assert.assertEquals(numberOfThreads * incrementsPerThread, codeUnderTest.value());
    }

    @Test
    public void testForThreadSafetyUsingCyclicBarrierToMaximizeConcurrency() throws Exception {
        final Counter codeUnderTest = new Counter();
        final int numberOfThreads = 20;
        final int incrementsPerThread = 100;

        // create barrier for all thread + current one
        CyclicBarrier entryBarrier = new CyclicBarrier(numberOfThreads + 1);
        CyclicBarrier exitBarrier = new CyclicBarrier(numberOfThreads + 1);

        Runnable runnable = () -> {
            for (int i = 0; i < incrementsPerThread; i++) {
                codeUnderTest.increment();
            }
        };

        for (int i = 0; i < numberOfThreads; i++) {
            new SynchedThread(runnable, entryBarrier, exitBarrier).start();
        }

        Assert.assertEquals(0, codeUnderTest.value());
        entryBarrier.await();
        exitBarrier.await();
        Assert.assertEquals(numberOfThreads * incrementsPerThread, codeUnderTest.value());
    }
}
