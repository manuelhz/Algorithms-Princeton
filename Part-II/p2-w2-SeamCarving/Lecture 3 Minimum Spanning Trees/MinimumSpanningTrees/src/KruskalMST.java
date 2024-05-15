import edu.princeton.cs.algs4.*;

public class KruskalMST {
    private Queue<Edge> mst = new Queue<>();
    private double weight;
    // constructor
    public KruskalMST(EdgeWeightedGraph G) {
        MinPQ<Edge> pq = new MinPQ<>();
        for (Edge e : G.edges())
            pq.insert(e);
        UF uf = new UF(G.V());
        while (!pq.isEmpty() && mst.size() < G.V()-1) {
            Edge e = pq.delMin();
            int v = e.either(), w = e.other(v);
            if (uf.find(v) != uf.find(w)) {
                uf.union(v, w); // merge v and w component
                mst.enqueue(e); // add edge to mst
                weight += e.weight();
            }
        }
    }

    // edges in MST
    Iterable<Edge> edges() {
        return mst;
    }

    //weight of MST
    double weight(){
        return weight;
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        KruskalMST kruskalMst = new KruskalMST(G);
        for ( Edge e : kruskalMst.edges())
            StdOut.println(e);
        StdOut.printf("%.2f\n", kruskalMst.weight());
    }

}
