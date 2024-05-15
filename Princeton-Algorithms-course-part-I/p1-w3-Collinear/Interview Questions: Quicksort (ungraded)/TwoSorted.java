import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Random;

public class TwoSorted {
    public static String[] genVec(int n) {
        String [] vec = new String[n];
        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            int aleatorio = 97 + rnd.nextInt(26);
            vec[i] = String.valueOf((char) aleatorio);
        }
        return vec;
    }
    public static void printArray(Comparable[] a) {
        for (Comparable n: a)
            System.out.print(" " + n);
        System.out.println();
    }
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi+1;
        while (true) {
            //find item on left to swap
            while (less(a[++i], a[lo]))
                if (i == hi) break;

            // find item on right to swap
            while (less(a[lo], a[--j]))
                if(j == lo) break;
            // check if pointers cross
            if (i >= j) break;
            // swap
            exch(a, i, j);
        }
        //swap with partitioning item
        exch(a, lo, j);
        // return index of item now known to be in place
        return j;
    }
    public static Comparable select(Comparable[] a1, Comparable[] a2, int k) {
        int n1 = a1.length;
        int n2 = a2.length;
        int n = n1 + n2;
        Comparable[] a = new Comparable[n];
        for(int i = 0; i < n1; i++) {
            a[i] = a1[i];
        }
        for(int i = 0, j = n1; j < n; i++, j++) {
            a[j] = a2[i];
        }
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length - 1;
        while (hi > lo) {
            int j = partition(a, lo, hi);
            if (j < k) lo = j + 1;
            else if (j > k) hi = j - 1;
            else return a[k];
        }
        return a[k];
    }
    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    public static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    public static void main(String[] args) {
        int k = Integer.valueOf(args[0]);
        String[] a1 = genVec(5);
        String[] a2 = genVec(7);
        printArray(a1);
        printArray(a2);
        StdOut.println("k=" + k);
        StdOut.println(select(a1, a2, k));
    }
}