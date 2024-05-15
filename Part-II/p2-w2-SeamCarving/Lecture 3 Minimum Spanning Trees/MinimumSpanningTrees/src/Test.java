import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Test {
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        LazyPrimeMST mst = new LazyPrimeMST(G);
        for (Edge e : mst.edges())
            StdOut.println(e);
        StdOut.printf("%.2f\n", mst.weight());
    }
}
