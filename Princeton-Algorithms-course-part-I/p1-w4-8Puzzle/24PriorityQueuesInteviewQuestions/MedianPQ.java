import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;



public class MedianPQ<Key extends Comparable<Key>> {
    private int N = 0;
    private Key median;
    MinPQ minpq;
    MaxPQ maxpq;
    public MedianPQ(int capacity) {
        capacity = capacity + 1;
        minpq = new MinPQ(capacity/2);
        maxpq = new MaxPQ(capacity/2);
    }
    private void insert(Key k) {
        if(N == 0)
            median = k;
        else {
            if(k.compareTo(median) > 0)
                this.minpq.insert(k);
            if(k.compareTo(median) <= -1)
                this.maxpq.insert(k);
            balance();
        }
        N++;
    }
    private void balance() {
        if (this.minpq.size() - this.maxpq.size() > 1) {
            this.maxpq.insert(median);
            median = (Key)  this.minpq.delMin();
        }
        if (this.minpq.size() - this.maxpq.size() < -1) {
            this.minpq.insert(median);
            median = (Key)  this.maxpq.delMax();
        }
    }
    public Key removeMedian() {
        Key j = median;
        if (minpq.size() > maxpq.size()) {
            median = (Key) minpq.delMin();
        }
        else if (minpq.size() < maxpq.size()) {
            median = (Key) maxpq.delMax();
        }
        else if (minpq.size() == maxpq.size()) {
            if (minpq.getMin().compareTo(maxpq.getMax()) <=0)
                median = (Key) minpq.delMin();
            else median = (Key) maxpq.delMax();
        }
        N--;
        return j;
    }
    public static void main(String[] str) {
        MedianPQ medianpq = new MedianPQ(11);
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        StdRandom.shuffle(a);
        for(int i: a) {
            medianpq.insert(i);
        }
        StdOut.println(medianpq.removeMedian());
        StdOut.println(medianpq.removeMedian());
        StdOut.println(medianpq.removeMedian());
        StdOut.println(medianpq.removeMedian());
        StdOut.println(medianpq.removeMedian());
        StdOut.println(medianpq.removeMedian());
    }
}
