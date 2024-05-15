/*
DFS-iterative (G, s):                                   //Where G is graph and s is source vertex
      let S be stack
      S.push( s )            //Inserting s in stack
      mark s as visited.
      while ( S is not empty):
          //Pop a vertex from stack to visit next
          v  =  S.top( )
         S.pop( )
         //Push all the neighbours of v in stack that are not visited
        for all neighbours w of v in Graph G:
            if w is not visited :
                     S.push( w )
                    mark w as visited


    DFS-recursive(G, s):
        mark s as visited
        for all neighbours w of s in Graph G:
            if w is not visited:
                DFS-recursive(G, w)
https://www.hackerearth.com/practice/algorithms/graphs/depth-first-search/tutorial/#:~:text=This%20recursive%20nature%20of%20DFS,until%20the%20stack%20is%20empty.
 */
import java.util.Stack;

public class DFSNR {
    private boolean[] marked; //marked[v] = true if v connected to s
    private int[] edgeTo; // edgeTo[v] = previous vertex on path from s to v
    private int s;
    // find paths in G from source s
    DFSNR(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        Stack<Integer> n = new Stack<>();
        n.push(s);
        marked[s] = true;
        while(!n.isEmpty()) {
            int v = n.peek();
            n.pop();
            for (int w: G.adj(v))
                if (!marked[w]) {
                    edgeTo[w] = v;
                    n.push(w);
                    marked[w] = true;
                }
        }
    }
    public boolean hasPathTo(int v) {
        return marked[v];
    }
    // path from s to v; null if no such path
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }
}
