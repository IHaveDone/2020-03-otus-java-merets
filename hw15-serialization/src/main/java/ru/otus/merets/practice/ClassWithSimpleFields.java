/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.practice;

import java.util.Objects;

public class ClassWithSimpleFields {
    private int size;
    private int sum;
    private String name;
    private char letter;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassWithSimpleFields that = (ClassWithSimpleFields) o;
        return size == that.size &&
                sum == that.sum &&
                name.equals( that.name ) &&
                letter == that.letter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, sum, name, letter);
    }

    public ClassWithSimpleFields(int size, int sum, String name, char letter) {
        this.size = size;
        this.sum = sum;
        this.name = name;
        this.letter = letter;
    }
}
