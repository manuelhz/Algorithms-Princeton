import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class LLRBT<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;

    private class Node {
        private Key key;
        private Value val;
        private Node left;
        private Node right;
        boolean color; // color of parent link;
        private int count;
        public Node(Key key, Value val, boolean color) {
            this.key = key;
            this.val = val;
            this.color = color;
        }
    }
    private boolean isRed(Node x) {
        if (x == null) return false; // null links are BLACK;
        return x.color == RED;
    }
    private Node rotateLeft(Node h) {
        assert isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }
    private Node rotateRight(Node h) {
        assert isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }
    private void flipColors(Node h) {
        assert !isRed(h);
        assert isRed(h.left);
        assert isRed(h.right);
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }
    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, RED);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else if (cmp == 0)
            x.val = val;
        x.count = 1 + size(x.left) + size(x.right);

        if (isRed(x.right) && !isRed(x.left)) x = rotateLeft(x);
        if (isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
        if (isRed(x.left) && isRed(x.right)) flipColors(x);

        return x;
    }
    
    public Value get(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else if (cmp == 0) return x.val;
        }
        return null;
    }
    public int size(){
        return size(root);
    }
    private int size(Node x) {
        if (x == null) return 0;
        return x.count;
    }
    public Iterable<Key> keys() {
        Queue<Key> q = new Queue<>();
        inorder(root, q);
        return q;
    }
    private void inorder(Node x, Queue<Key> q) {
        if (x == null) return;
        inorder(x.left, q);
        q.enqueue(x.key);
        inorder(x.right, q);
    }
    public static void main(String args[]) {
        LLRBT<String, Integer> st = new LLRBT<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}
