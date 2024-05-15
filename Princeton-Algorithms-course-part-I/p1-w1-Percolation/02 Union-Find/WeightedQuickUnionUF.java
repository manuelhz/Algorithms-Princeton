import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WeightedQuickUnionUF {
    private int[] id;
    private int[] sz;

    public WeightedQuickUnionUF(int N) {
        id = new int[N];
        sz = new int[N];
        //set id of each object to itself
        //(2N array accesses)
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }
    private int root(int i) {
        //chase parent pointers until reach root
        //(depth of i array accesses)
        //Depth of any node x is at most lg N (lg = base-2 logarithm)
        while (i != id[i]) i = id[i];
        return i;
    }
    //find:
    public boolean connected(int p, int q) {
        //takes time proportional to depth of p and q
        //check if p and q have same root
        //(depth of p and q array accesses)
        return root(p) == root(q);
    }
    public void union(int p, int q) {
        //Link root of smaller tree to root of larger tree.
        //Update the sz[] array.
        //takes constant time, given roots
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(N);
        while (!StdIn.isEmpty()){
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.connected(p, q)) {
                StdOut.println(p + " " + q + "connected:" + uf.connected(p, q));
                uf.union(p, q);
                StdOut.println(p + " " + q + "connected:" + uf.connected(p, q));
            } else {StdOut.println(p + " " + q + "connected:" + uf.connected(p, q));}
        }
    }
}
