package net.cserny.tdd.multithreading;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadFactory;

public class Server {
    private ThreadFactory threadFactory;

    public void setThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    public void start() {
        threadFactory.newThread(() -> {
            System.out.println(new SimpleDateFormat().format(new Date(System.currentTimeMillis())) + " Server started");
        });
    }

    public void stop() {
        threadFactory.newThread(() -> {
            System.out.println(new SimpleDateFormat().format(new Date(System.currentTimeMillis())) + " Server stopped");
        });
    }
}
