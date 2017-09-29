package net.cserny.tdd.multithreading;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class MethodBlockingTest {
    @Test
    public void testBlockingBehaviour() throws Exception {
        final AtomicBoolean blocked = new AtomicBoolean(true);

        Thread buyer = new Thread(() -> {
            try {
                new BlackMarket().buyTicket();
                blocked.set(false);
            } catch (InterruptedException ignored) { }
        });

        buyer.start();
        Thread.sleep(1000);
        buyer.interrupt();
        buyer.join(1000);

        Assert.assertFalse("Thread didn't interrupt", buyer.isAlive());
        Assert.assertTrue("Method didn't block", blocked.get());
    }
}
