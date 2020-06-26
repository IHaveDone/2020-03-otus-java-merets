package ru.otus.cachehw;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {

    private final WeakHashMap<K,V> cache = new WeakHashMap<>();
    private final List<HwListener<K,V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        listeners.forEach( p->p.notify(key,value,"to put") );
        cache.put(key,value);
    }

    @Override
    public void remove(K key) {
        if(cache.containsKey(key)) {
            listeners.forEach(p -> p.notify(key, cache.get(key), "to remove"));
            cache.remove(key);
        }
    }

    @Override
    public V get(K key) {
        if(cache.containsKey(key)) {
            listeners.forEach(p -> p.notify(key, cache.get(key), "to get"));
            return cache.get(key);
        } else {
            listeners.forEach(p -> p.notify(key, null, "to get"));
            return null;
        }
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
