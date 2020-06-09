package ru.otus.merets.mygson;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Here and later we think that {@code MyGson} is a part of Json
 * which starts with '{' and ends with '}'.
 * {@code MyGson} parses an object (Object) and build a tree
 * that mirrors the object.
 */
public class MyGson {
    /**
     * Each MyGson can have some key->value pairs
     */
    private Map<String, Object> map;

    private final Class<?>[] defaults = {Integer.class, Character.class, Float.class, Double.class, Boolean.class, Byte.class, String.class,
            int.class, char.class, float.class, double.class, boolean.class, byte.class};
    private final Class<?>[] quotesClasses = {String.class, Character.class};

    /**
     * Determine is the {@code clazz} a basic class or not
     *
     * @param clazz Class
     * @return true if {@code clazz} is a basic Class (String, Integer, etc.)
     */
    private boolean isDefaultType(Class<?> clazz) {
        return Arrays.asList(defaults).contains(clazz);
    }

    /**
     * Actually I do not need default constructor, the task says
     * we should use it like MyGson myGson = new MyGson();
     * so I have to keep it.
     * I was wrong, default constructor is necessary for this case :-]
     */
    public MyGson() {
    }

    /**
     * Simple wrapper around the class
     *
     * @param obj {@code Object} that should be converted to Json
     * @return String in Json format
     */
    public String toJson(Object obj) {
        init(obj);
        return toString();
    }

    /**
     * Private constructor to make easier usage during parsing,
     * no {@code MyGson myGson = new MyGson(); myGson.toJson(object);}
     * just {@code new MyGson(Object);}
     *
     * @param object {@code Object} that should be translated to Json
     */
    private MyGson(Object object) {
        init(object);
    }

    /**
     * Parse Object[] value and put it into the map
     *
     * @param keyOfField - key
     * @param valueOfField - value
     */
    private void fillMapThroughObjectArray(String keyOfField, Object[] valueOfField) {
        if (Array.getLength(valueOfField) == 0) {
            map.put(keyOfField, null);
            return;
        }
        fillMapThroughCollection(keyOfField, Arrays.asList(valueOfField));
    }


    /**
     * Parse Array[] and put values into the map
     *
     * @param keyOfField
     * @param valueOfField
     */
    private void fillMapThroughDefaultArray(String keyOfField, Object[] valueOfField) {
        map.put(keyOfField, Arrays.asList(valueOfField));
    }

    /**
     * Iterate Collection and put values into the map
     *
     * @param keyOfField
     * @param col - Collection of objects
     */
    private void fillMapThroughCollection(String keyOfField, Collection<Object> col) {
        if (col.size() == 0) {
            map.put(keyOfField, null);
        }
        final List<Object> innerArray = new ArrayList<>();
        col.forEach((p) -> {
            if (isDefaultType(p.getClass())) {
                innerArray.add(p);
            } else {
                innerArray.add(new MyGson(p));
            }
        });
        map.put(keyOfField, innerArray);
    }

    /**
     * Takes {@code Object} and tries to build Json from it.
     *
     * @param object
     * @throws IllegalAccessException
     */
    private void init(Object object) {
        if (object == null) {
            return;
        }
        map = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> typeOfField = field.getType();
            Object valueOfField = null;
            try {
                valueOfField = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            String keyOfField = field.getName();

            if (valueOfField == null) {
                map.put(keyOfField, null);
            } else if (typeOfField.isArray()) {
                if (valueOfField instanceof Object[]) {
                    fillMapThroughObjectArray(keyOfField, (Object[]) valueOfField);
                } else {
                    fillMapThroughDefaultArray(keyOfField, (Object[]) valueOfField);
                }
            } else {
                if (isDefaultType(typeOfField)) {
                    map.put(keyOfField, valueOfField);
                } else if (Arrays.asList(typeOfField.getInterfaces()).contains(Collection.class)) {
                    fillMapThroughCollection(keyOfField, (Collection<Object>) valueOfField);
                } else {
                    map.put(keyOfField, new MyGson(valueOfField));
                }
            }
        }

    }

    /**
     * @param clazz
     * @return true if {@code clazz} should be shielded via quotes
     */
    private boolean needQuotes(Class<?> clazz) {
        return Arrays.asList(quotesClasses).contains(clazz);
    }

    /**
     * Convert the value to string format
     *
     * @param object
     * @return String for Json format
     */
    private String elementToString(Object object) {
        if (object == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        if (needQuotes(object.getClass())) {
            sb.append("\"").append(object.toString()).append("\"");
        } else {
            sb.append(object.toString());
        }
        return sb.toString();
    }

    /**
     * Convert {@code list} to Json format
     *
     * @param list
     * @return String for Json format (with delimiters and brackets)
     */
    private String listToString(List<Object> list) {
        return "[" + list.stream().map((p) -> elementToString(p)).collect(Collectors.joining(",")) + "]";
    }

    /**
     * Convert {@code MyGson} to String (Json format)
     *
     * @return Json-string
     */
    public String toString() {
        if (map == null || map.size() == 0) {
            return "{}";
        }

        StringBuilder json = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (json.length() > 0) {
                json.append(",");
            }
            json.append("\"").append(entry.getKey()).append("\":");
            if (entry.getValue() == null) {
                json.append("null");
            } else if (Arrays.asList(entry.getValue().getClass().getInterfaces()).contains(List.class)) {
                json.append(listToString((List<Object>) entry.getValue()));
            } else {
                json.append(elementToString(entry.getValue()));
            }
        }
        return "{" + json.toString() + "}";
    }

}