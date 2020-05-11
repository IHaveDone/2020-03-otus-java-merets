package ru.otus.merets;

import ru.otus.merets.blackbox.Box;
import ru.otus.merets.blackbox.BoxImpl;
import ru.otus.merets.watcher.Watcher;

public class HW09Main {

    public static void main(String[] args) {
        Watcher watcher = new Watcher<Box>();
        Box box = new BoxImpl();
        Box myClass = (Box) watcher.createProxedObject( box );


        myClass.startAction(0, "Security Param");//Log
        myClass.getStatus(0);  //Log
        myClass.startAction(1, "One more param");//Log
        myClass.getStatus(1);  //Log
        myClass.getStatus();  // no Log

    }
}
