package ru.merets.otus;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.otus.merets.DIYarrayList;

import java.util.*;

public class DIYarrayListTest {

    public List<String> getSimpleList(int count){
        List<String> newArray = new DIYarrayList<>();
        for(int i=0; i<count; i++) {
            newArray.add("string "+i);
        }
        return newArray;
    }

    @Test
    public void initTest(){
        DIYarrayList<String> myArrayTest = new DIYarrayList<>();
        Assert.assertEquals("Initial capacity set correct", 10, myArrayTest.getCapacity() );
        Assert.assertEquals("Initial size set correct", 0, myArrayTest.size() );
        Assert.assertEquals("Initial real internalArray size set correct", 0, myArrayTest.toArray().length );
    }

    @Test
    public void addAndSizeTest() {
        List<String> testArray1 = new DIYarrayList<>();
        testArray1.add("just text message 1");
        testArray1.add("just text message 2");
        testArray1.add("just text message 3");
        Assert.assertEquals("Check method get() (start index)", "just text message 1", testArray1.get(0));
        Assert.assertEquals("Check method get() (last index)", "just text message 3", testArray1.get(2));
        Assert.assertEquals("Check method size()", 3, testArray1.size());
        testArray1.add("just text message 4");
        testArray1.add("just text message 5");
        testArray1.add("just text message 6");
        testArray1.add("just text message 7");
        testArray1.add("just text message 8");
        testArray1.add("just text message 9");
        testArray1.add("just text message 10");
        testArray1.add("just text message 11");
        Assert.assertEquals("Check capacity increasing", 20, ((DIYarrayList) testArray1).getCapacity());
        Collections.addAll(testArray1, "test addAll 1", "test addAll 2", "test addAll 3");
        Assert.assertEquals("Check method size of the list after addAll()", 14, testArray1.size());
    }

    @Test
    public void iteratorTest() {
        List<Integer> arrayList = new DIYarrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);
        Iterator<Integer> it = arrayList.iterator();
        it.next();
        it.next();
        Assert.assertEquals("Iterator checking next()", Integer.valueOf(3), it.next());
        ListIterator<Integer> listIt = arrayList.listIterator();
        listIt.next();
        listIt.next();
        Assert.assertEquals("ListIterator checking next()", Integer.valueOf(3), listIt.next());
        Assert.assertEquals("ListIterator checking previous()", Integer.valueOf(3), listIt.previous());
    }

    @Test(expected = NoSuchElementException.class)
    public void interatorPreviousException()
    {
        List<String> array = this.getSimpleList(1);
        ListIterator<String> lit = array.listIterator();
        lit.previous();
    }

    @Test(expected = NoSuchElementException.class)
    public void interatorNextException()
    {
        List<String> array = this.getSimpleList(1);
        ListIterator<String> lit = array.listIterator();
        lit.next();
        lit.next();
    }

    @Test
    public void compareMyArrayAndArrayList(){
        List<String> myArray = new DIYarrayList<>();
        List<String> referenceArray = new ArrayList<>();

        for(int i=0; i<30; i++) {
            myArray.add("string " + i);
            referenceArray.add("string "+i);
        }

        Assert.assertEquals("Compare ArrayList and DIYarrayList, size", referenceArray.size(),myArray.size());
        Assert.assertEquals("Compare ArrayList and DIYarrayList, indexOf via index", referenceArray.indexOf("string 5"), myArray.indexOf("string 5"));
        Assert.assertEquals("Compare ArrayList and DIYarrayList, lastIndexOf", referenceArray.lastIndexOf("string 5"), myArray.lastIndexOf("string 5"));
        Assert.assertEquals("Compare ArrayList and DIYarrayList, get", referenceArray.get(3), myArray.get(3));
        myArray.clear();
        referenceArray.clear();
        Assert.assertEquals("Compare ArrayList and DIYarrayList, isEmpty", referenceArray.isEmpty(), myArray.isEmpty());
    }

    @Test
    public void removeTest(){
        List<String> test = this.getSimpleList(3);
        test.remove(1);
        test.remove(0);
        Assert.assertEquals("Test remove (length)", 1, test.size());
        Assert.assertEquals("Test remove (content)", "string 2", test.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeExceptionTest(){
        List<String> test = this.getSimpleList(1);
        test.remove(0);
        test.remove(0);
    }

}
