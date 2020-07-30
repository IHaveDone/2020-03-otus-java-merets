package ru.otus.appcontainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.config.TooManyConstructorsInAppConfigException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * En:
 * This is a custom DI implementation.
 * Assumption #1: {@code order} is set in numeric style. That is we always have
 * 1st, 2nd, 3rd, etc. The case when we have {@code order} number 1 and {@code order} number 3, but do not have
 * {@code order} number 3 is prohibited.
 * Assumption #2: {@code name} param (in {@code AppComponent}) is uniq
 * Assumption #3: all components have the only one constructor. In the other case we need to determine
 * which exactly constructor we have to use to create object.
 * Assumption #4: config class should have only default constructor.
 * <p>
 * Ru:
 * {@code AppComponentsContainerImpl} - попытка реализации принципа DI.
 * Допущеение №1: поле {@code order} в аннотации {@code AppComponent} порядковое. То есть он увеличивается по очереди,
 * не может быть ситуации, когда у метода аннотация с {@code order} 1, {@code order} 3, но нет аннотации с {@code order} 2.
 * Допущение №2: поле {@code name} в аннотации {@code AppComponent} уникально
 * Допущение №3: все компоненты имеют только один конструктор, иначе нам придется научиться определять наиболее подходящий
 * конструктор и подбирать под него входящие параметры, это надо иметь в виду.
 * Допущение №4: класс конфига должен иметь только конструктор по умолчанию
 */
public class AppComponentsContainerImpl implements AppComponentsContainer {
    private static final Logger logger = LoggerFactory.getLogger(AppComponentsContainerImpl.class);
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    /**
     * En: gets sorted list of methods (sorted by {@code order} property)
     * Ru: возвращает отсортированный по полю {@code order} список с именами методов
     * (имена - параметр {@code name} аннотации {@code AppComponent}
     *
     * @param methods
     * @return
     */
    private List<String> getSortedMethods(List<Method> methods) {
        Map<String, Integer> componentsMap = new HashMap();
        for (Method method : methods) {
            if (method.isAnnotationPresent(AppComponent.class)) {
                AppComponent appComponent = method.getAnnotation(AppComponent.class);
                componentsMap.put(appComponent.name(), appComponent.order());
            }
        }
        return componentsMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map((e) -> {
                    return e.getKey();
                })
                .collect(Collectors.toList());
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
            obj = configClass.cast(constructor.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private Object getNewInstanceViaMethod(Method method, Object obj) {
        Class<?>[] params = method.getParameterTypes();
        List<Object> allNecessaryObjects = new ArrayList<>();
        for (Class clazz : params) {
            allNecessaryObjects.add(getAppComponent(clazz));
        }
        Object newInstance = null;
        try {
            if (params == null) {
                newInstance = method.invoke(obj);
            } else {
                newInstance = method.invoke(obj, allNecessaryObjects.toArray());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return newInstance;
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);

        List<Method> methods = Arrays.asList(configClass.getMethods());
        List<String> sortedMethods = getSortedMethods(methods);

        Constructor<?>[] construct = configClass.getConstructors();
        if (construct.length != 1) {
            logger.error("Config class should have the only default constructor");
            throw new TooManyConstructorsInAppConfigException("Too many contructors in config class.  should have only one constructor");
        }
        Object configClassObject = getNewInstance(configClass, construct[0]);

        for (String currentComponent : sortedMethods) {
            for (Method method : methods) {
                if (method.isAnnotationPresent(AppComponent.class)) {
                    AppComponent appComponent = method.getAnnotation(AppComponent.class);
                    if (appComponent.name().equals(currentComponent)) {
                        appComponentsByName.put(currentComponent, getNewInstanceViaMethod(method, configClassObject));
                    }
                }
            }
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
        throw new NonExistentComponentException(String.format("Can't find component by class {}", componentClass.getClass().getName()));
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        if (appComponentsByName.containsKey(componentName)) {
            return (C) appComponentsByName.get(componentName);
        }
        throw new NonExistentComponentException(String.format("Can't find component by AppComponent.name {}", componentName));
    }
}
