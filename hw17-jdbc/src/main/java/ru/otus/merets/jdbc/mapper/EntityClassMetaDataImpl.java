/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData {
    private static final Logger logger = LoggerFactory.getLogger(EntityClassMetaDataImpl.class);
    private final Class<?> clazz;
    private Field id;
    private final String name;
    private final List<Field> fieldList;
    private final List<Field> fieldListNoId;
    private Constructor<T> constructor;

    private void parseFields() {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Id.class)) {
                if (id == null) {
                    id = field;
                    logger.debug("Class `{}`, Id value is {}", clazz.getName(), id.getName());
                } else {
                    logger.error("You have already found id field (current value is {}, new value is {}).", id.getName(), field.getName());
                    throw new EntityClassMetaDataImplManyIndexFieldsException("You have already found id field.", new Throwable());
                }
            } else {
                fieldListNoId.add(field);
            }
            fieldList.add(field);
        }
        if (id == null) {
            logger.error("There is no id field in class `{}`. You should add @Id annotation.", clazz.getName());
            throw new NoIdInClassException("No id field in class", new Throwable());
        }
    }

    private void parseConstructor(){
        try {
            constructor = (Constructor<T>) clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage());
            throw new EntityClassMetaDataImplConstructorNotFoundException("Default constructor not found", new Throwable());
        }
    }

    public EntityClassMetaDataImpl(Class clazz) {
        id = null;
        this.clazz = clazz;
        name = clazz.getSimpleName();
        fieldList = new ArrayList<>();
        fieldListNoId = new ArrayList<>();

        parseConstructor();
        parseFields();
    }

    @Override
    public String getName() {
        return name.toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return id;
    }

    @Override
    public List<Field> getAllFields() {
        return fieldList;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldListNoId;
    }
}
