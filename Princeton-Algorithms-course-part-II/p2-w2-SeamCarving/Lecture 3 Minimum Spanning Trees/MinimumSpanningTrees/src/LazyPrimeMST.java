import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

public class LazyPrimeMST {
    private boolean[] marked; // MST vertices
    private Queue<Edge> mst; // MST edges
    private MinPQ<Edge> pq; // PQ of edges
    private double weight;

    public LazyPrimeMST(EdgeWeightedGraph G) {
        pq = new MinPQ<Edge>();
        mst = new Queue<Edge>();
        marked = new boolean[G.V()];
        visit(G, 0); // assume G is connected
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either(), w = e.other(v);
            if (marked[v] && marked[w]) continue;
            mst.enqueue(e); // add edge e to tree
            weight += e.weight();
            if (!marked[v]) visit(G, v); // add v or w to tree
            if (!marked[w]) visit(G, w);
        }
    }
    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true; // add v to T
        for (Edge e : G.adj(v)) // for each edge e = v - w, add to PQ if w not already in T
            if (!marked[e.other(v)])
                pq.insert(e);
    }
    public Iterable<Edge> mst() {
        return mst;
    }
    Iterable<Edge> edges() {
        return mst;
    }
    public double weight() {
        return weight;
    }
}
