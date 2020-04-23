package ru.otus.merets.autotest;

import ru.otus.merets.util.ConsoleColors;
import ru.otus.merets.annotation.After;
import ru.otus.merets.annotation.Before;
import ru.otus.merets.annotation.Test;
import ru.otus.merets.exception.TestStarterDefaultConstructorException;
import ru.otus.merets.exception.TestStarterInitFailedException;
import ru.otus.merets.exception.TestStarterRunBeforeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestStarter {
    private List<Method> beforeMethods;
    private List<Method> testMethods;
    private List<Method> afterMethods;
    private Class clazz;

    public TestStarter(Class clazz) {
        this.clazz = clazz;
        beforeMethods = new ArrayList<>();
        testMethods = new ArrayList<>();
        afterMethods = new ArrayList<>();
    }

    private void init() {
        Arrays.stream(clazz.getMethods()).forEach(method -> {
                    if (method.isAnnotationPresent(Before.class)) {
                        beforeMethods.add(method);
                    }
                    if (method.isAnnotationPresent(Test.class)) {
                        testMethods.add(method);
                    }
                    if (method.isAnnotationPresent(After.class)) {
                        afterMethods.add(method);
                    }
                }
        );
    }

    private Object getObject() throws TestStarterInitFailedException, TestStarterDefaultConstructorException {
        Constructor<Object> contructor = null;
        try {
            contructor = clazz.getConstructor();
        } catch (Exception e) {
            throw new TestStarterDefaultConstructorException("Your test class shall have default constructor without any arguments");
        }
        Object testObject = null;
        try {
            testObject = clazz.cast(contructor.newInstance());
        } catch (Exception e) {
            throw new TestStarterInitFailedException("InstantiationException");
        }
        return testObject;
    }

    private void runBefore(Object testObject) throws TestStarterRunBeforeException {
        for (Method beforeMethod : beforeMethods) {
            try {
                beforeMethod.invoke(testObject);
            } catch (Exception e) {
                throw new TestStarterRunBeforeException(String.format("Exception during '%s' beforeMethod execution", beforeMethod.getName()));
            }
        }
    }

    private void runAfter(Object testObject) {
        for (Method afterMethod : afterMethods) {
            try {
                afterMethod.invoke(testObject);
            } catch (Exception e) {
                System.out.println( String.format(" - method %s failed. If most cases this is not a problem, but you should know about it. Maybe you do something wrong.", afterMethod.getName() ));
                e.printStackTrace();
            }
        }
    }

    public void test() throws TestStarterInitFailedException, TestStarterDefaultConstructorException {
        init();
        int failedTests = 0;
        int currentMethod = 0;

        for (Method method : testMethods) {
            System.out.println(String.format("#%d. Starting a new test: %s", ++currentMethod, method.getName()));
            Object testObject = null;

            testObject = getObject();
            try {
                runBefore(testObject);
                method.invoke(testObject);
                System.out.println(ConsoleColors.GREEN + " --- SUCCESS " + ConsoleColors.RESET);
            } catch (Exception e) {
                failedTests++;
                System.out.println(ConsoleColors.RED + " --- FAIL " + ConsoleColors.RESET);
                e.printStackTrace();
            } finally {
                runAfter(testObject);
            }

        }
        System.out.println(ConsoleColors.BLACK_BOLD + String.format("You run %d tests. %d have finished successfully, %d have finished with errors", testMethods.size(), (testMethods.size() - failedTests), failedTests) + ConsoleColors.RESET);
    }



}
