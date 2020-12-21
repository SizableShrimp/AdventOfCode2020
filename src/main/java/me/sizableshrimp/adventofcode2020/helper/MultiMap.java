package me.sizableshrimp.adventofcode2020.helper;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MultiMap<K, V> implements Map<K, Set<V>> {
    private HashMap<K, Set<V>> map;

    public MultiMap() {
        this.map = new HashMap<>();
    }

    public MultiMap(Map<? extends K, ? extends Set<V>> m) {
        this.map = new HashMap<>(m);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    public Set<V> get(Object key) {
        return map.get(key);
    }

    public Set<V> getOrDefault(K key) {
        return map.computeIfAbsent(key, ignored -> new HashSet<>());
    }

    @Override
    public Set<V> put(K key, Set<V> value) {
        return map.put(key, value);
    }

    public Set<V> putValue(K key, V value) {
        Set<V> set = map.computeIfAbsent(key, ignored -> new HashSet<>());
        set.add(value);
        return set;
    }

    @Override
    public Set<V> remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends Set<V>> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<Set<V>> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, Set<V>>> entrySet() {
        return map.entrySet();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
