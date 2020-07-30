package ru.otus.appcontainer;

public class NonExistentComponentException extends RuntimeException{
    public NonExistentComponentException(String message) {
        super(message);
    }
}
