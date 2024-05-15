import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.List;

public class TaxiCab implements Comparable<TaxiCab> {
    private int x;
    private int y;
    private float sum;
    /* ptc(n) obtains the first n TaxiCab numbers n^3 time complexity
     */
    public static void ptc(int n) {
        int count = 0;
        int i = 1;
        while (count < n) {
            List<Integer> num = new ArrayList<>();
            double s = Math.cbrt(i);
            for (int j = 1; j < s; j++) {
                for (int k = 1; k < s; k++) {
                    if (i == (j*j*j + k*k*k)){
                        num.add(j);
                        num.add(k);
                    }
                }
            }
            if(num.size() > 2 && ! (num.get(0) == num.get(3) ) ) {
                count++;
                StdOut.println("(" + count + ") " + i + " = " + num.get(0) + "^3 + " + num.get(1) + "^3 = " + num.get(2) + "^3 + " + num.get(3)+"^3");
            }
            i++;
        }
    }
    TaxiCab(int x, int y) {
        this.x = x;
        this.y = y;
        this.sum = (float) x*x*x + (float) y*y*y;
    }

    @Override
    public int compareTo(TaxiCab that) {
        if (this.sum > that.sum) return 1;
        else if (this.sum < that.sum) return -1;
        else if (!(this.x == that.y)) return 0;
        return -1;
    }
    // this method implements tacicab numbers in log(n) n^2 time complexity but it has an error
    public static void numbers(int n) {
        MinPQ<TaxiCab> pq = new MinPQ<>(n*n);
        for (int i = 1; i < n; i++)
            for (int j = 1; j < n; j++)
                pq.insert(new TaxiCab(i, j));
        TaxiCab prev = new TaxiCab(0, 0);

        while(!pq.isEmpty()) {
            TaxiCab current = pq.delMin();
            if (current.compareTo(prev) == 0){
                StdOut.print(prev.sum + " = ");
                StdOut.print(prev.x + "^3 " + prev.y + "^3 = ");
                StdOut.print(current.x + "^3 " + current.y + "^3");
                StdOut.println();
            }
            prev = current;
        }
    }



    public static void main(String[] str) {
        //ptc(Integer.parseInt(str[0]));
        numbers(Integer.parseInt(str[0]));
    }
}
