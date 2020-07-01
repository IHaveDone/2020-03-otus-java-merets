package ru.otus.merets.core.service;

import ru.otus.merets.core.model.User;

import java.util.Optional;

public interface DBServiceUser {
    long saveUser(User user);
    Optional<User> getUser(long id);
}
