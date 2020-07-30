package ru.otus.appcontainer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("AppComponentsContainerImplTest must ")
class AppComponentsContainerImplTest {

    @Test
    @DisplayName(" process only config classes")
    public void correctConfigClassTest(){
        Assertions.assertThrows( IllegalArgumentException.class, ()->new AppComponentsContainerImpl(Integer.class) );
    }

    @Test
    @DisplayName(" throw exception if there is no necessary object in configuration")
    public void brokenConfigTest(){
        Assertions.assertThrows( NonExistentComponentException.class, ()->new AppComponentsContainerImpl(BrokenAppConfig.class) );
    }
}