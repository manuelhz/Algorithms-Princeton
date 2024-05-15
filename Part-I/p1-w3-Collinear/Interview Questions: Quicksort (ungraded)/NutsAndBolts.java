public class NutsAndBolts {
    public static void printArray(char[] a) {
        for (char n: a)
            System.out.print(" " + n);
        System.out.println();
    }
    public static void bruteForce(char[] a, char[] b) {
        int i = 0, j = 0;
        while (i < b.length)
        if(a[i] == b[j]) {
            swap(b, i++, j);
            j = i;}
        else j++;
    }
    private static int partition(char[] a, int lo, int hi, char pivot) {
        int i = lo;
        for(int j = lo; j<hi; j++) {
            if (a[j] < pivot)
                swap(a, i++, j);
            else if (a[j] == pivot)
                swap(a, j--, hi);
        }
        swap(a, i, hi);
        return i;
    }
    private static boolean less(char x, char y) {
        if (x < y) return true;
        return false;
    }


    private static void sort2(char[] nuts, char[] bolts, int lo, int hi) {
        if (lo<hi) {
            int pivotLoc = partition(nuts, lo, hi, bolts[hi]);
            partition(bolts, lo, hi, nuts[pivotLoc]);

            sort2(nuts, bolts, lo, pivotLoc-1);
            sort2(nuts, bolts, pivotLoc+1, hi);
        }


    }
    public static void sort2(char[] nuts, char[] bolts) {
        int low =0, hi = nuts.length-1;
        sort2(nuts, bolts, low, hi);
    }


    public static void swap(char[] a, int i, int j) {
        char temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    public static void main(String[] args) {
        char nuts[] = {'@', '#', '$', '%', '^', '&'};
        char bolts[] = {'$', '%', '&', '^', '@', '#'};
        System.out.println("Nuts and bolts:");
        printArray(nuts);
        printArray(bolts);
        sort2(nuts, bolts);
        printArray(nuts);
        printArray(bolts);

    }
}
