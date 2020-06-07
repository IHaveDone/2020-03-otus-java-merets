/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.jdbc.mapper;

import org.junit.jupiter.api.*;
import ru.otus.merets.core.model.User;
import ru.otus.merets.h2.DataSourceH2;
import ru.otus.merets.h2.Schema;
import ru.otus.merets.jdbc.DbExecutorImpl;
import ru.otus.merets.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;

@DisplayName("JdbcMapperImpl must ")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcMapperImplTest {
    private User user1;
    private DataSource dataSource;
    private SessionManagerJdbc sessionManager;
    private JdbcMapperImpl<User> userJdbcMapper;

    @BeforeAll
    public void starter(){
        dataSource = new DataSourceH2();
        Schema.createTableUser(dataSource);
        Schema.createTableAccount(dataSource);
        sessionManager = new SessionManagerJdbc(dataSource);
        userJdbcMapper = new JdbcMapperImpl(new DbExecutorImpl<>(),sessionManager,User.class);
    }

    @BeforeEach
    public void init() {
        Schema.truncateAccount(dataSource);
        Schema.truncateUser(dataSource);
        user1 = new User(1, "Artem", 30);
    }

    @AfterAll
    public void finish(){
        sessionManager.close();
    }

    @Test
    @DisplayName(" insert into DB and read from DB")
    void insertAndReadTest() {
        userJdbcMapper.insert( user1 );
        User restoredUser = userJdbcMapper.findById(1,User.class);
        Assertions.assertEquals(user1, restoredUser);
    }

    @Test
    @DisplayName(" update data in DB")
    void updateTest(){
        userJdbcMapper.insert( user1 );
        user1.setAge(25);
        userJdbcMapper.update(user1);
        User restoredUpdatedUser = userJdbcMapper.findById(1,User.class);
        Assertions.assertEquals(25,restoredUpdatedUser.getAge());
    }

    @Test
    @DisplayName(" insert or update in DB")
    public void insertOrUpdateTest(){
        userJdbcMapper.insertOrUpdate( user1 );
        user1.setAge(25);
        userJdbcMapper.insertOrUpdate(user1);
        User restoredUpdatedUser = userJdbcMapper.findById(1,User.class);
        Assertions.assertEquals(25,restoredUpdatedUser.getAge());
    }

}