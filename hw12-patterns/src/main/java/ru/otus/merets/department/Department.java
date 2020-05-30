/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.department;

import ru.otus.merets.atm.ATM;

import java.util.LinkedList;
import java.util.List;

public class Department {
    private List<ATM> atmList;

    public Department(){
        atmList = new LinkedList<>();
    }

    public void addATM(ATM atm){
        atmList.add(atm);
    }

}
