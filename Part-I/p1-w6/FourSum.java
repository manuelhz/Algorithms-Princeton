/*
4-SUM. Given an array a[] of n integers, the 4-SUM problem is to determine if there exist
distinct indices i, j, k, and l such that a[i] + a[j] = a[k] + a[l].
Design an algorithm for the 4-SUM problem that takes time proportional to n^2
(under suitable technical assumptions).
 */
import java.util.TreeSet;
import java.util.Set;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.List;
public class FourSum {
    int i, j, k, l;
    double sum;
    public FourSum(int i, int j, int k, int l, int sum) {
        this.i = i;
        this.j = j;
        this.k = k;
        this.l = l;
        this.sum = sum;
    }
    public String toString() {
        return i + " + " + j + " = " + k + " + " + l + " = " + sum;
    }

    public static void solve(int[] nums) {
        Hashtable<Integer, HashSet<List<Integer>>> store = new Hashtable<>();

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                int sum = nums[i] + nums[j];
                HashSet getSum = store.get(sum);
                if (getSum == null) {
                    HashSet<List<Integer>> l = new HashSet<>();
                    //l.add(List.of(i, j));
                    l.add(List.of(i, j));
                    store.put(sum, l);
                }
                else {
                    getSum.add (List.of(i, j));
                    store.put (sum, getSum);
                }
            }
        }
        Enumeration w = store.keys();
        while (w.hasMoreElements()) {
            Integer n = (Integer) w.nextElement();
            HashSet x = (HashSet) store.get(n);
            if (x.size() > 1) {
                System.out.println(n +" " + x);
                break;
            }



        }


        //return new FourSum(i, j, i, j, nums[i] + nums[j]);

    }

    public static FourSum solveBruteForce(int[] nums) {
        ArrayList<FourSum> a = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                for (int k = j+1; k < nums.length; k++) {
                    for (int l = k+1; l < nums.length; l++) {
                        if (allConditions(nums, i, j, k, l))
                            return new FourSum(i, j, k, l, nums[i] + nums[j]);
                    }
                }
            }
        }
        return null;
    }
    private static boolean allConditions(int[] nums, int i, int j, int k, int l) {
        return (double) nums[i] + nums[j] == (double) nums[k] + nums[l]
                && i != j
                && i != k
                && i != l
                && j != k
                && j != l
                && k != l;
    }

    public static void main(String[] args) {
        //int[] nums = {10, 2, 3, 4, 5, 9, 7, 8};
        int[] nums = {2, 7, 4, 0, 9, 5, 1, 3};
        System.out.println(solveBruteForce(nums));
        solve(nums);
    }
}
