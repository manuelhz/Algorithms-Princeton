import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Test {
    public static void main(String args[]) {
        // read graph from  input stream
        In in = new In(args[0]);
        Graph G = new Graph(in);

        // print out each edge (twice)
        for (int v = 0; v < G.V(); v++)
            for (int w : G.adj(v))
                StdOut.println (v + "-" + w);
        StdOut.println("Deep first path: ");
        BreadthFirstPaths a = new BreadthFirstPaths(G, 0);
        for (int i : a.pathTo(3))
            StdOut.println(i);
        StdOut.println("connected components: ");
        CC c = new CC(G);
        StdOut.println("0-6 : " + c.connected(0, 6));
        StdOut.println("0-7 : " + c.connected(0, 7));
        StdOut.println("Deep first search no recursion:");
        DFSNR d = new DFSNR(G, 0);
        for (int i : d.pathTo(3))
            StdOut.println(i);
        StdOut.println("Longest path:");
        Diameter dd = new Diameter();
        for (int i : dd.diameter(G))
            StdOut.println(i);
    }
}
