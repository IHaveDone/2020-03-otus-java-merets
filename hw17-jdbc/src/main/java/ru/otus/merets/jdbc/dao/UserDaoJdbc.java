package ru.otus.merets.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.merets.core.dao.UserDao;
import ru.otus.merets.core.model.User;
import ru.otus.merets.core.sessionmanager.SessionManager;
import ru.otus.merets.jdbc.mapper.JdbcMapperImpl;
import ru.otus.merets.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class UserDaoJdbc implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);
    private final JdbcMapperImpl<User> jdbcMapper;

    public UserDaoJdbc(JdbcMapperImpl<User> jdbcMapper) {
        this.jdbcMapper = jdbcMapper;
    }

    @Override
    public Optional<User> findById(long id) {
        User user = jdbcMapper.findById(id, User.class);
        return Optional.of(user);
    }

    @Override
    public long insertUser(User user) {
        jdbcMapper.insert(user);
        return user.getId();
    }

    @Override
    public void updateUser(User user) {
        jdbcMapper.update(user);
    }

    @Override
    public void insertOrUpdate(User user) {
        jdbcMapper.insertOrUpdate(user);
    }

    @Override
    public SessionManager getSessionManager() {
        return jdbcMapper.getSessionManager();
    }
}
