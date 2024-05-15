import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
public class RandomPQ {
    public static void main(String[] str) {
        int N = 11;
        MaxPQ maxpq = new MaxPQ(N);
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

        for(int i: a) {
            maxpq.insert(i);
        }
        for(int i = 0; i < N; i++)
            StdOut.print(" "+maxpq.getRand());
        StdOut.println();
        while(!maxpq.isEmpty())
            StdOut.print(" "+maxpq.delRand());
        StdOut.println();
    }
}
