import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private int numberOfSegments = 0;
    private final int NUMBER_OF_SEGMENTS;
    private LineSegment[] segments;
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] arr) {
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
            
        
        for (int i = 0; i < N; i++) {
            if(points[i] == null) throw new IllegalArgumentException();
        }
        ArrayList<LineSegment> seg = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++){
                for (int k = j + 1; k < N; k++) {
                    for (int l = k + 1; l < N; l++) {
                        if (
                                points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                                &&
                                        points[i].slopeTo(points[k]) == points[i].slopeTo(points[l])
                        ) {
                            Point[] pointsInSegment = new Point[4];
                            pointsInSegment[0] = points[i];
                            pointsInSegment[1] = points[j];
                            pointsInSegment[2] = points[k];
                            pointsInSegment[3] = points[l];
                            seg.add(lineSegment(pointsInSegment));
                            numberOfSegments++;
                        }
                    }
                }
            }
        }
        //segments = seg.toArray(new LineSegment[numberOfSegments]);
        segments = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++)
            segments[i] = seg.get(i);
        NUMBER_OF_SEGMENTS = numberOfSegments;
    }
    private LineSegment lineSegment(Point[] points) {
        Point firstPoint = points[0];
        Point lastPoint = points[0];
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
        return segments;
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
        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}