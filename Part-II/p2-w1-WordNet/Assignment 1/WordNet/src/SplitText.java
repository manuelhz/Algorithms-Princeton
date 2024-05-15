import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SplitText {
    public static void main(String args[]){
        In in = new In(args[0]);
        String document = in.readAll();
        String words[] = new String[args.length-1];
        for(int i = 1; i < args.length; i ++)
            words[i-1] = args[i];
        String text[] = document.replaceAll("[^A-Za-z0-9 ]","").toLowerCase().split(" ");
        Set<String> word = new HashSet<>(Arrays.asList(text));
        Out out = new Out(args[1]);
        for (String s : word)
            out.println(s);
    }
}
