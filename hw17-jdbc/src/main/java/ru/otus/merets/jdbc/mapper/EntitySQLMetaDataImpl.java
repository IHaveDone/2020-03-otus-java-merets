/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private static final Logger logger = LoggerFactory.getLogger(EntitySQLMetaDataImpl.class);
    private final String selectAll;
    private final String selectById;
    private final String insert;
    private final String update;

    @Override
    public String getSelectAllSql() {
        return selectAll;
    }

    @Override
    public String toString() {
        return "EntitySQLMetaDataImpl{" +
                "selectAll='" + selectAll + '\'' +
                ", selectById='" + selectById + '\'' +
                ", insert='" + insert + '\'' +
                ", update='" + update + '\'' +
                '}';
    }

    public EntitySQLMetaDataImpl(Class clazz) {
        var metaData = new EntityClassMetaDataImpl(clazz);
        selectAll = "SELECT * FROM " + metaData.getName();
        selectById = "SELECT * FROM " + metaData.getName() + " WHERE " + metaData.getIdField().getName() + "=?";

        List<Field> fieldList = metaData.getAllFields();
        String fields = fieldList
                .stream()
                .map((p) -> p.getName().toLowerCase())
                .collect(Collectors.joining(","));

        String fieldPlaceholders = fieldList
                .stream()
                .map( p->"?")
                .collect(Collectors.joining(","));

        List<Field> fieldUpdateList = metaData.getFieldsWithoutId();
        String fieldUpdate = fieldUpdateList
                .stream()
                .map((p) -> p.getName().toLowerCase() + "=?")
                .collect(Collectors.joining(","));

        insert = "INSERT INTO " + metaData.getName() + "(" + fields + ") VALUES(" + fieldPlaceholders + ")";
        update = "UPDATE " + metaData.getName() + " SET " + fieldUpdate + " WHERE " + metaData.getIdField().getName() + "=?";

        logger.debug("INSERT: {}", insert);
        logger.debug("UPDATE: {}", update);
        logger.debug("SELECT ALL: {}", selectAll);
        logger.debug("SELECT BY ID: {}", selectById);
    }

    @Override
    public String getSelectByIdSql() {
        return selectById;
    }

    @Override
    public String getInsertSql() {
        return insert;
    }

    @Override
    public String getUpdateSql() {
        return update;
    }
}
