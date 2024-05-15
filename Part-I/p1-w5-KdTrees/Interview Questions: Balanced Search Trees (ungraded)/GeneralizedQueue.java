import edu.princeton.cs.algs4.RedBlackBST;
public class GeneralizedQueue<Item> {
    private RedBlackBST gq;
    private int index = 0;
    // Create an empty data structure:
    public GeneralizedQueue() {
        gq = new RedBlackBST<Integer, Item>();
    }
    // Append an item to the end of the queue:
    public void append(Item item) {
        gq.put(index++, item);
    }
    // Remove an item from the front of the queue.
    public void dequeue() {
        gq.deleteMin();
    }
    // Return the i^{th} item in the queue.

    public Item get(int i) {
        return (Item) gq.get(i + 1);
    }
    // Remove the i^{th} item in the queue.
    public Item remove(int i) {
        Item w = get(i);
        gq.delete(i);
        resetIndex();
        return w;
    }
    public void resetIndex() {
        // could be done
    }

    public static void main(String main[]) {

    }
}
