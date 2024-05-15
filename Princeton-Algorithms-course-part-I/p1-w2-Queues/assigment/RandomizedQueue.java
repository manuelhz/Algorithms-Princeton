import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item q[];
    private Integer size;
    // index where the next element will be inserted
    private Integer index;
    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[1];
        size = 0;
        index = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if(item == null) throw new IllegalArgumentException();
        if (size == q.length) resize(2 * q.length);
        q[index++] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if(isEmpty()) throw new NoSuchElementException();
        int chosen = randomIndex();
        Item item = q[chosen];
        q[chosen] = null;
        if(size > 0 && size <= q.length / 4) resize(q.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    private int randomIndex() {
        int val;
        do {
            Double i = Math.random() * (index);
            val = i.intValue();
        } while (q[val] == null);
        return val;
    }
    public Item sample() {
        if(isEmpty()) throw new NoSuchElementException();
        return q[randomIndex()];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new CustomIterator();
    }
    private class CustomIterator implements Iterator<Item> {
        int[] iteratorIndexes;
        // index of iteratorIndexes next to return
        int iteratorIndex = 0;
        public CustomIterator() {
            iteratorIndexes = new int[index];
            for (int i = 0; i < iteratorIndexes.length; i++)
                iteratorIndexes[i] = i;
            StdRandom.shuffle(iteratorIndexes);
        }
        public boolean hasNext() {
            if(iteratorIndex >= index) return false;
            while (q[iteratorIndexes[iteratorIndex]] == null) {
                iteratorIndex++;
            }
            return true;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            return q[iteratorIndexes[iteratorIndex++]];

        }
    }



    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int j = 0;
        for (int i = 0; i < q.length; i++) {
            if (q[i] != null) {
                copy[j] = q[i];
                j++;
            }
        }
        q = copy;
    }
    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        while(!StdIn.isEmpty()) {
            String s = StdIn.readString();
            randomizedQueue.enqueue(s);
        }
        Iterator<String> iter = randomizedQueue.iterator();
        while(iter.hasNext()) {
            String nextElement = iter.next();
            StdOut.print(nextElement + " ");
        }
        StdOut.println();
        StdOut.println("----- Samples: -----");
        for (int i = 0; i < randomizedQueue.size; i++)
            StdOut.print(randomizedQueue.sample() + " ");
        StdOut.println();
        StdOut.println("----- 2xdequeue: -----");
        randomizedQueue.dequeue();
        randomizedQueue.dequeue();

        iter = randomizedQueue.iterator();
        while(iter.hasNext()) {
            String nextElement = iter.next();
            StdOut.print(nextElement + " ");
        }




        StdOut.println();
        StdOut.println("----- 2xdequeue: -----");
        randomizedQueue.dequeue();
        randomizedQueue.dequeue();

        iter = randomizedQueue.iterator();
        while(iter.hasNext()) {
            String nextElement = iter.next();
            StdOut.print(nextElement + " ");
        }
    }
}
