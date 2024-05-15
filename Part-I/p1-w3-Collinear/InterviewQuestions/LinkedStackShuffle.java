import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class LinkedStackShuffle<Item> {
    private Node first = null;
    private int size = 0;

    public int size() {
        return size;
    }

    private class Node {
        Item item;
        Node next;
    }
    public boolean isEmpty() {
        return first == null;
    }
    public void push(Item item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        size++;
    }
    public Item pop() {
        Item item = first.item;
        first = first.next;
        size--;
        return item;
    }
    private static void merge(LinkedStackShuffle v, LinkedStackShuffle w, LinkedStackShuffle a) {
        while (v.size() > 0 || w.size() > 0) {
            Double random = StdRandom.uniform();
            if(random > 0.5)
                if (w.size() > 0)
                    a.push(w.pop());
                else a.push(v.pop());
            else if (v.size() > 0)
                a.push(v.pop());
        }
    }
    public static void shuffle(LinkedStackShuffle v, LinkedStackShuffle w, LinkedStackShuffle a) {
        if(a.size() < 1) return;
        Double random = StdRandom.uniform();
        if(random > 0.5)
            v.push(a.pop());
        else
            w.push(a.pop());
        shuffle(v, w, a);
        merge(v, w, a);
    }
    public static void shuffle(LinkedStackShuffle a) {
        LinkedStackShuffle v = new LinkedStackShuffle();
        LinkedStackShuffle w = new LinkedStackShuffle();
        shuffle(v, w, a);
    }
    public static void main (String[] args) {
        LinkedStackShuffle<String> l = new LinkedStackShuffle<>();
        while (!StdIn.isEmpty())
            l.push(StdIn.readString());
        shuffle(l);
        while (l.size() > 0)
            StdOut.println(l.pop()+"  ");
    }
}
