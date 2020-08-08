package ru.otus.config;

public class IncorrectParamsAppComponent extends RuntimeException{
    public IncorrectParamsAppComponent(String message) {
        super(message);
    }
}
