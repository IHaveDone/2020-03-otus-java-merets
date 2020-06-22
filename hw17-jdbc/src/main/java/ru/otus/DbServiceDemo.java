package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.merets.core.model.Account;
import ru.otus.merets.core.model.User;
import ru.otus.merets.core.service.DbServiceUserImpl;
import ru.otus.merets.h2.DataSourceH2;
import ru.otus.merets.h2.Schema;
import ru.otus.merets.jdbc.DbExecutorImpl;
import ru.otus.merets.jdbc.dao.UserDaoJdbc;
import ru.otus.merets.jdbc.mapper.JdbcMapper;
import ru.otus.merets.jdbc.mapper.JdbcMapperImpl;
import ru.otus.merets.jdbc.sessionmanager.SessionManagerJdbc;

import java.math.BigDecimal;
import java.util.Optional;

public class DbServiceDemo {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) {
        var dataSource = new DataSourceH2();

        Schema.createTableUser(dataSource);
        Schema.createTableAccount(dataSource);

        var sessionManager = new SessionManagerJdbc(dataSource);

        JdbcMapperImpl<User> userJdbcMapper = new JdbcMapperImpl<User>(new DbExecutorImpl<User>(), sessionManager, User.class);
        var userDao = new UserDaoJdbc(userJdbcMapper);
        var dbServiceUser = new DbServiceUserImpl(userDao);
        var id = dbServiceUser.saveUser( new User(1, "Artem", 30) );
        Optional<User> user = dbServiceUser.getUser(id);
        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );

    }



}
