import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Date;

public class SocialNetworkUF {
    private int[] id;
    private int[] sz;
    private int components;

    public SocialNetworkUF(int N) {
        id = new int[N];
        sz = new int[N];
        components = N;
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
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
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
        components = components - 1;
    }
    int count() {
        return components;
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        SocialNetworkUF uf = new SocialNetworkUF(N);
        StdOut.println("p q con date comps");
        while (!StdIn.isEmpty()){
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            String date = StdIn.readString();
            String time = StdIn.readString();
            String dateTime = date + " " + time;
            if (!uf.connected(p, q)) {
                StdOut.println(p + " " + q + " " + uf.connected(p, q) + dateTime + " " + uf.count());
                uf.union(p, q);
                StdOut.println(p + " " + q + " " + uf.connected(p, q) + " " + uf.count());
            } else {StdOut.println(p + " " + q + " " + uf.connected(p, q) + " " + uf.count());}
            if (uf.components==1) {
                StdOut.println("The earliest time at which all members are connected is " + dateTime);
                return;
            }
        }
    }
}