import edu.princeton.cs.algs4.In;

import java.util.Stack;

public class DepthFirstPaths {
    private boolean[] marked; //marked[v] = true if v connected to s
    private int[] edgeTo; // edgeTo[v] = previous vertex on path from s to v
    private int s;
    // find paths in G from source s
    DepthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s); // find vertices connected to s
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w: G.adj(v))
            if (!marked[w]) {
                dfs(G, w);
                edgeTo[w] = v;
            }
    }
    // is there a path from s to v?
    public boolean hasPathTo(int v) {
        return marked[v];
    }
    // path from s to v; null if no such path
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }
}
