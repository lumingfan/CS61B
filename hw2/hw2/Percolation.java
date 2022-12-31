package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // used for judge if percolated
    private final WeightedQuickUnionUF set;

    // used for judge if full
    private final WeightedQuickUnionUF help;

    // used to judge if the site is open
    private final boolean[] open;

    private final int N;
    private int openSites;

    // N * N is the top virtual site, N * N + 1 is the bottom virtual site
    private final int topSite;
    private final int bottomSite;

    private int rowColToIndex(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException();
        }
        return row * N + col;
    }

    /**
     * create N-by-N grid, with all sites initially blocked
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        this.openSites = 0;
        this.open = new boolean[N * N];

        // N * N is the top virtual site, N * N + 1 is the bottom virtual site
        this.set = new WeightedQuickUnionUF(N * N + 2);
        this.help = new WeightedQuickUnionUF(N * N + 1);
        this.topSite = N * N;
        this.bottomSite = N * N + 1;
    }

    /**
     * open the site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        int index = rowColToIndex(row, col);
        if (this.open[index]) {
            return;
        }
        this.open[index] = true;
        ++this.openSites;

        if (row == 0) {
            set.union(index, topSite);
            help.union(index, topSite);
        }
        if (row == N - 1) {
            set.union(index, bottomSite);
        }

        if (row > 0) {
            int upIndex = rowColToIndex(row - 1, col);
            unionOpen(index, upIndex);
        }
        if (row < N - 1) {
            int downIndex = rowColToIndex(row + 1, col);
            unionOpen(index, downIndex);
        }
        if (col > 0) {
            int leftIndex = rowColToIndex(row, col - 1);
            unionOpen(index, leftIndex);
        }
        if (col < N - 1) {
            int rightIndex = rowColToIndex(row, col + 1);
            unionOpen(index, rightIndex);
        }
    }

    private void unionOpen(int index, int adjIndex) {
        if (open[adjIndex]) {
            set.union(index, adjIndex);
            help.union(index, adjIndex);
        }
    }

    /**
     * is the site (row, col) open ?
     */
    public boolean isOpen(int row, int col) {
        return this.open[rowColToIndex(row, col)];
    }

    /**
     * is the site (row, col) full ?
     */
    public boolean isFull(int row, int col) {
        return help.connected(topSite, rowColToIndex(row, col));
    }



    /**
     * number of open sites
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * dose the system percolate ?
     */
    public boolean percolates() {
        return set.connected(bottomSite, topSite);
    }

    /**
     * use for unit testing(not required)
     */
    public static void main(String[] args) {

    }
}
