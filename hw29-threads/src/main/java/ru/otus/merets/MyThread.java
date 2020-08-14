package ru.otus.merets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(MyThread.class);
    private final StringBuilder currentStatus = new StringBuilder();
    private final Object monitor;
    private final Boolean isStarter;
    private final String offset;
    private Integer counter = 0;
    private Boolean isUpstairs = true;
    private static Boolean THE_FIRST_START = false;
    private static final Object SHARED_STARTER_MONITOR = new Object();

    public MyThread(Object monitor, Boolean isStarter, String offset) {
        this.monitor = monitor;
        this.isStarter = isStarter;
        this.offset = offset;
    }

    private static void waitWrapper(Object object) {
        try {
            object.wait();
        } catch (Exception e) {
            logger.error("Error during objects wait method. ", e);
        }
    }

    private void change() {
        if (counter == 10) {
            isUpstairs = false;
        }
        if (isUpstairs) {
            counter++;
        } else {
            counter--;
        }
        currentStatus.append(" ").append(counter);
    }

    @Override
    public void run() {
        currentStatus.append(Thread.currentThread().getName()).append(":").append(offset);

        synchronized (SHARED_STARTER_MONITOR) {
            if(!THE_FIRST_START) {
                if (isStarter) {
                    SHARED_STARTER_MONITOR.notifyAll();
                    THE_FIRST_START = true;
                } else {
                    waitWrapper(SHARED_STARTER_MONITOR);
                }
            }
        }

        while ( (counter > 1 || isUpstairs) && THE_FIRST_START) {
            change();
            synchronized (monitor) {
                monitor.notifyAll();
                System.out.println(currentStatus);
                if (counter > 1 || isUpstairs) {
                    waitWrapper(monitor);
                }
            }
        }
    }
}
