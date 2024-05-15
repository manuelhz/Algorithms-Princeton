import java.util.LinkedList;
import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.In;
public class PointSET {
    private TreeSet<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<>();
    }
    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }
    // number of points in the set
    public int size() {
        return points.size();
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if(p == null)
            throw new IllegalArgumentException("trying to insert a null value");
        if(!points.contains(p))
            points.add(p);
    }
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if(p == null)
            throw new IllegalArgumentException("null value");
        return points.contains(p);
    }
    // draw all points to standard draw
    public void draw() {
        for(Point2D p: points) {
            p.draw();
        }
    }
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null)
            throw new IllegalArgumentException("rectangle is null");
        LinkedList<Point2D> ps = new LinkedList<>();
        for (Point2D p: points) {
            if (rect.contains(p))
                ps.add(p);
        }
        return ps;
    }
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if(p == null)
            throw new IllegalArgumentException("trying to insert a null value");
        if(points.isEmpty())
            return null;
        Point2D nearestPoint = new Point2D(0, 0);
        double nearestDistance = Double.MAX_VALUE;
        for (Point2D point: points) {
            if(point.distanceTo(p) < nearestDistance) {
                nearestPoint = point;
                nearestDistance = point.distanceTo(p);
            }
        }
        return nearestPoint;
    }
    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        //KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);

            //kdtree.insert(p);
            brute.insert(p);
        }
        StdOut.println("size: " + brute.size());
        Point2D w1 = new Point2D(1, 1);
        Point2D w2 = new Point2D(0.375, 0.3125);
        RectHV r1 = new RectHV(0.125, 0.0, 0.375, 0.125);
        StdOut.println("contains " + w1 + brute.contains(w1));
        StdOut.println("contains " + w2 + brute.contains(w2));
        StdOut.println("range: " + r1 + " returns: ");
        for(Point2D e :brute.range(r1))
            StdOut.println(e);


    }
}
