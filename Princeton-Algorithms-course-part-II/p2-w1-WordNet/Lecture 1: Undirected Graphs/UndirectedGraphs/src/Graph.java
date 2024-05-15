import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
public class Graph {
    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    // create an empty graph with V vertices
    Graph(int V) {
        this.V = V;
        adj = (Bag<Integer>[]) new Bag[V]; // adjacency lists ( using Bag data type )
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }
    // create a graph from input stream
    Graph(In in) {
        this.V = in.readInt();
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v <V; v++)
            adj[v] = new Bag<Integer>();
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }
    void addEdge(int v, int w) {
        //add edge v-w  (parallel edges and  self-loops allowed)
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }
    // vertices adjacent to v
    Iterable<Integer> adj (int v) {
        return adj[v];
    }
    // number of vertices
    int V() {
        return V;
    }
    // number of edges
    int E() {
        return E;
    }

    /////
    ///////////////////////////
    // string representation
//    String toString() {}

    // Typical graph-processing code

    // compute the degree of v
    public static int degree(Graph G, int v) {
        int degree = 0;
        for (int w : G.adj(v)) degree++;
        return degree;
    }
    // compute maximum degree
    public static int maxDegree(Graph G) {
        int max = 0;
        for (int v = 0; v < G.V(); v++)
            if (degree(G, v) > max)
                max = degree(G, v);
        return max;
    }
    // compute average degree
    public static double averageDegree(Graph G) {
        return 2.0 * G.E() / G.V();
    }

    // count self-loops
    public static int numberOfSelfLoops(Graph G) {
        int count = 0;
        for (int v = 0; v < G.V(); v++)
            for (int w : G.adj(v))
                if(v == w) count++;
        return count/2; // each edge counted twice
    }

    public static void main(String args[]) {
        // read graph from  input stream
        In in = new In(args[0]);
        Graph G = new Graph(in);

        // print out each edge (twice)
        for (int v = 0; v < G.V(); v++)
            for (int w : G.adj(v))
                StdOut.println (v + "-" + w);
    }

}
