/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.practice;

import java.util.Objects;

public class ClassWithAnotherClass {
    private ClassWithSimpleFields classWithSimpleFields;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassWithAnotherClass that = (ClassWithAnotherClass) o;
        return Objects.equals(classWithSimpleFields, that.classWithSimpleFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classWithSimpleFields);
    }

    public ClassWithAnotherClass(ClassWithSimpleFields classWithSimpleFields) {
        this.classWithSimpleFields = classWithSimpleFields;
    }
}
