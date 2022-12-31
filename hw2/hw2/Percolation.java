package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // used to form percolated path
    private final WeightedQuickUnionUF set;

    // used to judge if the site is open
    private final boolean[] open;

    // used to judge if the site is full(only for the set.find() site)
    private final boolean[] full;
    private final int N;
    private int openSites;

    // if the system percolate
    private boolean percolation = false;

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
        this.full = new boolean[N * N];
        this.set = new WeightedQuickUnionUF(N * N);
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
            full[index] = true;
        }

        if (row > 0) {
            int upIndex = rowColToIndex(row - 1, col);
            unionFull(index, upIndex);
        }
        if (row < N - 1) {
            int downIndex = rowColToIndex(row + 1, col);
            unionFull(index, downIndex);
        }
        if (col > 0) {
            int leftIndex = rowColToIndex(row, col - 1);
            unionFull(index, leftIndex);
        }
        if (col < N - 1) {
            int rightIndex = rowColToIndex(row, col + 1);
            unionFull(index, rightIndex);
        }
    }

    private void unionFull(int index, int adjIndex) {
        if (open[adjIndex]) {
            set.union(index, adjIndex);
        }
        if (full[adjIndex]) {
            full[set.find(index)] = true;
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
        boolean retuenBoolean = full[set.find(rowColToIndex(row, col))];
        if (row == N - 1 && retuenBoolean) {
            percolation = true;
        }
        return retuenBoolean;
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
        return percolation;
    }

    /**
     * use for unit testing(not required)
     */
    public static void main(String[] args) {

    }
}
