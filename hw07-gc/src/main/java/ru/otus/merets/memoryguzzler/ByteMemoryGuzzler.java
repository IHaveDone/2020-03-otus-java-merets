package ru.otus.merets.memoryguzzler;

import java.util.Arrays;
import java.util.Random;

public class ByteMemoryGuzzler implements MemoryGuzzler{
    private byte[] internalArray;
    private int counter;
    private int goal;

    public ByteMemoryGuzzler(){
        internalArray = new byte[90000000];//мы убеждены, что буфера хватит для показаний датчика
        //хотя это не значит, что мы правы
        counter=0;
        goal=36000;//36 тыс показаний - число, на основании которого можно строить прогноз
    }

    public static byte[] concat(byte[] first, byte[] second) {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    @Override
    public boolean eatSlowly() {
        //byte[] arr = new byte[10];
        byte[] arr = new byte[(counter+1)*100]; // <-- new version with a bug
        Random r = new Random();
        r.nextBytes(arr);
        byte[] newInternalArray = concat(internalArray,arr);
        internalArray = newInternalArray;
        counter++;
        System.out.println(String.format( "Interation: %d",counter) );
        if(counter<goal){
            return true;
        } else {
            return false;
        }
    }
}
