package net.cserny.tdd.multithreading;

public abstract class RetriedAssert {
    private int timeoutMillis;
    private int intervalMillis;

    public RetriedAssert(int timeoutMillis, int intervalMillis) {
        this.timeoutMillis = timeoutMillis;
        this.intervalMillis = intervalMillis;
    }

    public final void start() throws Exception {
        long stopAt = System.currentTimeMillis() + timeoutMillis;
        while (System.currentTimeMillis() < stopAt) {
            try {
                run();
                return;
            } catch (AssertionError ignored) { }

            try {
                Thread.sleep(intervalMillis);
            } catch (InterruptedException ignored) { }
        }
        run();
    }

    public abstract void run() throws Exception;
}
