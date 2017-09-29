package net.cserny.tdd.multithreading;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ServerStartsAndStopsTest {
    private StartStopSynchronizedThread thread;

    @Test
    public void startingAndStoppingServerThreads() throws Exception {
        ThreadFactory threadFactory = r -> {
            thread = new StartStopSynchronizedThread(r);
            return thread;
        };

        Server server = new Server();
        server.setThreadFactory(threadFactory);

        server.start();
        thread.waitForStarted(1, TimeUnit.SECONDS);

        server.stop();
        thread.waitForStoped(1, TimeUnit.SECONDS);
    }

    @Test
    public void testBySleeping() throws Exception {
        LongLastingCalculation calculation = new LongLastingCalculation();
        calculation.start();
        Thread.sleep(2000);
        Assert.assertEquals(42, calculation.getResult());
    }

    @Test
    public void testByRetryingTheAssertOften() throws Exception {
        final LongLastingCalculation calculation = new LongLastingCalculation();
        calculation.start();

        new RetriedAssert(2000, 100) {
            @Override
            public void run() throws Exception {
                Assert.assertEquals(42, calculation.getResult());
            }
        }.start();
    }
}
