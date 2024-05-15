import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private BreadthFirstDirectedPaths bfs1;
    private BreadthFirstDirectedPaths bfs2;
    private Digraph G;


    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        this.G = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        if (v < 0 || v >= G.V() || w < 0 || w >= G.V()) throw new IllegalArgumentException();

        return bfs(v, w)[0];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || v >= G.V() || w < 0 || w >= G.V()) throw new IllegalArgumentException();

        return bfs(v, w)[1];
    }
    private int [] bfs(int v, int w) {
        bfs1 = new BreadthFirstDirectedPaths(G, v);
        bfs2 = new BreadthFirstDirectedPaths(G, w);
        return bfs(bfs1, bfs2);
    }
    private int[] bfs(Iterable<Integer> v, Iterable<Integer> w) {
        bfs1 = new BreadthFirstDirectedPaths(G, v);
        bfs2 = new BreadthFirstDirectedPaths(G, w);
        return bfs(bfs1, bfs2);
    }
    private int[] bfs(BreadthFirstDirectedPaths bfs1, BreadthFirstDirectedPaths bfs2) {
        int minDist = -1;
        int[] ancestor = new int [2]; // ancestor[0,1] = [distMin, ancestor
        ancestor[0] = -1; // minimal distance
        ancestor[1] = -1; // ancestor with minimal distance
        for (int i = 0; i < G.V(); i++) {
            if(bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                if (ancestor[0] == -1) {
                    ancestor[0] = bfs1.distTo(i) + bfs2.distTo(i);
                    ancestor[1] = i;
                } else {
                    int dist = bfs1.distTo(i) + bfs2.distTo(i);
                    if (ancestor[0] > dist) {
                        ancestor[0] = dist;
                        ancestor[1] = i;
                    }
                }
            }
        }
        return ancestor;
    }



    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        for (Integer i : v) if (i == null || i < 0 || i >= G.V()) throw new IllegalArgumentException();
        for (Integer i : w) if (i == null || i < 0 || i >= G.V()) throw new IllegalArgumentException();

        return bfs(v, w)[0];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        for (Integer i : v) if (i == null || i < 0 || i >= G.V()) throw new IllegalArgumentException();
        for (Integer i : w) if (i == null || i < 0 || i >= G.V()) throw new IllegalArgumentException();

        return bfs(v, w)[1];
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}