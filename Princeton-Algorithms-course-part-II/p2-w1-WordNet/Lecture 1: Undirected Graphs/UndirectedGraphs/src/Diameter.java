/*
Run BFS on any node s in the graph, remembering the node u discovered last.
Run BFS from u remembering the node v discovered last. d(u,v) is the diameter of the tree.
https://cs.stackexchange.com/questions/11263/longest-path-in-an-undirected-tree-with-only-one-traversal
 */
import edu.princeton.cs.algs4.StdRandom;

import java.util.Stack;

public class Diameter {
    private boolean[] marked; //marked[v] = true if v connected to s
    private int[] edgeTo; // edgeTo[v] = previous vertex on path from s to v


    private int s;
    static int last;
    public Iterable<Integer> diameter(Graph G) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        s = StdRandom.uniform(G.V());
        dfs(G, s); // find vertices connected to s
        s = last;
        dfs(G, s); // find vertices connected to s
        return pathTo(last);
    }
    private void dfs(Graph G, int v) {
        marked[v] = true;
        last = v;
        for (int w: G.adj(v))
            if (!marked[w]) {
                dfs(G, w);
                edgeTo[w] = v;
            }
    }
    public boolean hasPathTo(int v) {
        return marked[v];
    }
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }
}
