import java.util.Arrays;
import edu.princeton.cs.algs4.StdRandom;

public class MergingWithSmaller {
    public static boolean less(Comparable v, Comparable w) {
        /* as before */
        return v.compareTo(w) < 0;
    }

    public static void merge(Comparable[] a) {
        int lo, mid, hi;
        int n = a.length / 2;
        Comparable[] aux = new Comparable[n];

        for (int k = 0; k < n; k++)
            aux[k] = a[k];

        int i = 0, j = n;

        for (int k = 0; k < 2*n-1; k++) {
            if (i > n-1) break;
            else if (j > 2 * n) a[k] = a[j++];
            else if (less(a[j], aux[i])) a[k] = a[j++];
            else a[k] = aux[i++];
        }

    }
    public static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++)
            if (less(a[i], a[i-1]))
                return false;
        return true;
    }
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        Double[] a1 = new Double[n];
        Double[] a2 = new Double[n];
        Double[] a = new Double[2*n];
        for (int i = 0; i < n; i++){
            a1[i] = StdRandom.uniform();
            a2[i] = StdRandom.uniform();
        }
        Arrays.sort(a1);
        Arrays.sort(a2);
        for (int i = 0; i < n; i++) {
            a[i] = a1[i];
            a[i+n] =a1[i];
        }
        merge(a);
        for (int i = 0; i < 2*n; i++)
            System.out.println(a[i]);
        System.out.println("is sorted: " + isSorted(a, 1, 2*n-1));
    }
}
