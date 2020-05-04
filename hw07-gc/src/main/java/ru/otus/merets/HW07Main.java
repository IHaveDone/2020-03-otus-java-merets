package ru.otus.merets;

import com.sun.management.GarbageCollectionNotificationInfo;
import ru.otus.merets.memoryguzzler.MemoryGuzzler;
import ru.otus.merets.memoryguzzler.ByteMemoryGuzzler;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

public class HW07Main {
    private static long maxDuration;
    private static long oldCounter;
    private static long youngCounter;
    public static void main(String[] args) throws InterruptedException {
        maxDuration=0;
        oldCounter=0;
        youngCounter=0;
        switchOnMonitoring();
        MemoryGuzzler memoryGuzzler = new ByteMemoryGuzzler();
        while(memoryGuzzler.eatSlowly()) {
            Thread.sleep(2);
        }
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();
                    if(duration>maxDuration) {
                        maxDuration=duration;
                    }
                    if(gcAction.equals("end of minor GC")){
                        youngCounter++;
                    } else if(gcAction.equals("end of major GC")) {
                        oldCounter++;
                    }

                    System.out.println("Max duration:"+ maxDuration +" old:"+oldCounter+" young: "+youngCounter+" start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}
