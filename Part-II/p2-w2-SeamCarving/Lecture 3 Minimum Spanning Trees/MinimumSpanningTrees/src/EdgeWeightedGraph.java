import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class EdgeWeightedGraph {
    private final int V;
    private int E;
    private final Bag<Edge>[] adj; // same as Graph, but adjacency lists of Edges instead of integers

    // create an empty graph with V vertices
    EdgeWeightedGraph(int V) {
        this.V = V;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Edge>();
    }
    public int V() {
        return V;
    }

    // create a graph from input stream
    EdgeWeightedGraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }

    // add weighted edge e to this graph
    void addEdge(Edge e) {
        int v = e.either(), w = e.other(v);
        // add edge to both adjacency list
        adj[v].add(e);
        adj[w].add(e);
    }

    // edges incident to v
    Iterable<Edge> adj(int v){
        return adj[v];
    }

    // all edges in this graph
    Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }
                // add only one copy of each self loop (self loops will be consecutive)
                else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }

    // number of vertices
    //int V() {}

    //string representation
//    String toString() {}
}
