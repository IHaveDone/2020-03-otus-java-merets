package ru.otus.config;

public class AppConfigInitializationException extends RuntimeException{
    public AppConfigInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
