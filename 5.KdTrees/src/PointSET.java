import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Iterator;

public class PointSET {
    private SET <Point2D> set;
    public PointSET()  {
        set = new SET();
    }                             // construct an empty set of points
    public boolean isEmpty() {
        return set.isEmpty();
    }                     // is the set empty?
    public int size() {
        return set.size();
    }                     // number of points in the set
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Null");
        }
        set.add(p);
    }           // add the point to the set (if it is not already in the set)
    public boolean contains(Point2D p) {
        return set.contains(p);
    }           // does the set contain point p?
    public void draw() {
        for (Point2D s: set) {
            s.draw();
        }
    }                        // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Null");
        }
        ArrayList<Point2D> iterator = new ArrayList<Point2D>();
        for (Point2D s: set) {
            if (rect.contains(s)) {
                iterator.add(s);
            }
        }
        return iterator;
    }           // all points that are inside the rectangle (or on the boundary)
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Null");
        }
        Point2D ans;
        double min = p.distanceSquaredTo(ans = set.min());
        double min2;
        for (Point2D s: set) {
            if ((min2 = s.distanceSquaredTo(p)) < min) {
                min = min2;
                ans = s;
            }
        }
        return ans;
    }             // a nearest neighbor in the set to point p; null if the set is empty
    public static void main(String[] args) {            // unit testing of the methods (optional)
    }
}
