package ru.otus.merets;

/**
 * En: Alternating threads in Java. Has been chosen the way to implement it via Thread.
 * HappensBefore principle implements through synchronized. There is no reason to do it
 * exactly this way. I just did it.
 * There are two monitors (static member of MyThread to sync order of thread starting
 * and external monitor object to sync order ot printing).
 *
 * Ru: Чередующиеся потоки. Реализовано через Thread, особых причин это нет, из рассматриваемых
 * технологий просто выбрал наугад. Очередность потоков реализована через статический монитор
 * класса MyThread, сам процесс печати через внещний объект монитора.
 */
public class HW29Main {
    public static void main(String[] args) throws InterruptedException {
        Object monitor = new Object();

        MyThread thread2 = new MyThread(monitor, false, "_");
        MyThread thread1 = new MyThread(monitor, true,"");

        thread2.start();
        Thread.sleep(3000);
        thread1.start();

        thread1.join();
        thread2.join();
    }
}
