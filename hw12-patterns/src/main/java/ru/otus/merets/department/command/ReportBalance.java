/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.department.command;

import ru.otus.merets.atm.ATM;

public class ReportBalance implements Command {
    @Override
    public int execute(ATM atm) {
        return atm.getBalance();
    }
}
