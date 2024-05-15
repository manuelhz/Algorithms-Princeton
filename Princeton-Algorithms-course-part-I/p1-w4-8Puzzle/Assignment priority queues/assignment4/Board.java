import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.StdRandom;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
public class Board {
    private int[][] tiles;
    private int[][] goal;
    private int[][] twin = null;
    private int n;
    private int n2;
    private Integer manhattan = null;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.n2 = this.n * this.n;
        this.tiles = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }

        this.goal = new int[n][n];
        int counter = 1;
        for(int i = 0; i<n; i++)
            for(int j = 0; j < n; j++)
                goal[i][j] = counter++;
        goal[n-1][n-1] = 0;
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() { return n; }

    // number of tiles out of place
    public int hamming(){
        int hamming = 0;
        for(int i = 0; i < n-1; i++)
            for(int j = 0; j < n; j ++)
                if(this.tiles[i][j] != goal[i][j])
                    hamming++;
        for(int j = 0; j < n-1; j++)
            if(this.tiles[n-1][j] != goal[n-1][j])
                hamming++;
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if(manhattan == null) {
            manhattan = 0;
            int aux = 0;
            for(int i = 0; i < n; i++)
                loop:
                        for(int j = 0; j < n; j++) {
                            for(int ii = 0; ii < n; ii++){
                                for(int jj = 0; jj < n; jj++){
                                    if(goal[i][j] == this.tiles[ii][jj]){
                                        manhattan += (Math.abs(i - ii) + Math.abs(j - jj));
                                        if(++aux == n2-1) break loop;
                                    }
                                }
                            }
                        }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                if(tiles[i][j] != goal[i][j])
                    return false;
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        // self check
        if (this == y)
            return true;
        // null check
        if (y == null)
            return false;
        // type check and cast
        if (getClass() != y.getClass())
            return false;
        Board x = (Board) y;
        if(this.n != x.n)
            return false;
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                if(this.tiles[i][j] != x.tiles[i][j])
                    return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        // search the blank square
        int y = 0;
        int x = 0;
        search:
        for(y = 0; y < n; y++)
            for(x = 0; x < n; x++)
                if(tiles[y][x] == 0)
                    break search;

        List<Board> neighbors = new ArrayList<>();
        // possible moves
        int [][] top;
        int [][] left;
        int [][] bottom;
        int [][] right;

        if(y == 0) top = null;
        else top = new int[n][n];

        if(y == n - 1) bottom = null;
        else bottom = new int[n][n];

        if(x == 0) left = null;
        else left = new int[n][n];

        if(x == n - 1) right = null;
        else right = new int[n][n];

        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (top != null) top[i][j] = tiles[i][j];
                if (right != null) right[i][j] = tiles[i][j];
                if (bottom != null) bottom[i][j] = tiles[i][j];
                if (left != null) left[i][j] = tiles[i][j];
            }
        }
        if(left != null) {
            swap(left, y, x, y, x - 1); // left
            neighbors.add(new Board(left));
        }
        if(right != null) {
            swap(right, y, x, y, x + 1); // right
            neighbors.add(new Board(right));
        }
        if(top != null) {
            swap(top, y, x, y - 1, x); // top
            neighbors.add(new Board(top));
        }
        if(bottom != null) {
            swap(bottom, y, x, y + 1, x); // bottom
            neighbors.add(new Board(bottom));
        }
        return neighbors;
    }

    private static void swap(int[][] a, int i, int j, int k, int l) {
        int copy = a[i][j];
        a[i][j] = a[k][l];
        a[k][l] = copy;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if(twin == null) {
            this.twin = new int[n][n];
            for(int i = 0; i < n; i++)
                for(int j = 0; j < n; j++)
                    twin[i][j] = tiles[i][j];
            int i = 0,j = 0, k = 0, l = 0;
            do {
                do {
                    i = StdRandom.uniform(0, n);
                    j = StdRandom.uniform(0, n);
                } while(twin[i][j] == 0);
                do {
                    k = StdRandom.uniform(0, n);
                    l = StdRandom.uniform(0, n);
                } while(twin[k][l] == 0);

            } while (i == k && l == l);
            swap(twin, i, j, k, l);
        }
        return new Board(twin);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        StdOut.println("Initial:");
        StdOut.println(initial);
        Iterable<Board> l = initial.neighbors();
        StdOut.println("Neighbors:");
        for(Board i: l)
            StdOut.println(i);
        StdOut.println("hamming:" + initial.hamming());
        StdOut.println("manhattan:" + initial.manhattan());
        StdOut.println("Is goal:" + initial.isGoal());
        StdOut.println("Is goal:" + new Board(initial.goal) .isGoal());
    }
}
