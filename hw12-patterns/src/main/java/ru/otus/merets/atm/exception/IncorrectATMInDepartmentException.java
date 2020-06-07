/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.atm.exception;

public class IncorrectATMInDepartmentException extends ATMException {
    public IncorrectATMInDepartmentException(String message, Throwable cause) {
        super(message, cause);
    }
}