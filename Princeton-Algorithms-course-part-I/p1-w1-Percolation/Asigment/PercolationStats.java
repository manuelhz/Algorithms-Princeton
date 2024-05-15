import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int n;
    private int T;
    private double[] results;
    // perform independents trials om an n-by-n grid
    public PercolationStats(int n, int trials) {
        if(n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.T = trials;
        Integer[] grid = new Integer[this.n*this.n];
        for (int i = 1; i <= this.n*this.n; i++) {
            grid[i-1] = i;
        }
        this.results = new double[this.T];
        for (int t = 0; t < this.T; t++) {
            StdRandom.shuffle(grid);
            Percolation percolation = new Percolation(n);
            int i = 0;
            while(!percolation.percolates()) {
                percolation.open(ct(grid[i])[0], ct(grid[i])[1]);
                i++;
            }
            this.results[t] = (double) i/(double) (this.n*this.n);
        }
    }
    // sample mean of percolation threshold
    private int[] ct(int z) {
        int[] w = new int[2];
        w[0] = (int)Math.ceil((float)z/this.n);
        w[1] = z - this.n * (w[0] - 1);
        return w;
    }
    public double mean() {
        return StdStats.mean(this.results);
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        // to do`
        return StdStats.stddev(this.results);
    }
    // low endpoint if 95% confidence interval
    public double confidenceLo() {
        return this.mean() - 1.96*this.stddev()/Math.sqrt((float)T);
    }
    // high endpoint if 95% confidence interval
    public double confidenceHi() {
        return this.mean() + 1.96*this.stddev()/Math.sqrt((float)T);
    }

    public static void main(String[] args) {
        PercolationStats percolationStats =
                new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.printf("mean %37s%n", "= " + percolationStats.mean());
        StdOut.printf("stddev %37s%n", "= " + percolationStats.stddev());
        StdOut.printf("95 confident interval %20s%n", "= [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}
