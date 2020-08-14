package ru.otus.merets.hibernate.dao;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.merets.core.repository.UserRepository;
import ru.otus.merets.core.repository.UserDaoException;
import ru.otus.merets.core.model.User;
import ru.otus.merets.core.sessionmanager.SessionManager;
import ru.otus.merets.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.merets.hibernate.sessionmanager.SessionManagerHibernate;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryHibernate implements UserRepository {
    private static Logger logger = LoggerFactory.getLogger(UserRepositoryHibernate.class);
    private final SessionManagerHibernate sessionManager;

    public UserRepositoryHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<User> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(User.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Query query = currentSession.getHibernateSession().createQuery("SELECT e FROM User e WHERE e.name = ?1 AND e.password=?2");
            query.setParameter(1, login);
            query.setParameter(2, password);
            return Optional.ofNullable((User) query.getSingleResult());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Query query = currentSession.getHibernateSession().createQuery("SELECT e FROM User e");
            return query.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<User>();
    }


    @Override
    public long insertUser(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.persist(user);
            hibernateSession.flush();
            return user.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void updateUser(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.merge(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public long insertOrUpdate(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (user.getId() > 0) {
                hibernateSession.merge(user);
            } else {
                hibernateSession.persist(user);
                hibernateSession.flush();
            }
            return user.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
