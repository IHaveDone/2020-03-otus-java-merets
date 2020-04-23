package ru.otus.merets;

import ru.otus.merets.autotest.TestStarter;
import ru.otus.merets.webapp.test.WebApplicationTest;

public class MainHW06 {
    public static void main(String[] args) {
        try {
            new TestStarter(WebApplicationTest.class).test();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
