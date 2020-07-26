package ru.otus.merets.service;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
