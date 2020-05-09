package ru.otus.merets.blackbox;

import ru.otus.merets.watcher.Log;

import java.util.HashMap;
import java.util.Map;

public class BoxImpl implements Box {
    private Map<Integer, String> matrix;
    public BoxImpl(){
        matrix = new HashMap<>();
    }

    @Override
    @Log
    public boolean getStatus(int actionCode) {
        return !matrix.get(actionCode).isEmpty();
    }

    public boolean getStatus(){
        System.out.println("getStatus without params");
        return true;
    }

    @Override
    @Log
    public void startAction(int actionCode, String actionParam) {
        matrix.put(actionCode,actionParam);
    }

    @Override
    public void stopAction(int actionCode) {
        matrix.remove(actionCode);
    }
}
