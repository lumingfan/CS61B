public class UnionFind {

    // todo - Add instance variables?
    private int[] id;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        // TODO
        id = new int[n];
        for (int i = 0; i < n; ++i) {
            id[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        // todo
        if (vertex < 0 || vertex >= id.length) {
            throw new IllegalArgumentException();
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        // todo
        validate(v1);
        int vid = find(v1);
        return -id[vid];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        // todo
        validate(v1);
        if (id[v1] < 0) {
            return -1;
        }
        return find(v1);
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        // todo
        validate(v1);
        validate(v2);
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a
       vertex with itself or vertices that are already connected should not
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        // todo
        int pid = find(v1);
        int qid = find(v2);
        if (pid == qid) {
            return;
        }

        if (id[pid] > id[qid]) {
            id[qid] += id[pid];
            id[pid] = qid;
        } else {
            id[pid] += id[qid];
            id[qid] = pid;
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        // todo
        int[] help = new int[id.length];
        int index = 0;
        while (id[vertex] >= 0) {
            help[index++] = vertex;
            vertex = id[vertex];
        }
        for (int i = 0; i < index; ++i) {
            id[help[i]] = vertex;
        }
        return vertex;
    }
}