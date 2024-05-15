

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Topological;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Iterator;

public class WordNet {
    private Digraph G;
    private TreeMap<Integer, Set<String>> synsetsIntString = new TreeMap<>();
    private TreeMap<String, Set<Integer>> synsetsStringInt = new TreeMap<>();
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();
        In file1 = new In(synsets);
        In file2 = new In(hypernyms);
        // create symbol tables for synsets
        while(file1.hasNextLine()) {
            String line = file1.readLine();
            String[] tokens = line.split(",");
            Set<String> w = new LinkedHashSet<String>(Arrays.asList(tokens[1].split(" ")));
            Integer y = Integer.parseInt(tokens[0]);
            synsetsIntString.put(y, w);
            for(String k : w){
                if(synsetsStringInt.containsKey(k)) {
                    Set<Integer> h = synsetsStringInt.get(k);
                    h.add(y);
                    synsetsStringInt.put(k, h);
                } else {
                    Set<Integer> h = new HashSet<>();
                    h.add(y);
                    synsetsStringInt.put(k, h);
                }
            }
        }






        String[] s = file2.readAllLines();

        // create digraph of hypernyms
        G = new Digraph(s.length);
        for(int i = 0; i < s.length; i++) {
            String[] tokens = s[i].split(",");
            for(int j = 1; j < tokens.length; j++) {
                G.addEdge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[j]));
            }
        }
        // check if the graph is a rooted DAG
        // 1) it can't have cycles : check using Topological
        Topological topological = new Topological(G);
        if(!topological.hasOrder()) // if it has order is DAG
            throw new IllegalArgumentException();
        // 2) there must be only one vertex with zero output
        int zeroOut = 0; // count number of vertex whith zero output
        for (int i = 0; i < G.V(); i ++) {
            if (G.outdegree(i) == 0) zeroOut++;
        }
        if (zeroOut > 1) throw new IllegalArgumentException(); // if more than one vertex whith zero outdegree
    }
//    private WordNet(WordNet wordNet) {
//        this.G = new Digraph(wordNet.G);
//        this.synsetsIntString = new TreeMap<>();
//        for (Integer i : wordNet.synsetsIntString.keySet()) {
//            Set<String> s = new HashSet<>();
//            for (String j : wordNet.synsetsIntString.get(i))
//                s.add(j);
//            this.synsetsIntString.put(i, s);
//        }
//        this.synsetsStringInt = new TreeMap<>();
//        for (String i : wordNet.synsetsStringInt.keySet()) {
//            Set<Integer> s = new HashSet<>();
//            for (Integer j : wordNet.synsetsStringInt.get(i))
//                s.add(j);
//            this.synsetsStringInt.put(i, s);
//        }
//    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsetsStringInt.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();
        return synsetsStringInt.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();

        SAP sap = new SAP(G);
        Set<Integer> s = synsetsStringInt.get(nounA);
        Set<Integer> t = synsetsStringInt.get(nounB);
        if (s.size() == 1 && t.size() == 1)
            return sap.length(s.iterator().next(), t.iterator().next());
        else return sap.length(s, t);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        SAP sap = new SAP(G);
        Set<Integer> s = synsetsStringInt.get(nounA);
        Set<Integer> t = synsetsStringInt.get(nounB);
        int w;
        if (s.size() == 1 && t.size() == 1) {
            w = sap.ancestor(s.iterator().next(), t.iterator().next());
        } else {
            w = sap.ancestor(s, t);
        }

        Iterator<String> l = synsetsIntString.get(w).iterator();
        String k = l.next();
        while (l.hasNext()) {
            k = k + " " + l.next();
        }
        return k;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);
        StdOut.println(wordNet.sap("gill_net","Laos"));
    }
}