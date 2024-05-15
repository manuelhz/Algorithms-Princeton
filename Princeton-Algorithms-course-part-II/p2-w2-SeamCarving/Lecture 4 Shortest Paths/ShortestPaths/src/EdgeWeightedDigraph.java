import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class EdgeWeightedDigraph {
    private final int V;
    private final Bag<DirectedEdge>[] adj;
    public EdgeWeightedDigraph(int V) {
        this.V = V;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<DirectedEdge>();
    }
    public int V() {
        return V;
    }
    public void addEdge(DirectedEdge e) {
        int v = e.from();
        adj[v].add(e);
    }
    public EdgeWeightedDigraph(In in) {
        this.V = in.readInt();
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<DirectedEdge>();
        }
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            addEdge(new DirectedEdge(v, w, weight));
        }
    }
    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }
}
