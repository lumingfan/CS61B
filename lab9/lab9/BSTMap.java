package lab9;

import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            return getHelper(key, p.left);
        } else if (cmp > 0) {
            return getHelper(key, p.right);
        } else {
            return p.value;
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            return new Node(key, value);
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (cmp > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (get(key) == null) {
            ++size;
        }
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////


    private void keyCollection(Node cur, Collection<K> collector) {
        if (cur != null) {
            keyCollection(cur.left, collector);
            collector.add(cur.key);
            keyCollection(cur.right, collector);
        }
    }
    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> returnSet = new HashSet<>();
        keyCollection(root, returnSet);
        return returnSet;
    }

    private Node remove(Node cur, K key) {
        if (cur == null) {
            return null;
        }
        int cmp = key.compareTo(cur.key);
        if (cmp < 0) {
            cur.left = remove(cur.left, key);
        } else if (cmp > 0) {
            cur.right = remove(cur.right, key);
        } else {
            if (cur.left == null && cur.right == null) {
                cur = null;
            } else if (cur.right == null) {
                cur = cur.left;
            } else if (cur.left == null) {
                cur = cur.right;
            } else {
                Node right = cur.right;
                while (right.left != null) {
                    right = right.left;
                }
                right.left = cur.left;
                right.right = remove(cur.right, right.key);
                cur = right;
            }
        }
        return cur;
    }
    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        V returnValue = get(key);
        if (returnValue == null) {
            return null;
        }
        --size;
        root = remove(root, key);
        return returnValue;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V removeValue = get(key);
        if (removeValue != value) {
            return null;
        }
        return remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        Queue<K> queue = new LinkedList<>();
        keyCollection(root, queue);
        return queue.iterator();
    }
}
