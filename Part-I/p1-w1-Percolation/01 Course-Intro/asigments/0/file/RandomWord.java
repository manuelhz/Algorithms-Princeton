import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
public class RandomWord {
    public static void main(String[] args){
        int i = 1;
        float p;
        String champion= "";
        String value;
        while(!StdIn.isEmpty()){
            p = 1/ ((float) i);
            value = StdIn.readString();
            if (StdRandom.bernoulli(p) == true){
                champion = value;
            }
            i++;
        }
        StdOut.println(champion);
    }
}
