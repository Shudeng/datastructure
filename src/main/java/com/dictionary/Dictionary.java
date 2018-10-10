package com.dictionary;

/**
 * Created by Wushudeng on 2018/10/10.
 */
public interface Dictionary<K extends Comparable<? super K>, V> {
    int size();
    V get(K key);
    boolean put(K key, V value);
    boolean is_empty();
    boolean contains(K key);
    boolean remove(K key);
}
