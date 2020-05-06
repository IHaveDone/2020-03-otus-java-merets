package ru.otus.merets.watcher;

import ru.otus.merets.blackbox.BoxInterface;
import ru.otus.merets.exception.CreateWatcherException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;


public class Watcher<T> {
    public <T> T createMyClass(Class<? super T> childClass) throws CreateWatcherException {
        Constructor<? super T> contructor = null;
        try {
            contructor = childClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new CreateWatcherException("Cannot get constructor");
        }
        Object testObject = null;
        try {
            testObject = childClass.cast(contructor.newInstance());
        } catch (Exception e) {
            throw new CreateWatcherException("Cannot cast type");
        }
        InvocationHandler handler = new InvocationHandlerImpl<T>((T) testObject);
        T example = (T) Proxy.newProxyInstance(Watcher.class.getClassLoader(),
                new Class<?>[]{BoxInterface.class}, handler);
        return example;
    }
}
