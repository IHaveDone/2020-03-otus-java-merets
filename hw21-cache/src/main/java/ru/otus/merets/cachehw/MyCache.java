package ru.otus.merets.cachehw;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private final Logger logger = LoggerFactory.getLogger(MyCache.class);
    private final WeakHashMap<K,V> cache = new WeakHashMap<>();
    private final List<HwListener<K,V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        listeners.forEach(p->p.notify(key,value, "before put"));
        cache.put(key, value);
        listeners.forEach(p->p.notify(key,value, "after put"));
        logger.debug("Key {} was added into the cache with value {}", key, value);
    }

    @Override
    public void remove(K key) {
        if(cache.containsKey(key)){
            listeners.forEach(p->p.notify(key, cache.get(key) , "before remove"));
            cache.remove(key);
            listeners.forEach(p->p.notify(key, null , "after remove"));
        } else {
            logger.warn("Attempt to remove wrong value: {}", key);
        }
    }

    @Override
    public V get(K key) {
        V value = null;
        if(cache.containsKey(key)) {
            listeners.forEach(p->p.notify(key, cache.get(key) , "before get"));
            value = cache.get(key);
            listeners.forEach(p->p.notify(key, cache.get(key) , "after get"));
        } else {
            logger.warn("Non-existent values was requested: {}", key);
        }
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        if(listener!=null) {
            listeners.add(listener);
            logger.debug("New listener was added");
        } else {
            logger.error("Attempt to add null instead of listener");
        }
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        if(listeners.contains(listener)){
            listeners.remove(listener);
            logger.debug("The listener was removed");
        } else {
            logger.warn("Attempt to remove non-existent listener");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"count\":");
        sb.append(cache.size());
        sb.append(", \"listeners\":\"");
        sb.append(listeners.size());
        sb.append("\"}");
        return sb.toString();
    }
}
