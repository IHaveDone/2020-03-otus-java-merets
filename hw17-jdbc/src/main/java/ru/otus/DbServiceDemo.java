package ru.otus;

import ru.otus.merets.core.model.Account;
import ru.otus.merets.core.model.User;
import ru.otus.merets.h2.DataSourceH2;
import ru.otus.merets.h2.Schema;
import ru.otus.merets.jdbc.DbExecutorImpl;
import ru.otus.merets.jdbc.mapper.JdbcMapper;
import ru.otus.merets.jdbc.mapper.JdbcMapperImpl;
import ru.otus.merets.jdbc.sessionmanager.SessionManagerJdbc;

import java.math.BigDecimal;

public class DbServiceDemo {

    public static void main(String[] args) {
        var dataSource = new DataSourceH2();

        Schema.createTableUser(dataSource);
        Schema.createTableAccount(dataSource);

        var sessionManager = new SessionManagerJdbc(dataSource);

        User user1 = new User(1, "Artem", 30);
        User user2 = new User(2, "Elvira", 30);

        JdbcMapper<User> userJdbcMapper = new JdbcMapperImpl<User>(new DbExecutorImpl<User>(), sessionManager, User.class);
        userJdbcMapper.insert(user1);
        userJdbcMapper.insert(user2);

        User restoredUser1 = userJdbcMapper.findById(1, User.class);
        User restoredUser2 = userJdbcMapper.findById(2, User.class);

        Account account1 = new Account(1, "admin", new BigDecimal(100));
        Account account2 = new Account(2, "user", new BigDecimal(50));

        JdbcMapper<Account> accountJdbcMapper = new JdbcMapperImpl<Account>(new DbExecutorImpl<Account>(), sessionManager, Account.class);
        accountJdbcMapper.insert(account1);
        accountJdbcMapper.insert(account2);

        Account restoreAccount1 = accountJdbcMapper.findById(1, Account.class);
        Account restoreAccount2 = accountJdbcMapper.findById(2, Account.class);

    }



}
