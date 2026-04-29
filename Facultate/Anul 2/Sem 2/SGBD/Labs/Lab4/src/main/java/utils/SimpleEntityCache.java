package utils;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class SimpleEntityCache<K, V> {

    private static class Entry<V> {
        final V value;
        final long expiresAt;
        Entry(V value, long expiresAt) { this.value = value; this.expiresAt = expiresAt; }
    }

    private final Map<K, Entry<V>> map = new ConcurrentHashMap<>();
    private final long ttlMillis;
    private final LongAdder hits = new LongAdder();
    private final LongAdder misses = new LongAdder();

    public SimpleEntityCache(long ttlMillis) {
        this.ttlMillis = ttlMillis;
    }

    public V get(K key) {
        Entry<V> e = map.get(key);
        if (e == null) { misses.increment(); return null; }
        if (Instant.now().toEpochMilli() > e.expiresAt) { map.remove(key); misses.increment(); return null; }
        hits.increment();
        return e.value;
    }

    public void put(K key, V value) {
        map.put(key, new Entry<>(value, Instant.now().toEpochMilli() + ttlMillis));
    }

    public void invalidate(K key) {
        map.remove(key);
    }

    public void clear() { map.clear(); }

    public long getHits() { return hits.longValue(); }
    public long getMisses() { return misses.longValue(); }
}

