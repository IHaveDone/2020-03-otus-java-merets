package ru.otus.merets.core.dao;

import java.util.Optional;

import ru.otus.merets.core.model.User;
import ru.otus.merets.core.sessionmanager.SessionManager;

public interface UserDao {
    Optional<User> findById(long id);
    long insertUser(User user);
    void updateUser(User user);

    /**
     * Changed return type from void to long. Why do we not to wait any return value? It is not logical.
     * I think we have to return id as well as we do in case of insert-method.
     * @param user
     * @return
     */
    long insertOrUpdate(User user);
    SessionManager getSessionManager();
}
