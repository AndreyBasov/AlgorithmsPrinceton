import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;
    private Node root, ans;
    private int size;
    private double dist;
    private ArrayList<Point2D> Range;

    // a class for node
    private class Node {
        Point2D p;
        Node left, right;
        boolean level;
        public Node (Point2D P, Node Left, Node Right, boolean Level) {
            p = P;
            left = Left;
            right = Right;
            level = Level;
        }
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }      
     
    // is the set empty?                
    public boolean isEmpty() {
        return root == null;
    }                 

    // number of points in the set
    public int size() {
        return size;
    }                         
    
    // recursively add new node to a KD-tree
    private Node put(Node rootSub, Point2D p, boolean lvl) {
        if (rootSub == null) {
            rootSub = new Node(p, null, null, lvl);
        }
        else if (rootSub.level == VERTICAL) {
            if (p.x() < rootSub.p.x()) {
                rootSub.left = put(rootSub.left, p, HORIZONTAL);
            }
            else {
                rootSub.right = put(rootSub.right, p, HORIZONTAL);
            }
        }
        else {
            if (p.y() < rootSub.p.y()) {
                rootSub.left = put(rootSub.left, p, VERTICAL);
            }
            else {
                rootSub.right = put(rootSub.right, p, VERTICAL);
            }
        }
        return rootSub;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p)  {   
        if (p == null) {
            throw new IllegalArgumentException("Null");
        }
        size++;
        if (root == null) {
            root = new Node(p, null, null, VERTICAL);
            return;
        }
        put(root, p, VERTICAL);  // using the recursive method
    }  

    // does the set contain point p?
    public boolean contains(Point2D p) {
        Node cur = root;
        while (cur != null) {
            if (cur.p.equals(p)) {
                return true;
            }
            if (cur.level == VERTICAL) {
                if (p.x() < cur.p.x()) {
                    cur = cur.left;
                }
                else {
                    cur = cur.right;
                }
            }
            else {
                if (p.y() < cur.p.y()) {
                    cur = cur.left;
                }
                else {
                    cur = cur.right;
                }
            }
        }
        return false;
    }          

    // recursively draw the nodes of a given subtree
    private void drawNode(Node node, double xmin, double ymin, double xmax, double ymax) {
        if (node == null) {
            return;
        }
        StdDraw.setPenRadius(0.025);
        StdDraw.setPenColor(0, 0, 0);
        StdDraw.point(node.p.x(), node.p.y());
        StdDraw.setPenRadius(0.005);
        if (node.level == VERTICAL) {
            StdDraw.setPenColor(255, 0, 0);
            StdDraw.line(node.p.x(), ymin, node.p.x(), ymax);
            drawNode(node.left, xmin, ymin, node.p.x(), ymax);
            drawNode(node.right, node.p.x(), ymin, xmax, ymax);
        }
        else {
            StdDraw.setPenColor(0, 0, 255);
            StdDraw.line(xmin, node.p.y(), xmax, node.p.y());
            drawNode(node.left, xmin, ymin, xmax, node.p.y());
            drawNode(node.right, xmin, node.p.y(), xmax, ymax);
        }
    }

    // draw all points to standard draw
    public void draw() {
        drawNode(root, 0, 0, 512, 512);
    }             

    // recursively put all nodex to Range
    private Iterable<Point2D> rangeRec(RectHV rect, Node cur) {
        if (cur == null) {
            return null;
        }
        double xmin = rect.xmin();
        double xmax = rect.xmax();
        double ymin = rect.ymin();
        double ymax = rect.ymax();
        if (rect.contains(cur.p)) {
            Range.add(cur.p);
        }
        if (cur.level == VERTICAL) {
            if (xmin <= cur.p.x()) {
                rangeRec(rect, cur.left);
            }
            if (xmax >= cur.p.x()) {
                rangeRec(rect, cur.right);
            }
        }
        else {
            if (ymin <= cur.p.y()) {
                rangeRec(rect, cur.left);
            }
            if (ymax >= cur.p.y()) {
                rangeRec(rect, cur.right);
            }
        }
        return Range;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        Range = new ArrayList <Point2D>();
        return rangeRec(rect, root);
    }            

    private void nearestNode(Point2D p, Node node, double xmin, double ymin, double xmax, double ymax) {
        if (node == null) {
            return;
        }
        if (dist == -1 || node.p.distanceSquaredTo(p) < dist) {
            dist = node.p.distanceSquaredTo(p);
            ans = node;
        }
        RectHV rect;
        if (node.level == VERTICAL) {
            if (p.x() < node.p.x()) {
                rect = new RectHV(xmin, ymin, node.p.x(), ymax);
                if (rect.distanceSquaredTo(p) <= dist) {
                    nearestNode(p, node.left, xmin, ymin, node.p.x(), ymax);
                }
                rect = new RectHV(node.p.x(), ymin, xmax, ymax);
                if (rect.distanceSquaredTo(p) <= dist) {
                    nearestNode(p, node.right, node.p.x(), ymin, xmax, ymax);
                }
            }
            else {
                rect = new RectHV(node.p.x(), ymin, xmax, ymax);
                if (rect.distanceSquaredTo(p) <= dist) {
                    nearestNode(p, node.right, node.p.x(), ymin, xmax, ymax);
                }
                rect = new RectHV(xmin, ymin, node.p.x(), ymax);
                if (rect.distanceSquaredTo(p) <= dist) {
                    nearestNode(p, node.left, xmin, ymin, node.p.x(), ymax);
                }
            }
        }
        else {
            if (p.y() < node.p.y()) {
                rect = new RectHV(xmin, ymin, xmax, node.p.y());
                if (rect.distanceSquaredTo(p) <= dist) {
                    nearestNode(p, node.left, xmin, ymin, xmax, node.p.y());
                }
                rect = new RectHV(xmin, node.p.y(), xmax, ymax);
                if (rect.distanceSquaredTo(p) <= dist) {
                    nearestNode(p, node.right, xmin, node.p.y(), xmax, ymax);
                }
            }
            else {
                rect = new RectHV(xmin, node.p.y(), xmax, ymax);
                if (rect.distanceSquaredTo(p) <= dist) {
                    nearestNode(p, node.right, xmin, node.p.y(), xmax, ymax);
                }
                rect = new RectHV(xmin, ymin, xmax, node.p.y());
                if (rect.distanceSquaredTo(p) <= dist) {
                    nearestNode(p, node.left, xmin, ymin, xmax, node.p.y());
                }
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (root == null) {
            return null;
        }
        dist = -1;
        nearestNode(p, root, 0, 0, 512, 512);
        return ans.p;
    }           
}
