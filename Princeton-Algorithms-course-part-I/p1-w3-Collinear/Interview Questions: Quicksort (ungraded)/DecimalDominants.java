import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
//implementation using 3 ways quick sorting
public class DecimalDominants {
    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, gt = hi;
        Comparable v = a[lo];
        int i = lo;
        while (i <= gt){
            int cmp = a[i].compareTo(v);
            if (cmp < 0) exch(a, lt++, i++);
            else if (cmp > 0) exch(a, i, gt--);
            else i++;
        }
        int n = gt - lt + 1;
        if((n)>a.length/10)
        StdOut.println("The element " + a[gt] + " repeats " + (n) + " times");

        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }
    private static void sort(Comparable[] a) {
        int lo = 0 , hi = a.length-1;
        sort(a, lo, hi);
    }
    public static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    public static void main(String[] args) {
        Integer[] a = {1, 2, 1, 2, 1, 2, 1, 4, 3, 2, 4, 2, 3, 5, 6, 7, 8, 9, 3, 2, 4, 5, 2, 3, 6, 7, 8, 3, 2, 5};
        StdOut.println("Showing elements repeated more than " + a.length/10);
        sort(a);
    }
    
}
