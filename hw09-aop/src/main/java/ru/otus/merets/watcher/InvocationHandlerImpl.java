package ru.otus.merets.watcher;

import ru.otus.merets.methodutils.UniqMethod;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class InvocationHandlerImpl<T> implements InvocationHandler {
    private Set<UniqMethod> markedMethods; //a set of marked methods
    private boolean isAnalyzed; //this means (if it is true) we have already marked all annotated methods

    private void findAllAnnotatedMethods() {
        Arrays.stream(this.clazz.getClass().getMethods()).forEach(method -> {
            if (method.isAnnotationPresent(Log.class)) {
                markedMethods.add(new UniqMethod(method.getName(), method.getParameterTypes()));
            }
        });
        isAnalyzed = true;
    }

    private final T clazz;

    InvocationHandlerImpl(T myClass) {
        this.clazz = myClass;
        markedMethods = new HashSet<>();
        isAnalyzed = false;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!isAnalyzed) {
            findAllAnnotatedMethods();
        }

        if (markedMethods.contains(new UniqMethod(method.getName(), method.getParameterTypes()))) {
            System.out.println("executed method:" + method);
            if (args != null) {
                Arrays.stream(args).forEach((p) -> System.out.println(" - " + p.toString()));
            } else {
                System.out.println(" - no arguments for this method");
            }
        }
        return method.invoke(clazz, args);
    }


}

