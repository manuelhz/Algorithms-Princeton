import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.In;

/*
* Non-degenerate points: no two points (or rectangles) share either an
                           x-coordinate or a y-coordinate

* Distinct points:       no two points (or rectangles) share both an
                           x-coordinate and a y-coordinate

* General points:        no restrictions on the x-coordinates or y-coordinates
                           of the points (or rectangles)
 */

public class KdTree {
    // fields
    private Node root;
    private static final boolean VERTICAL_LINE = true;
    private static final boolean HORIZONTAL_LINE = false;
    private int count = 0;


    // construct an empty set of points
    public KdTree(){}
    // is the set empty?
    public boolean isEmpty() {
        return root == null; // if root is null the tree is empty
    }
    // number of points in the set
    public int size() {
        return count;
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if(p == null)
            throw new IllegalArgumentException("trying to insert a null value");
        RectHV r = new RectHV(0, 0, 1, 1);
        root = insert(root, p, r);

    }
    private Node insert(Node x, Point2D p, RectHV r) {
        if (x == null) {
            count++;
            return new Node(p, VERTICAL_LINE, r);
        }
        if(x.line == VERTICAL_LINE) {
            if (p.x() < x.key.x()) {
                RectHV w = new RectHV(x.recthv.xmin(), x.recthv.ymin(), x.key.x(), x.recthv.ymax());
                x.left = insert(x.left, p, w);
            }
            else {
                if (p.x() != x.key.x() || p.y() != x.key.y()) {
                    RectHV w = new RectHV(x.key.x(), x.recthv.ymin(), x.recthv.xmax(), x.recthv.ymax());
                    x.right = insert(x.right, p, w);
                }
            }
        }
        else if(x.line == HORIZONTAL_LINE) {
            if (p.y() < x.key.y()) {
                RectHV w = new RectHV(x.recthv.xmin(), x.recthv.ymin(), x.recthv.xmax(), x.key.y());
                x.left = insert(x.left, p, w);
            }
            else {
                if(p.x() != x.key.x() || p.y() != x.key.y()) {
                    RectHV w = new RectHV(x.recthv.xmin(), x.key.y(), x.recthv.xmax(), x.recthv.ymax());
                    x.right = insert(x.right, p, w);
                }
            }
        }
        if(isVertical(x) && isVertical(x.left))
            x.left.line = HORIZONTAL_LINE;
        if(isVertical(x) && isVertical(x.right))
            x.right.line = HORIZONTAL_LINE;
        return x;
    }
    private boolean isVertical(Node x) {
        if(x == null)
            return false;
        if (x.line == VERTICAL_LINE)
            return true;
        return false;
    }
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if(p == null)
            throw new IllegalArgumentException("trying to insert a null value");
        if(isEmpty())
            return false;
        Node x = root;
        return contains(root, p);
    }
    private boolean contains(Node x, Point2D p) {
        if(x != null) {
            if (x.line == VERTICAL_LINE) {
                if(p.x() < x.key.x())
                    return contains(x.left, p);
                else {
                    if (p.x() != x.key.x() || p.y() != x.key.y())
                        return contains(x.right, p);
                    else return true;
                }
            }
            else if(x.line == HORIZONTAL_LINE) {
                if (p.y() < x.key.y())
                    return contains(x.left, p);
                else {
                    if(p.y() != x.key.y() || p.x() != x.key.x())
                        return contains(x.right, p);
                    else return true;
                }
            }
        }
        return false;
    }
    // draw all points to standard draw
    public void draw() {
        Node x = root;
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        draw(x);
        StdDraw.show();
    }

    private void draw(Node x) {
        // draw the nodes
        StdDraw.setPenRadius(0.03);
        StdDraw.setPenColor(StdDraw.BLACK);
        x.key.draw();
        StdDraw.setPenRadius(0.01);
        if(isVertical(x)) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.key.x(),x.recthv.ymin(), x.key.x(), x.recthv.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.recthv.xmin(), x.key.y(), x.recthv.xmax(), x.key.y());
        }
        if(x.left != null && isVertical(x))
            draw(x.left);
        if(x.right != null && isVertical(x))
            draw(x.right);
        if(x.left != null && !isVertical(x))
            draw(x.left);
        if(x.right != null && !isVertical(x))
            draw(x.right);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null)
            throw new IllegalArgumentException("rectangle is null");
        LinkedList<Point2D> sop = new LinkedList<>();
        return range(root, sop, rect);
    }
    private LinkedList<Point2D> range(Node x, LinkedList<Point2D> sop, RectHV rect) {
        if(rect.contains(x.key))
            sop.add(x.key);
        if(x.left != null && x.left.recthv.intersects(rect))
            sop = range(x.left, sop, rect);
        if(x.right != null && x.right.recthv.intersects(rect))
            sop = range(x.right, sop, rect);
        return sop;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if(p == null)
            throw new IllegalArgumentException("trying to insert a null value");
        return nearest(root, p, root.key);
    }
    private Point2D nearest(Node x, Point2D p, Point2D nearest) {
        if(x.key.distanceSquaredTo(p) < nearest.distanceSquaredTo(p))
            nearest = x.key;
        if(x.left != null && p.distanceSquaredTo(nearest) >= x.left.recthv.distanceSquaredTo(p))
            nearest = nearest(x.left, p, nearest);
        if(x.right != null && p.distanceSquaredTo(nearest) >= x.right.recthv.distanceSquaredTo(p))
            nearest = nearest(x.right, p, nearest);

        return nearest;
    }

    private class Node {
        private Point2D key;
        private Node left;
        private Node right;
        boolean line;
        RectHV recthv;
        public Node(Point2D key, boolean line, RectHV recthv) {
            this.key = key;
            this.line = line;
            this.recthv = recthv;
        }
    }
    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            StdOut.print(p + " contains?: " + kdtree.contains(p));
            kdtree.insert(p);
            StdOut.println(" size: " + kdtree.size() + " contains?: " + kdtree.contains(p));
            //StdOut.println(p + " size: " + kdtree.size() );
        }

        kdtree.draw();
        StdOut.println("Contains Point2D(0.9, 0.6)?: " + kdtree.contains(new Point2D(0.9, 0.6)));
        RectHV r = new RectHV(0, 0, 1, 0.5);
        StdOut.println("Points contained in the rectangle " + r);
        for(Point2D p: kdtree.range(r))
            StdOut.println(p);
        RectHV r2 = new RectHV(0, 0.5, 1, 1);
        StdOut.println("Points contained in the rectangle " + r2);
        for(Point2D p: kdtree.range(r2))
            StdOut.println(p);
        Point2D p = new Point2D(0.375, 0.3125);
        StdOut.println("nearest point to the point " + p + " is : " + kdtree.nearest(p));
    }
}
