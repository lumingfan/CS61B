package lab9;

import javax.swing.text.Position;
import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }
    private void resize(int newSize) {
        ArrayMap<K, V>[] originBuckets = buckets;
        buckets = new ArrayMap[newSize];
        for (int i = 0; i < buckets.length; ++i) {
            buckets[i] = new ArrayMap<>();
        }


        size = 0;
        for (ArrayMap<K, V> bucket : originBuckets) {
            for (K key : bucket) {
                put(key, bucket.get(key));
            }
        }
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return buckets[hash(key)].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (loadFactor() >= MAX_LF) {
            resize(size * 2);
        }
        if (get(key) == null) {
            ++size;
        }
        buckets[hash(key)].put(key, value);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (ArrayMap<K, V> bucket : buckets) {
            for (K key : bucket) {
                set.add(key);
            }
        }
        return set;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        return buckets[hash(key)].remove(key);
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        return buckets[hash(key)].remove(key, value);
    }

    @Override
    public Iterator<K> iterator() {
        Queue<K> queue = new LinkedList<>();
        for (ArrayMap<K, V> bucket : buckets) {
            for (K key : bucket.keySet()) {
                queue.add(key);
            }
        }
        return queue.iterator();
    }
}
