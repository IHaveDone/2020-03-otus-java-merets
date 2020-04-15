package ru.otus.merets;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Такой try\catch, конечно, только для тестовых демо запусков
        try {

            List<String> myFirstArray = new DIYarrayList<>();
            myFirstArray.add("#1 I have just added object number 1");
            myFirstArray.add("#1 I have just added object number 2");
            myFirstArray.add("#1 I have just added object number 3");

            System.out.println("Initial DIYarrayList");
            for (String obj : myFirstArray) {
                System.out.println(obj);
            }

            Collections.addAll(myFirstArray, "#2 just a string number 1", "#2 just a string number 2", "#2 just a string number 3", "#2 just a string number 4", "#2 just a string number 5");

            System.out.println();
            System.out.println("After addAll");
            for (String obj : myFirstArray) {
                System.out.println(obj);
            }

            List<String> mySecondArray = new DIYarrayList<>();
            mySecondArray.add("#3 I gonna use it for copy, 1");

            Collections.copy(myFirstArray, mySecondArray);

            System.out.println();
            System.out.println("After copy");
            for (String obj : myFirstArray) {
                System.out.println(obj);
            }

            Collections.sort(myFirstArray, (s1, s2) -> s1.compareTo(s2));

            System.out.println();
            System.out.println("After sort");
            for (String obj : myFirstArray) {
                System.out.println(obj);
            }
        }
        catch(Exception e){
            System.err.println(String.format("Error message: %s", e.getMessage()) );
            e.printStackTrace();
        }
    }


}
