public class Edge implements Comparable<Edge> {
    private final int v, w;
    private final double weight;

    // create a weighted edge v-w
    Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
    // either endpoint
    int either() {
        return v;
    }

    // the endpoint that's not v
    int other(int vertex) {
        if (vertex == v) return w;
        else return v;
    }

    // compare this edge to that edgeby weight
    public int compareTo(Edge that) {
        if (this.weight < that.weight) return -1;
        else if (this.weight > that.weight) return +1;
        else return 0;
    }

    // the weight
    double weight() {
        return weight;
    }

    // string representation
    public String toString() {
        return v + "-" + w;
    }
}
