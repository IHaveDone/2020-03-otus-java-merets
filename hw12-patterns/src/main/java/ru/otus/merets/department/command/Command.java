/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.department.command;


import ru.otus.merets.atm.ATM;

@FunctionalInterface
public interface Command {
    int execute(ATM atm);
}
