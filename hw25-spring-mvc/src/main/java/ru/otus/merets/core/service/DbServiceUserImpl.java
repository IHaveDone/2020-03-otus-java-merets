package ru.otus.merets.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.merets.cachehw.HwCache;
import ru.otus.merets.cachehw.MyCache;
import ru.otus.merets.core.repository.UserRepository;
import ru.otus.merets.core.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DbServiceUserImpl implements DBServiceUser {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);
    private final HwCache<String, User> cache;
    private final UserRepository userRepository;

    public DbServiceUserImpl(UserRepository userRepository, MyCache<String, User> cache) {
        this.userRepository = userRepository;
        this.cache = cache;
    }

    @Override
    public long saveUser(User user) {
        try (var sessionManager = userRepository.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var userId = userRepository.insertOrUpdate(user);
                sessionManager.commitSession();
                logger.info("User saved: {}", user);
                if (cache != null) {
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
        if (cache != null) {
            userOptional = Optional.ofNullable(cache.get(String.valueOf(id)));
        }
        if (userOptional.isEmpty()) {
            try (var sessionManager = userRepository.getSessionManager()) {
                sessionManager.beginSession();
                try {
                    userOptional = userRepository.findById(id);
                    sessionManager.commitSession();
                    logger.info("User was read by id: {}", userOptional.orElse(null));
                    if (cache != null) {
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

    /**
     * Wo do not use cache here. Why? Just because of time. Can we?
     * Yes, we just need to implement search by Login in cache.
     * @param login
     * @return
     */
    @Override
    public Optional<User> getUserByLoginAndPassword(String login, String password) {
        try (var sessionManager = userRepository.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userRepository.findByLoginAndPassword(login, password);
                sessionManager.commitSession();
                logger.info("User was read by login: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (var sessionManager = userRepository.getSessionManager()) {
            sessionManager.beginSession();
            try {
                List<User> users = userRepository.getAllUsers();
                sessionManager.commitSession();
                logger.info("Got all users: {}", users.size() );
                return users;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return new ArrayList<User>();
        }
    }

    public String getCacheInfo() {
        if (cache != null) {
            return cache.toString();
        } else {
            return "no cache";
        }
    }
}
