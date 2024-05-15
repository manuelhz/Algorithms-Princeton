import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class CanonicalUF {

    private int[] id;
    private int[] sz;
    private int[] max;

    public CanonicalUF(int N) {
        id = new int[N];
        sz = new int[N];
        max = new int[N];
        //set id of each object to itself
        //(2N array accesses)
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
            max[i] = i;
        }
    }
    private int root(int i) {
        //chase parent pointers until reach root
        //(depth of i array accesses)
        //Depth of any node x is at most lg N (lg = base-2 logarithm)
        while (i != id[i]) {
            max[i] = max[id[i]];
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
        if(max[i] > max[j]) {max[j] = max[i];   } else {max[i] = max[j];}
    }
    public int find(int p) {
        int maxComp = 0;
        int root = root(p);
        for (int i = 0; i < id.length; i++) {
            if(id[i] == root && i > maxComp) {
                maxComp = i;
            }
        }
        return maxComp;
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        CanonicalUF uf = new CanonicalUF(N);
        while (!StdIn.isEmpty()){
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.connected(p, q)) {

                uf.union(p, q);

            }
        }
        StdOut.println("find (0): " + uf.find(0));
        StdOut.println("find (0): " + uf.max[0]);
        StdOut.println("find (3): " + uf.find(3));
        StdOut.println("find (3): " + uf.max[3]);
        for(int i = 0; i< uf.id.length; i++) {
            StdOut.print(" " + uf.max[i]);
        }
    }
}
