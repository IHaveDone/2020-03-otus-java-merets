package ru.otus.merets.watcher;

import ru.otus.merets.exception.CreateWatcherException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;


public class Watcher<T> {
    public <T> T createProxedObject(T childClass) throws CreateWatcherException {
        InvocationHandler handler = new InvocationHandlerImpl<T>(childClass );
        return (T) Proxy.newProxyInstance(Watcher.class.getClassLoader(),
                childClass.getClass().getInterfaces(), handler);

    }
}
