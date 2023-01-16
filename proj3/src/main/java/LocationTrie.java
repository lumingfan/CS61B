import java.util.HashMap;
import java.util.Map;

import java.util.List;
import java.util.LinkedList;

import java.util.Set;
import java.util.HashSet;


public class LocationTrie {
    private TrieNode root;
    public LocationTrie() {
        this.root = new TrieNode();
    }

    public void add(Long id, String name) {
        add(root, id, name, 0);
    }

    private void add(TrieNode nowNode, Long id, String name, int index) {
        if (index == name.length()) {
            nowNode.setEnd(true);
            nowNode.setName(name);
            nowNode.addID(id);
            return;
        }
        add(getNextNode(nowNode, name.charAt(index)), id, name, index + 1);
    }

    private TrieNode getNextNode(TrieNode node, char ch) {
        if (ch == ' ') {
            return node;
        }
        if (!node.next.containsKey(lower(ch))) {
            node.next.put(lower(ch), new TrieNode());
        }
        return node.next.get(lower(ch));
    }

    private TrieNode getNextNodeNoCreate(TrieNode node, char ch) {
        if (ch == ' ') {
            return node;
        }
        return node.next.get(lower(ch));
    }

    private char lower(char ch) {
        return Character.toLowerCase(ch);
    }

    public List<String> getPrefix(String prefix) {
        TrieNode node = find(root, prefix, 0);
        List<String> list = new LinkedList<>();
        if (node == null) {
            return list;
        }
        getAllName(node, list);
        return list;
    }

    public Set<Long> getFull(String fullName) {
        TrieNode node = find(root, fullName, 0);
        if (node == null || !node.isEnd()) {
            return new HashSet<>();
        }
        return node.getIdSet();
    }

    private TrieNode find(TrieNode node, String name, int index) {
        if (index == name.length() || node == null) {
            return node;
        }
        return find(getNextNodeNoCreate(node, name.charAt(index)), name, index + 1);
    }

    public List<String> getAllName() {
        List<String> paths = new LinkedList<>();
        getAllName(root, paths);
        return paths;
    }

    private void getAllName(TrieNode node, List<String> path) {
        if (node.isEnd()) {
            path.add(node.getName());
        }
        for (TrieNode next : node.next.values()) {
            getAllName(next, path);
        }
    }




    private class TrieNode {
        private boolean isEnd;
        private Map<Character, TrieNode> next;
        private String name;
        private Set<Long> idSet;

        TrieNode() {
            setEnd(false);
            next = new HashMap<>();
            setName("");
            idSet = new HashSet<>();
        }


        public boolean isEnd() {
            return isEnd;
        }

        public void setEnd(boolean end) {
            isEnd = end;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void addID(Long id) {
            this.idSet.add(id);
        }

        public Set<Long> getIdSet() {
            return this.idSet;
        }
    }
}
