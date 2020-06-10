/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.department.command;

import ru.otus.merets.atm.ATM;

public class ReportBalance implements Command<Integer> {
    @Override
    public Integer execute(ATM atm) {
        return atm.getBalance();
    }
}
