package com.cache.lru;


import com.cache.Cacheable;

import java.util.*;

public class LRUCache<K,V> implements Cacheable<K,V> {
    private static final int defaultCapacity = 1000;

    private int capacity;
    private Map<K, DataStorage<K,V>> cache;
    private LinkedList<DataStorage<K,V>> values;

    public class DataStorage<K,V> {
        private K key;
        private V value;

        private DataStorage(K key, V value) {
            this.key = key;
            this.value = value;
        }

        private V getValue() {
            return (V) value;
        }

        private void setValue(V value) {
            this.value = value;
        }

        private K getKey() {
            return (K) key;
        }

        private void setKey(K key) {
            this.key = key;
        }
    }

    public LRUCache(int capacity) {
        if (capacity <= 0) {
            capacity = defaultCapacity;
        }
        this.capacity = capacity;
        cache = new HashMap<>(getCapacity());
        values = new LinkedList<>();
    }

    public int getCapacity() {
        return capacity;
    }

    public V get(K key) {
        if (cache.containsKey(key)) {
            DataStorage<K,V> value = cache.get(key);
            values.remove(value);
            values.add(value);

            return value.getValue();
        }
        return null;
    }

    public void set(K key, V value) {
        if (cache.containsKey(key)) {
            DataStorage oldValue = cache.get(key);
            values.remove(oldValue);
            DataStorage<K, V> newValue = new DataStorage<>(key, value);
            values.add(newValue);
            cache.put(key, newValue);
        } else {
            DataStorage<K, V> data = new DataStorage(key, value);
            if (capacity == cache.size()) {
                cache.remove(values.removeFirst().getKey());
            }
            cache.put(key, data);
            values.add(data);
        }
    }
}
