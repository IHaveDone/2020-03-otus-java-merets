package ru.merets.otus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.otus.merets.DIYarrayList;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("DIYarrayList must ")
public class DIYarrayListTest {

    private static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of("string x1"),
                Arguments.of("string x2"),
                Arguments.of("string x3"),
                Arguments.of("string x4")
        );
    }

    private DIYarrayList<String> myArrayTest;

    @BeforeEach
    public void initTest(){
        myArrayTest = new DIYarrayList<>();
        for(int i=0; i<10; i++) {
            myArrayTest.add("string "+i);
        }
    }

    @DisplayName(" return correct element")
    @Test
    public void getTest() {
        Assertions.assertEquals("string 0", myArrayTest.get(0));
    }

    @DisplayName(" add new values into the array")
    @ParameterizedTest
    @MethodSource("generateData")
    public void shouldAddNewData(String value) {
        myArrayTest.add(value);
        Assertions.assertEquals(myArrayTest.get( myArrayTest.size()-1 ),value);
    }

    @DisplayName("... This is just a synthetic test to use mockito ")
    @Test
    public void workWithArrayList(){
        @SuppressWarnings( "unchecked" )
        ArrayList<String> arrayListMock = mock( ArrayList.class );
        when(arrayListMock.get(anyInt())).thenAnswer( i -> "string "+ i.getArgument(0) );
        Assertions.assertEquals( arrayListMock.get(0), myArrayTest.get(0) );
    }

}
