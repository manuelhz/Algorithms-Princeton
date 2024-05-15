import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Arrays;

public final class FastCollinearPoints {
    private java.util.Comparator<Point> SLOPE_ORDER;

    private int numberOfSegments = 0;
    private final int NUMBER_OF_SEGMENTS;
    private final LineSegment[] SEGMENTS;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(final Point[] arr) {
        if(arr == null) throw new IllegalArgumentException();
        int N = arr.length;
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            if (arr[i] == null)
                throw new IllegalArgumentException();
            points[i] = arr[i];
        }
        // Detect if there are any duplicated points.
        Arrays.sort(points);
        for (int i = 0; i < N - 1; i++){
            if (points[i].compareTo(points[i+1]) == 0) throw new IllegalArgumentException();
        }
        // in 'seg' I will store the segments founded.
        ArrayList<LineSegment> seg = new ArrayList<>();
        // variable 'i' corresponds to the pivot, each point is the pivot once.
        for (int i = 0; i < N; i++) {
            Point[] pointsToOrder;
            pointsToOrder = new Point[N-1];

            for (int j = 0, w = 0; j < N; j++)
                if(j != i) pointsToOrder[w++] = points[j];

            SLOPE_ORDER = points[i].slopeOrder();
            Arrays.sort(pointsToOrder, SLOPE_ORDER);
            for (int s = 1; s < pointsToOrder.length; s++) {
                //StdOut.println("s:" + s + " i:" + i);
                if (points[i].slopeTo(pointsToOrder[s]) == points[i].slopeTo(pointsToOrder[s-1])) {
                    ArrayList<Point> collinear = new ArrayList<>();
                    collinear.add(pointsToOrder[s-1]);
                    collinear.add(pointsToOrder[s]);
                    while(
                            s < pointsToOrder.length-1
                            &&
                            points[i].slopeTo(pointsToOrder[s]) == points[i].slopeTo(pointsToOrder[s+1])
                    ) {
                        //if (points[i].compareTo(pointsToOrder[++s]) == -1)
                            //collinear.add(pointsToOrder[s]);
                        collinear.add(pointsToOrder[++s]);
                    }
                    if (collinear.size() > 2 && points [i].compareTo (collinear.get(0)) == -1) {
                        collinear.add(points[i]);
                        LineSegment l = lineSegment(collinear.toArray(new Point[collinear.size()]));
                        //Point a = collinear.toArray(new Point[collinear.size()])[0];
                        //Point b = collinear.toArray(new Point[collinear.size()])[0];
                        //LineSegment l = collinear.toArray(new Point[collinear.size()]);
                        seg.add(l);
                        numberOfSegments++;
                        //i = i + collinear.size() - 1;
                    }
                }
            }
        }
        SEGMENTS = seg.toArray(new LineSegment[numberOfSegments]);
        NUMBER_OF_SEGMENTS = numberOfSegments;
    }
    // given a set of collinear points it returns the segment conformed by the first and the last points.
    private LineSegment lineSegment(Point[] points) {
        Point firstPoint = points[0];
        Point lastPoint = points[points.length-1];
        for (int i = 0; i < points.length; i++){
            if (firstPoint.compareTo(points[i]) > 0) firstPoint = points[i];
            if (lastPoint.compareTo(points[i]) < 0) lastPoint = points[i];
        }
        return new LineSegment(firstPoint, lastPoint);
    }

    // the number of line segments
    public int numberOfSegments() {
        return NUMBER_OF_SEGMENTS;
    }
    // the line segments
    public LineSegment[] segments() {
        return SEGMENTS;
    }
    public static void main(String args[]) {
        // read the n points from a file
        int N = StdIn.readInt();
        Point points[] = new Point[N];
        int i = 0;
        while (!StdIn.isEmpty()) {
            points[i] = new Point(StdIn.readInt(), StdIn.readInt()) ;
            i++;
        }
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.03);
        StdDraw.setPenColor(StdDraw.BLUE);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}