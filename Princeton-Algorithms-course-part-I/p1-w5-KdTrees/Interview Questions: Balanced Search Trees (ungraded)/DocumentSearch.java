import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class DocumentSearch {
    Integer[] search;
    DocumentSearch (String[] corpus, String[] query) {
        RedBlackBST<String, Queue<Integer>> st = new RedBlackBST<>();
        for(int i = 0; i < corpus.length; i++) {
            if (!st.contains(corpus[i])) {
                Queue<Integer> queue = new Queue<>();
                queue.enqueue(i);
                st.put(corpus[i], queue);
            }
            else {
                Queue<Integer> queue = st.get(corpus[i]);
                queue.enqueue(i);
                st.put(corpus[i], queue);
            }
        }
        int interval = Integer.MAX_VALUE;
        search = new Integer[query.length];
        a:
        while(!st.get(query[0]).isEmpty()) {
            Integer[] positions = new Integer[query.length];
            for(int i = 0; i < query.length; i++) {
                if (i == 0) {
                    positions[i] = st.get(query[i]).dequeue();
                }
                else {
                    Integer n = 0;
                    while(n <= positions[i - 1] ){
                        if(st.get(query[i]).isEmpty())
                            break a;
                        n = st.get(query[i]).dequeue();
                    }
                    positions[i] = n;
                }
            }
            if(positions[query.length-1] - positions[0] < interval) {
                search = positions;
                interval = positions[query.length-1] - positions[0];
            }
        }
    }
    public Integer[] show() {
        return search;
    }
    public static void main(String args[]){
        In in = new In(args[0]);
        String document = in.readAll();
        String words[] = new String[args.length-1];
        for(int i = 1; i < args.length; i ++)
            words[i-1] = args[i];
        String text[] = document.replaceAll("[^A-Za-z0-9 ]","").toLowerCase().split(" ");
        DocumentSearch ds = new DocumentSearch(text, words);
        for (Integer a: ds.show())
            StdOut.println(a);
    }
}
