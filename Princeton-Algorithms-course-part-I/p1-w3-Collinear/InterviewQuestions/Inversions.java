import edu.princeton.cs.algs4.StdRandom;
public class Inversions {
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
    private static <T extends Comparable<? super T>> int merge(T[] a, T[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid); // precondition: a[lo..mid] sorted
        assert isSorted(a, mid+1, hi); // precondition: a[mid+q..hi] sorted

        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];

        int i = lo, j = mid + 1, count = 0;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
                count += (mid + 1) - i;
            }
            else {
                a[k] = aux[i++];
            }

        }
        assert isSorted(a, lo, hi); // postcondition a[lo..hi] sorted
        return count;
    }
    private static <T extends Comparable<? super T>> int sort(T[] a, T[] aux, int lo, int hi) {
        int inversionsCount = 0;
        if (hi <= lo)
            return inversionsCount;
        int mid = lo + (hi - lo) / 2;
        inversionsCount += sort(a, aux, lo, mid);
        inversionsCount += sort(a, aux, mid+1, hi);
        inversionsCount += merge(a, aux, lo, mid, hi);

        return inversionsCount;
    }

    public static <T extends Comparable<? super T>> int sort(T[] a) {
        T[] aux = (T[]) new Comparable[a.length];
        return sort(a, aux, 0, a.length - 1);
    }
    public static <T extends Comparable<? super T>> int inversions(T[] a) {
        int inversions = 0;
        for (int i = 0; i < a.length-1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (less(a[j], a[i]))
                    inversions++;
            }
        }
        return inversions;
    }
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Double[] test = new Double[N];
        for (int i = 0; i < N; i++)
            test[i] = StdRandom.uniform();
        System.out.println("number of inversions brute force: " + inversions(test));
        System.out.println("number of inversions merge sort: " + sort(test));
        for (int i = 0; i < test.length; i++)
            System.out.println(test[i]);
    }
}
