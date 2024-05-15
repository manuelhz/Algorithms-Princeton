import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.*;

public class SWD extends HashSet<Integer> {

    public int successor(int x) {
        int suc = Integer.MAX_VALUE;
        int next;
        Iterator<Integer> iterate = this.iterator();
        while (iterate.hasNext()) {
            next = iterate.next();
            if(next > x && next < suc) suc = next;
        }
        return suc;
    }
    public static void main(String[] args) {
        SWD swd = new SWD();
        while (!StdIn.isEmpty()) {
            swd.add(StdIn.readInt());
        }
        StdOut.println(swd);
        swd.remove(17);
        StdOut.println(swd);
        StdOut.println(swd.successor(32));
    }
}