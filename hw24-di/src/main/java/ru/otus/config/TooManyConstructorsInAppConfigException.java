package ru.otus.config;

public class TooManyConstructorsInAppConfigException extends RuntimeException {
    public TooManyConstructorsInAppConfigException(String message) {
        super(message);
    }
}
