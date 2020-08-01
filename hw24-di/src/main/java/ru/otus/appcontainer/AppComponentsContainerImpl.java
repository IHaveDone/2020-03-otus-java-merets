package ru.otus.appcontainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.config.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * En:
 * This is a custom DI implementation.
 * Assumption #1: {@code name} param (in {@code AppComponent}) is uniq
 * <p>
 * Ru:
 * {@code AppComponentsContainerImpl} - попытка реализации принципа DI.
 * Допущение №1: поле {@code name} в аннотации {@code AppComponent} уникально
 */
public class AppComponentsContainerImpl implements AppComponentsContainer {
    private static final Logger logger = LoggerFactory.getLogger(AppComponentsContainerImpl.class);
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    /**
     * En: returns new instance of config class
     * Ru: возвращает экземпляр класса с конфигурацией
     *
     * @param configClass
     * @param constructor
     * @return
     */
    private Object getNewInstance(Class<?> configClass, Constructor constructor) {
        Object obj = null;
        try {
            obj = constructor.newInstance();
        } catch (Exception e) {
            logger.error("It is impossible to create appConfig instance", e);
            throw new AppConfigInitializationException("It is impossible to create appConfig instance", e);
        }
        return obj;
    }

    private Object getNewInstanceViaMethod(Method method, Object obj) {
        Class[] params = method.getParameterTypes();
        List<Object> allNecessaryObjects = Arrays
                .stream(params)
                .map(p -> getAppComponent(p))
                .collect(Collectors.toList());

        Object newInstance = null;
        try {
            newInstance = method.invoke(obj, allNecessaryObjects.toArray());
        } catch (Exception e) {
            logger.error("Exception during DI-process.", e);
            throw new DIInitializationException("Exception during DI-process.", e);
        }
        return newInstance;
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);

        List<Method> methods = Arrays.asList(configClass.getMethods());

        Constructor<?>[] construct = configClass.getConstructors();
        if (construct.length != 1) {
            logger.error("Config class should have the only default constructor");
            throw new TooManyConstructorsInAppConfigException("Too many contructors in config class.  should have only one constructor");
        }
        Object configClassObject = getNewInstance(configClass, construct[0]);

        List<Method> methodListSorted = methods
                .stream()
                .filter(m -> m.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(m -> m.getAnnotation(AppComponent.class).order()))
                .collect(Collectors.toList());

        for (Method method : methodListSorted) {
            if (appComponentsByName.containsKey(method.getAnnotation(AppComponent.class).name())) {
                logger.error("Too many components with the same name.");
                throw new IncorrectParamsAppComponent(String.format("Too many components with the same name"));
            }
            appComponentsByName.put(
                    method.getAnnotation(AppComponent.class).name(),
                    getNewInstanceViaMethod(method, configClassObject)
            );
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            logger.error("Given class is not config {}", configClass.getName());
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        for (Map.Entry<String, Object> entry : appComponentsByName.entrySet()) {
            if (Arrays.asList(entry.getValue().getClass().getInterfaces()).contains(componentClass)) {
                return (C) entry.getValue();
            }
        }
        logger.error("Can't find component by class {}", componentClass.getClass().getName());
        throw new NonExistentComponentException(String.format("Can't find component by class %s", componentClass.getClass().getName()));
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        if (appComponentsByName.containsKey(componentName)) {
            return (C) appComponentsByName.get(componentName);
        }
        logger.error("Can't find component by class {}", componentName);
        throw new NonExistentComponentException(String.format("Can't find component by AppComponent.name %s", componentName));
    }
}
