/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.mygson;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.merets.practice.ClassWithAnotherClass;
import ru.otus.merets.practice.ClassWithArrays;
import ru.otus.merets.practice.ClassWithCollections;
import ru.otus.merets.practice.ClassWithSimpleFields;

import java.util.*;

@DisplayName("MyGson must ")
public class MyGsonTest {
    private MyGson myGson;
    private Gson gson;

    @BeforeEach
    public void init(){
        myGson = new MyGson();
        gson = new Gson();
    }

    private void printResult(Object object){
        System.out.println("MyGson: "+myGson.toJson(object) );
        System.out.println("Gson: "+gson.toJson(object) );
    }

    @Test
    @DisplayName(" restore an object where class has only simple fields")
    public void toJsonClassWithSimpleFields(){
        ClassWithSimpleFields classWithSimpleFields = new ClassWithSimpleFields(400, 20, "Otus Java", 'A');
        printResult(classWithSimpleFields);
        ClassWithSimpleFields restoredObject = gson.fromJson(myGson.toJson(classWithSimpleFields), ClassWithSimpleFields.class);
        Assertions.assertEquals(classWithSimpleFields, restoredObject );
    }

    @Test
    @DisplayName(" restore an object with array of simple types")
    public void toJsonWithArrays(){
        ClassWithArrays classWithArrays = new ClassWithArrays("one more test", -20, 666.666f, new String[]{"Mercury", "Venus", "Earth"});
        printResult(classWithArrays);
        ClassWithArrays restoredObject = gson.fromJson(myGson.toJson(classWithArrays), ClassWithArrays.class);
        Assertions.assertEquals(classWithArrays, restoredObject );
    }

    @Test
    @DisplayName(" restore an object with collection")
    public void toJsonWithCollections(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Set<Float> set = new HashSet<>();
        set.add(3.141592f);
        set.add(0.00001f);
        set.add(99.9999f);
        ClassWithCollections classWithCollections = new ClassWithCollections(list,set);
        printResult(classWithCollections);
        ClassWithCollections restoredObject = gson.fromJson(myGson.toJson(classWithCollections), ClassWithCollections.class);
        Assertions.assertEquals(classWithCollections, restoredObject );
    }

    @Test
    @DisplayName(" restore an object where class has another class")
    public void toJsonClassWithObject(){
        ClassWithSimpleFields classWithSimpleFields = new ClassWithSimpleFields(400, 20, "Otus Java", 'A');
        ClassWithAnotherClass classWithAnotherClass = new ClassWithAnotherClass(classWithSimpleFields);
        printResult(classWithAnotherClass);
        ClassWithAnotherClass restoredObject = gson.fromJson(myGson.toJson(classWithAnotherClass), ClassWithAnotherClass.class);
        Assertions.assertEquals(classWithAnotherClass, restoredObject );
    }
}
