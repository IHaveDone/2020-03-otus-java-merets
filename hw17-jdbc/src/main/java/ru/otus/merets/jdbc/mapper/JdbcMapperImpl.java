/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.merets.jdbc.DbExecutor;
import ru.otus.merets.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);
    private final EntityClassMetaData<T> entityClassMetaData;
    private final EntitySQLMetaData entitySQLMetaData;
    private final DbExecutor<T> dbExecutor;
    private final SessionManagerJdbc sessionManager;

    public JdbcMapperImpl(DbExecutor<T> dbExecutor, SessionManagerJdbc sessionManager, Class clazz) {
        this.dbExecutor = dbExecutor;
        this.sessionManager = sessionManager;
        entityClassMetaData = new EntityClassMetaDataImpl<T>(clazz);
        entitySQLMetaData = new EntitySQLMetaDataImpl(clazz);
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    @Override
    public void insert(T objectData) {
        List<Object> values = new ArrayList<>();
        try {
            for (Field f : entityClassMetaData.getAllFields()) {
                values.add(f.get(objectData));
            }
            sessionManager.beginSession();
            dbExecutor.executeInsert(getConnection(), entitySQLMetaData.getInsertSql(), values);
            sessionManager.commitSession();
            logger.debug("Object was inserted in DB: {}", objectData.toString());
        } catch (SQLException e) {
            logger.error("Error in SQL: ", e);
        } catch (IllegalAccessException e) {
            logger.error("Unbelievable error! We know that we set this field accessable.", e);
        }
    }

    @Override
    public void update(T objectData) {
        long id;
        List<Object> values = new ArrayList<>();

        try {
            for (Field f : entityClassMetaData.getFieldsWithoutId()) {
                values.add(f.get(objectData));
            }
            id = Long.valueOf(entityClassMetaData.getIdField().get(objectData).toString());
            sessionManager.beginSession();
            dbExecutor.executeUpdate(getConnection(), entitySQLMetaData.getUpdateSql(), values, id);
            sessionManager.commitSession();
            logger.debug("Object was updated in DB: {}", objectData.toString());
        } catch (IllegalAccessException e) {
            logger.error("Unbelievable error! We know that we set this field accessable.", e);
        } catch (SQLException e) {
            logger.error("Error in SQL: ", e);
        }
    }

    @Override
    public void insertOrUpdate(T objectData) {
        try {
            long id = Long.valueOf(entityClassMetaData.getIdField().get(objectData).toString());
            T obj = findById(id, objectData.getClass());
            if (obj == null) {
                insert(objectData);
            } else {
                update(objectData);
            }
        } catch (IllegalAccessException e) {
            logger.error("Unbelievable error! We know that we set this field accessable.", e);
        }
    }

    @Override
    public T findById(long id, Class clazz) {
        final Constructor<T> constructor = entityClassMetaData.getConstructor();
        Optional<T> extractedObj;
        try {
            sessionManager.beginSession();
            extractedObj = dbExecutor.executeSelect(getConnection(), entitySQLMetaData.getSelectByIdSql(), id,
                    rs -> {
                        try {
                            if (rs.next()) {
                                T obj = constructor.newInstance();
                                for (Field f : entityClassMetaData.getAllFields()) {
                                    f.setAccessible(true);
                                    f.set(obj, rs.getObject(f.getName()));
                                }
                                return obj;
                            }
                        } catch (Exception e) {
                            logger.error("Error during running lambda in findById: ", e);
                        }
                        return null;
                    });
            sessionManager.commitSession();
            T object = extractedObj.orElse(null);
            if (object != null) {
                logger.debug("Object was read from DB: {}", object);
            } else {
                logger.debug("Object with `id={}` was not found", id);
            }
            return object;
        } catch (SQLException e) {
            logger.error("Error in SQL: ", e);
        }
        return null;
    }
}
