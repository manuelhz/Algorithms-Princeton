import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
public class MinPQ <Key extends Comparable<Key>> {
    private Key[] pq;
    private int N;
    //create an empty priority queue
    MinPQ(int capacity) {
        pq = (Key[]) new Comparable[capacity+1];
    }
    //is the priority queue empty?
    boolean isEmpty() {
        return N == 0;
    }
    //insert a key into the priority queue
    public void insert(Key x) {
        pq[++N] = x;
        swim(N);
    }
    //return and remove the smallest key
    public Key delMin() {
        Key min = pq[1];
        exch(1, N--);
        sink(1);
        pq[N+1] = null;
        return min;
    }
    public Key getMin() {
        return pq[1];
    }
    private void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exch(k/2, k);
            k = k/2;
        }
    }
    private void sink(int k) {
        while (2*k <= N) {
            int j = 2*k;
            if (j < N && greater(j,j + 1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
    private boolean greater(int i, int j) {
        return pq[i].compareTo(pq[j]) > 0;
    }

    private void exch(int i, int j) {
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }
    public int size() {
        return N;
    }
    public static void main(String[] str) {
        int N;
        N = StdIn.readInt();
        MinPQ pq = new MinPQ(N);

        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            pq.insert(value);
            StdOut.print(value);
        }
        StdOut.println();
        while (!pq.isEmpty())
            StdOut.print(pq.delMin());
        StdOut.println();
    }

}
