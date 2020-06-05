/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.practice;

import java.util.Arrays;
import java.util.Objects;

public class ClassWithArrays {
    private String label;
    private int level;
    private Float radius;
    private String[] alumni;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassWithArrays that = (ClassWithArrays) o;
        return level == that.level &&
                Objects.equals(label, that.label) &&
                Objects.equals(radius, that.radius) &&
                Arrays.equals(alumni, that.alumni);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(label, level, radius);
        result = 31 * result + Arrays.hashCode(alumni);
        return result;
    }

    public ClassWithArrays(String label, int level, Float radius, String[] alumni) {
        this.label = label;
        this.level = level;
        this.radius = radius;
        this.alumni = alumni;
    }
}
