package ru.otus.merets.watcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Watcher<T> {
    public <T> T createProxedObject(T childClass) {
        InvocationHandler handler = new InvocationHandlerImpl<T>(childClass );
        return (T) Proxy.newProxyInstance(Watcher.class.getClassLoader(),
                childClass.getClass().getInterfaces(), handler);

    }
}
