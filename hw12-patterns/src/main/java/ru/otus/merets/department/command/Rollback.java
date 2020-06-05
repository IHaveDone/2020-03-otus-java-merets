/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.department.command;

import ru.otus.merets.atm.ATM;

public class Rollback implements Command<Boolean> {
    @Override
    public Boolean execute(ATM atm) {
        return atm.rollback();
    }
}
