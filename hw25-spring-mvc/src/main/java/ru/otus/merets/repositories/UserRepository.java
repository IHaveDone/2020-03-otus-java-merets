package ru.otus.merets.repositories;


import ru.otus.merets.core.model.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User save(User user);

    User findById(long id);

    User findByName(String name);
}
