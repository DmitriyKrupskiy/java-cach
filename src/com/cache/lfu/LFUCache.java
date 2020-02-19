package com.cache.lfu;

import com.cache.Cacheable;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class LFUCache<K,V> implements Cacheable<K,V> {
    private static final int defaultCapacity = 1000;

    private HashMap<K, V> cache;
    private HashMap<K, Integer> frequencies;
    private HashMap<Integer, LinkedHashSet<K>> frequencyKeyMap;
    private int capacity;
    private int minFrequency;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        if (capacity <= 0) {
            this.capacity = defaultCapacity;
        }
        cache = new HashMap<>();
        frequencies = new HashMap<>();
        frequencyKeyMap = new HashMap<>();
        frequencyKeyMap.put(1, new LinkedHashSet<>());
    }

    @Override
    public V get(K key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        int count = frequencies.get(key);
        frequencies.put(key, count + 1);
        frequencyKeyMap.get(count).remove(key);

        if (count == minFrequency && frequencyKeyMap.get(count).size() == 0)
            minFrequency++;
        if (!frequencyKeyMap.containsKey(count + 1))
            frequencyKeyMap.put(count + 1, new LinkedHashSet<>());
        frequencyKeyMap.get(count + 1).add(key);
        return cache.get(key);
    }

    @Override
    public void set(K key, V value) {
        if (capacity <= 0) {
            return;
        }
        if (cache.containsKey(key)) {
            cache.put(key, value);
            get(key);
            return;
        }
        if (cache.size() >= capacity) {
            K evict = frequencyKeyMap.get(minFrequency).iterator().next();
            frequencyKeyMap.get(minFrequency).remove(evict);
            cache.remove(evict);
            frequencies.remove(evict);
        }
        cache.put(key, value);
        frequencies.put(key, 1);
        minFrequency = 1;
        frequencyKeyMap.get(1).add(key);
    }
}