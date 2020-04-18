package ru.otus.merets;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainHW06 {

    public static void startTest(Class clazz){
        int allTests=0;
        int passedTests=0;
        int failedTests=0;
        List<Method> beforeMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();

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

        allTests = testMethods.size();
        int currentMethod = 0;

        Constructor<Object> contructor = null;
        try {
            contructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        for (Method method : testMethods) {
            Object testObject = null;
            try {
                testObject = clazz.cast( contructor.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            System.out.println("");
            currentMethod++;
            System.out.println(String.format("#%d. Starting a new test: %s", currentMethod, method.getName()));
            for (Method beforeMethod : beforeMethods) {
                System.out.println(String.format(" - init with: %s", beforeMethod.getName()));
                try {
                    beforeMethod.invoke(testObject);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(String.format(" - running: %s", method.getName()));
            try {
                method.invoke(testObject);
                System.out.println(ConsoleColors.GREEN +" --- SUCCESS " + ConsoleColors.RESET);
            } catch (Exception e) {
                System.out.println(ConsoleColors.RED +" --- FAIL " + ConsoleColors.RESET);
                failedTests++;
                e.printStackTrace();
            }
            for (Method afterMethod : afterMethods) {
                System.out.println(String.format(" - finish with: %s", afterMethod.getName()));
                try {
                    afterMethod.invoke(testObject);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        passedTests = allTests - failedTests;
        System.out.println();
        System.out.println(String.format("You run %d tests. %d have finished successfully, %d have finished with errors", allTests, passedTests, failedTests));
    }

    public static void main(String[] args) {
        startTest(WebApplicationTest.class);
    }
}
