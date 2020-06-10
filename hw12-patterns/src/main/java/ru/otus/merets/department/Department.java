/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.department;

import ru.otus.merets.atm.ATM;
import ru.otus.merets.atm.exception.IncorrectATMInDepartmentException;
import ru.otus.merets.department.command.Callback;
import ru.otus.merets.department.command.Command;

import java.util.LinkedList;
import java.util.List;

public class Department {
    private List<ATM> listeners;

    public Department(){
        listeners = new LinkedList<>();
    }

    public void addListener(ATM atm){
        listeners.add(atm);
    }

    public void removeListener( ATM atm){
        listeners.remove(atm);
    }

    public void event(Command command, Callback callback) {
        listeners.forEach(listener -> {
            callback.execute( listener.onCommand(command) );
        });
    }

    public ATM get(int i){
        if(i<listeners.size() && i>=0 ){
            return listeners.get(i);
        } else {
            throw new IncorrectATMInDepartmentException("You are trying to get ATM with incorrect index", new Throwable());
        }
    }




}
