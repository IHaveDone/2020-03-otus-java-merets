package ru.otus.merets.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.merets.cachehw.HwListener;
import ru.otus.merets.cachehw.MyCache;
import ru.otus.merets.core.dao.UserDao;
import ru.otus.merets.core.model.User;

import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);
    private final MyCache<String, User> cache;
    private final UserDao userDao;

    public DbServiceUserImpl(UserDao userDao, MyCache<String,User> cache) {
        this.userDao = userDao;
        this.cache = cache;
    }

    @Override
    public long saveUser(User user) {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var userId = userDao.insertOrUpdate(user);
                sessionManager.commitSession();
                logger.info("User saved: {}", user);
                if(cache!=null) {
                    cache.put(String.valueOf(userId), user);
                }
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
        Optional<User> userOptional = Optional.ofNullable(null);
        if(cache!=null) {
            userOptional = Optional.ofNullable(cache.get(String.valueOf(id)));
        }

        if (userOptional.isEmpty()) {
            try (var sessionManager = userDao.getSessionManager()) {
                sessionManager.beginSession();
                try {
                    userOptional = userDao.findById(id);
                    sessionManager.commitSession();
                    logger.info("User was read: {}", userOptional.orElse(null));
                    if(cache!=null) {
                        userOptional.ifPresent(p -> cache.put(String.valueOf(id), p));
                    }
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

    public String getCacheInfo() {
        if(cache!=null) {
            return cache.toString();
        } else {
            return "no cache";
        }
    }
}
