/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.department.command;

@FunctionalInterface
public interface Callback<T> {
    public void execute(T object);
}
