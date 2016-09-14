package com.kingleoric.maybe.http.cache;

/**
 * Created by qipu on 2016/8/8.
 */

public interface Cache<K,V> {
    public V get(K key);
    public void put(K key, V value);
    public void remove(K key);
}
