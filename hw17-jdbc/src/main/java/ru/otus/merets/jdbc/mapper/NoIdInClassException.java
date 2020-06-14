/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.jdbc.mapper;

public class NoIdInClassException extends RuntimeException {
    public NoIdInClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
