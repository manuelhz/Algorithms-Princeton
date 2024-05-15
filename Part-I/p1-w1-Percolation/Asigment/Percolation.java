import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private int n; // grid size
    private int N; // number of objects
    private boolean[][] grid;
    private boolean virtualTopSite;
    private boolean virtualBottomSite;
    private WeightedQuickUnionUF uf;
    private int openSites;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if(n < 1) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        N = n * n + 2;
        grid = new boolean[this.n][this.n];
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
        virtualTopSite = true;
        virtualBottomSite = true;
        uf = new WeightedQuickUnionUF(N);

        this.openSites = 0;
    }
    // transform coordinates from grid(row, col) to ID(i)
    private int tc(int row, int col) {
        return (row-1) * this.n + col;
    }
    // transform coordinates from ID(i) to grid(row, col)
    private int[] ct(int z) {
        int[] w = new int[2];
        w[0] = (int)Math.ceil((float)z/this.n);
        w[1] = z - this.n * (w[0] - 1);
        return w;
    }
    private void unionUp(int row, int col) {
        if (isOpen(row - 1, col)) uf.union(this.tc(row, col), this.tc(row - 1, col));
    }
    private void unionDown(int row, int col) {
        if (isOpen(row + 1, col)) uf.union(this.tc(row, col), this.tc(row + 1, col));
    }
    private void unionLeft(int row, int col) {
        if (isOpen(row, col - 1)) uf.union(this.tc(row, col), this.tc(row, col - 1));
    }
    private void unionRight(int row, int col) {
        if (isOpen(row, col + 1)) uf.union(this.tc(row, col), this.tc(row, col + 1));
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // change of coordinates scale from 1 ... n to 0 ... n-1
        int r = row - 1;
        int c = col - 1;
        if(row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }

        if (!isOpen(row, col)){
            grid[r][c] = true;
            this.openSites++;
            if (row == 1 && row == this.n) {
                uf.union(0, this.tc(row, col));
                uf.union(N - 1, this.tc(row, col));
            } else
            if (row == 1) {
                uf.union(0, this.tc(row, col));
                unionDown(row, col);
            } else if (row == this.n) {
                uf.union(N - 1, this.tc(row, col));
                unionUp(row, col);
            } else {
                unionUp(row, col);
                unionDown(row, col);
            }
            if (col == 1 && col == this.n) {
                return;
            } else
            if (col == 1) {
                unionRight(row, col);
            } else if (col == this.n) {
                unionLeft(row, col);
            } else {
                unionLeft(row, col);
                unionRight(row, col);
            }
        }
        //
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if(row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("row:" + row + " col:" + col);
        }
        //
        return grid[row-1][col-1];
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if(row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
        return uf.find(0) == uf.find(this.tc(row, col));
    }
    // returns the number of open sites
    public int numberOfOpenSites() {
        //
        return openSites;
    }
    // does the system percolate?
    public boolean percolates() {
        //
        return uf.find(0) == uf.find(N - 1);
    }
    // test client (optional)
    public static void main(String[] args) {
        //
    }
}
