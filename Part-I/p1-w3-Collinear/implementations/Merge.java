import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
public class Merge <T>{
    public static <T extends Comparable<? super T>> boolean less(T v, T w) {
        /* as before */
        return v.compareTo(w) < 0;
    }
    public static <T extends Comparable<? super T>> boolean isSorted(T[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++)
            if (less(a[i], a[i-1]))
                return false;
        return true;
    }
    private static <T extends Comparable<? super T>> void merge(T[] a, T[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid); // precondition: a[lo..mid] sorted
        assert isSorted(a, mid+1, hi); // precondition: a[mid+q..hi] sorted

        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
        assert isSorted(a, lo, hi); // postcondition a[lo..hi] sorted
    }
    private static <T extends Comparable<? super T>> void sort(T[] a, T[] aux, int lo, int hi) {

        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);

        sort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        T[] aux = (T[]) new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Double[] a = new Double[N];
        for (int i = 0; i < N; i++)
            a[i] = StdRandom.uniform();
        Merge.sort(a);
        for (int i = 0; i < N; i++)
            StdOut.println(a[i]);
    }
}



