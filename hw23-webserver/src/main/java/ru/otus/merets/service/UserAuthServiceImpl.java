package ru.otus.merets.service;

import ru.otus.merets.core.dao.UserDao;
import ru.otus.merets.core.service.DBServiceUser;

public class UserAuthServiceImpl implements UserAuthService {

    private final DBServiceUser dbServiceUser;

    public UserAuthServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return dbServiceUser.getUserByLoginAndPassword(login, password).isPresent();
    }

}
