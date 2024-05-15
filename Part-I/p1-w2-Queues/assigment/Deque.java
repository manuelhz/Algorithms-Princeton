import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size;
    private class Node {
        Item item;
        Node next;
        Node previous;
    }
    // construct an empty deque
    public Deque() {
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if(item == null) throw new IllegalArgumentException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (isEmpty()) last = first;
        else oldFirst.previous = first;
        size++;
    }

    // add the item to the back
    // default queue
    public void addLast(Item item) {
        if(item == null) throw new IllegalArgumentException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldLast;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        size++;
    }

    // remove and return the item from the front
    // default queue
    public Item removeFirst() {
        if(isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        size--;
        if(!isEmpty())
            first.previous = null;

        if (isEmpty()) last = null;
        return  item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if(isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.previous;
        size--;
        if(!isEmpty())
            last.next = null;

        if (isEmpty()) first = null;
        return  item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new CustomIterator();
    }
    private class CustomIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-")) StdOut.print(deque.removeLast() + " ");
            else
                deque.addFirst(s);
        }
        deque.addLast("hello");
        deque.addLast("world");
        deque.addLast("guy!");
        StdOut.print(deque.removeLast());
        StdOut.println();

        Iterator<String> iter;
        iter = deque.iterator();
        while (iter.hasNext()) {
            String mextElement = iter.next();
            StdOut.print(mextElement + " ");
        }
        StdOut.println();
    }
}