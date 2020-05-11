package ru.otus.merets.methodutils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UniqMethod {
    private String methodName;
    private Set<Class> paramsSet;

    public UniqMethod(String methodName, Set<Class> paramsSet) {
        this.methodName = methodName;
        this.paramsSet = paramsSet;
    }
    public UniqMethod(String methodName, Class[] paramsArray) {
        this.methodName = methodName;
        this.paramsSet = new HashSet<>();
        Arrays.stream( paramsArray ).forEach( (p) -> paramsSet.add(p));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniqMethod that = (UniqMethod) o;
        return methodName.equals(that.methodName) &&
                paramsSet.equals(that.paramsSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodName, paramsSet);
    }
}
