package hw2;

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double mean;
    private double dev;

    private int T;


    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf)   {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        double[] counts = new double[T];

        for (int i = 0; i < T; ++i) {
            Percolation percolation = pf.make(N);
            int cnt = 0;
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                while (percolation.isOpen(row, col)) {
                    row = StdRandom.uniform(N);
                    col = StdRandom.uniform(N);
                }
                percolation.open(row, col);
                for (int j = 0; j < N; ++j) {
                    if (percolation.isFull(N - 1, j)) {
                        break;
                    }
                }
                ++cnt;
            }
            counts[i] = 1.0 * cnt / (N * N);
        }

        this.mean = mean(counts, T);
        this.dev = stddev(counts, T, this.mean);
        this.T = T;
    }


    private double mean(double[] counts, int T) {
        double sum = 0;
        for (double count : counts) {
            sum += count;
        }
        return sum / T;
    }

    private double stddev(double[] counts, int T, double mean) {
        double sum = 0.0;
        for (double count : counts) {
            sum += Math.pow(count - mean, 2);
        }
        return Math.sqrt(sum / (T - 1));
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.dev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return this.mean - 1.96 * this.dev / Math.sqrt(this.T);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return this.mean + 1.96 * this.dev / Math.sqrt(this.T);
    }


    /*public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(20, 5000, new PercolationFactory());
        System.out.println(ps.mean());
    }*/
}
