package com.cache;

public interface Cacheable<K, V> {

    V get(K key);

    void set(K key, V value);

}

