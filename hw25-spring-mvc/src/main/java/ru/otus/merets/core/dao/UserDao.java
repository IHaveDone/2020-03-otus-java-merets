package ru.otus.merets.core.dao;

import ru.otus.merets.core.model.User;
import ru.otus.merets.core.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);
    Optional<User> findByLoginAndPassword(String login, String password);
    List<User> getAllUsers();
    long insertUser(User user);
    void updateUser(User user);
    long insertOrUpdate(User user);
    SessionManager getSessionManager();

}
