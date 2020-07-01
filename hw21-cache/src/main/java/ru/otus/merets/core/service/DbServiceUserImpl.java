package ru.otus.merets.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.merets.cachehw.HwListener;
import ru.otus.merets.cachehw.MyCache;
import ru.otus.merets.core.dao.UserDao;
import ru.otus.merets.core.model.User;

import javax.swing.text.html.Option;
import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);
    private final MyCache<String, User> cache = new MyCache<>();
    private final UserDao userDao;

    public DbServiceUserImpl(UserDao userDao) {
        this.userDao = userDao;
        cache.addListener(new HwListener<String, User>() {
            @Override
            public void notify(String key, User value, String action) {
                logger.info("Cash action ({}), object:{}", action, value);
            }
        });
    }

    @Override
    public long saveUser(User user) {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var userId = userDao.insertOrUpdate(user);
                sessionManager.commitSession();
                logger.info("User saved: {}", user);
                cache.put(String.valueOf(userId), user);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<User> getUser(long id) {
        Optional<User> userOptional = Optional.ofNullable(cache.get(String.valueOf(id)));

        if (!userOptional.isPresent()) {
            try (var sessionManager = userDao.getSessionManager()) {
                sessionManager.beginSession();
                try {
                    userOptional = userDao.findById(id);
                    sessionManager.commitSession();
                    logger.info("User was read: {}", userOptional.orElse(null));
                    userOptional.ifPresent(p -> cache.put(String.valueOf(id), p));
                    return userOptional;
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    sessionManager.rollbackSession();
                }
                return Optional.empty();
            }
        } else {
            logger.info("User with id '{}' was loaded from the cache", id);
        }
        return userOptional;
    }

    public Optional<User> getUserNoCache(long id) {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findById(id);
                sessionManager.commitSession();
                logger.info("User was read: {}", userOptional.orElse(null));
                userOptional.ifPresent(p -> cache.put(String.valueOf(id), p));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    public String getCacheInfo() {
        return cache.toString();
    }
}
