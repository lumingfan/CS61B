import java.util.Set;
import java.util.List;

import java.util.HashMap;
import java.util.LinkedList;


public class MyTrieSet implements TrieSet61B {
    private class Node {
        private boolean end;
        private HashMap<Character, Node> nexts;
        public Node() {
            end = false;
            nexts = new HashMap<>();
        }

        public void setEnd() {
            end = true;
        }

        public boolean isEnd() {
            return end;
        }

        public void setNext(Character index, Node next) {
            nexts.put(index, next);
        }

        public boolean hasNext(Character index) {
            return nexts.containsKey(index);
        }

        public Node getNext(Character index) {
            return nexts.get(index);
        }

        public Set<Character> keySet() {
            return nexts.keySet();
        }
    }

    private Node root;

    public MyTrieSet() {
        root = new Node();
    }

    @Override
    public void clear() {
        root.nexts.clear();
    }


    /** Returns true if the Trie contains KEY, false otherwise */
    @Override
    public boolean contains(String key) {
        Node cur = contains(key, 0, root);
        if (cur == null) return false;
        return cur.isEnd();
    }

    private Node contains(String key, int idx, Node cur) {
        if (idx == key.length() || cur == null) {
            return cur;
        }

        return contains(key, idx + 1, cur.getNext(key.charAt(idx)));
    }

    /** Inserts string KEY into Trie */
    @Override
    public void add(String key) {
        add(key, 0, root);
    }

    private void add(String key, int idx, Node cur) {
        if (idx == key.length()) {
            cur.setEnd();
            return;
        }

        char nextId = key.charAt(idx);
        if (!cur.hasNext(nextId)) {
            cur.setNext(nextId, new Node());
        }
        add(key, idx + 1, cur.getNext(nextId));
    }

    /** Returns a list of all words that start with PREFIX */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> returnList = new LinkedList<>();
        Node preNode = contains(prefix, 0, root);
        collect(prefix, preNode, returnList);
        return returnList;
    }

    private void collect(String path, Node cur, List<String> res) {
        if (cur == null) {
            return;
        }
        for (char ch : cur.keySet()) {
            collect(path + ch, cur.getNext(ch), res);
        }
        if (cur.isEnd()) res.add(path);
    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) {
        Node cur = root;
        for (int i = 0; i < key.length(); ++i) {
            Character index = key.charAt(i);
            if (cur.hasNext(index)) {
                cur = cur.getNext(index);
            } else {
                return key.substring(0, i);
            }
        }
        return null;
    }
}
