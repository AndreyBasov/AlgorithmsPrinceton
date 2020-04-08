import java.util.Arrays;

public class FastCollinearPoints {
    private  Point [] pointsNew;
    private int count;
    private int length;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Illegal Arguements Exception");
        length = points.length;
        for (int i = 0; i < length; i++)
            if (points[i] == null)
                throw new IllegalArgumentException("Illegal Arguements Exception");
        for (int i = 0; i < length; i++)
            for (int j = i + 1; j < length; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Illegal Arguements Exception");
        pointsNew = new Point[length];
        for (int i = 0; i < length; i++)
            pointsNew[i] = points[i];
        count = 0;
    }

    // the number of line segments
    public int numberOfSegments() {
    	return count;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment [] Segments = new LineSegment[length * length];
        for (int i = 0; i < length; i++) {
            Point [] pointOrder = new Point[length - 1];
            for (int j = 0; j < length - 1; j++)
                if (i != j)
                    pointOrder[j] = pointsNew[j];
                else
                    pointOrder[j] = pointsNew[length - 1];
            Arrays.sort(pointOrder, pointsNew[i].slopeOrder());
            int beg = 0, end = 0;
            while (beg != length - 1) {
                while ((end < length - 1) &&
                                pointsNew[i].slopeTo(pointOrder[end]) == pointsNew[i].slopeTo(pointOrder[beg])) {
                    end++;
                }
                Arrays.sort(pointOrder, beg, end);
                if (end != 0)
                    end--;
                if (end - beg >= 2) {
                    if (pointsNew[i].compareTo(pointOrder[beg]) < 0) {
                        Segments[count] = new LineSegment(pointsNew[i], pointOrder[end]);
                    }
                    else if (pointsNew[i].compareTo(pointOrder[end]) > 0) {
                        Segments[count] = new LineSegment(pointOrder[beg], pointsNew[i]);
                    }
                    else {
                        Segments[count] = new LineSegment(pointOrder[beg], pointOrder[end]);
                    }
                    for (int k = count - 1; k >= 0; k--)
                    {
                       if (Segments[count].toString().equals(Segments[k].toString()))
                        {
                           Segments[count] = null;
                            count--;
                            break;
                        }
                    }
                    count++;
                }
                end++;
                beg = end;
            }
        }
        LineSegment [] Segments2 = new LineSegment[count];
        for (int i = 0; i < count; i++)
            Segments2[i] = Segments[i];
        return Segments2;
    }
}
