package ru.otus.merets;

import ru.otus.merets.blackbox.BoxImpl;
import ru.otus.merets.blackbox.BoxInterface;
import ru.otus.merets.exception.CreateWatcherException;
import ru.otus.merets.watcher.Watcher;

public class HW09Main {

    public static void main(String[] args) throws CreateWatcherException {

        Watcher watcher = new Watcher<BoxInterface>();
        BoxInterface myClass = (BoxInterface) watcher.createMyClass(BoxImpl.class);

        myClass.startAction(0, "Security Param");   //Log
        myClass.getStatus(0);                                 //Log
        myClass.startAction(1, "One more param");   //Log
        myClass.getStatus(1);                                 //Log
        myClass.getStatus();                                            // no Log

    }
}
