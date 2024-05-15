import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solver {
    private int moves;
    private List<Board> solution = new ArrayList<>();
    private boolean isSolvable;
    private Board initial;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if(initial == null)
            throw new IllegalArgumentException();
        this.initial = initial;
        MinPQ<SearchNode> mpq = new MinPQ<>(1);
        MinPQ<SearchNode> mpqTwin = new MinPQ<>(1);

        mpq.insert(new SearchNode(initial, null));
        mpqTwin.insert(new SearchNode(initial.twin(), null));

        SearchNode aux; // father node
        SearchNode auxTwin; // father node

        SearchNode optimization; // store previous father

        while (!mpq.isEmpty()) {
            aux = mpq.delMin();
            auxTwin = mpqTwin.delMin();

            if(aux.board.isGoal()) {
                isSolvable = true;
                this.moves = aux.moves;
                Stack<Board> s = new Stack<>();
                while(true) {
                    s.push(aux.board);
                    if(aux.previousNode == null) break;
                    aux = aux.previousNode;
                }
                while(!s.isEmpty())
                    solution.add(s.pop());
                break;
            }

            if(auxTwin.board.isGoal()) {
                isSolvable = false;
                solution = null;
                this.moves = -1;
                return;
            }

            for(Board n: aux.board.neighbors()){
                if(aux.previousNode == null || !n.equals(aux.previousNode.board))
                    mpq.insert(new SearchNode(n, aux));
            }
            for(Board n: auxTwin.board.neighbors()){
                if(auxTwin.previousNode == null || !n.equals(auxTwin.previousNode.board))
                    mpqTwin.insert(new SearchNode(n, auxTwin));
            }

        }
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {return isSolvable;}

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {return this.moves;}

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        StdOut.println("Minimum number of moves = " + solver.moves());
        for(Board w: solver.solution())
            StdOut.println(w);

        StdOut.println("Unsolvable puzzle");
    }
}

class SearchNode implements Comparable<SearchNode>{
    Board board;
    SearchNode previousNode;
    int manhattan;
    int moves;
    int priority;
    SearchNode(Board board, SearchNode previousNode) {
        this.previousNode = previousNode;
        this.board = board;
        manhattan = board.manhattan();
        if(previousNode == null)
            moves = 0;
        else moves = previousNode.moves + 1;
        priority = manhattan + moves;
    }
    public int compareTo(SearchNode that) {
        if(this.priority > that.priority) return 1;
        if(this.priority < that.priority) return -1;
        return 0;
    }
}