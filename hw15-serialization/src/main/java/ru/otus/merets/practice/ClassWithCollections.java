/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.practice;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ClassWithCollections {
    private List<Integer> floors;
    private Set<Float> mass;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassWithCollections that = (ClassWithCollections) o;
        return Objects.equals(floors, that.floors) &&
                Objects.equals(mass, that.mass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(floors, mass);
    }

    public ClassWithCollections(List<Integer> floors, Set<Float> mass) {
        this.floors = floors;
        this.mass = mass;
    }
}
