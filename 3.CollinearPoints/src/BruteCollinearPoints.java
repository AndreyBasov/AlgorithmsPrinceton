import java.util.Arrays;

public class BruteCollinearPoints {
        private Point [] pointsNew;
        private int count;
        private int length;
        
        public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
        {
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
            Arrays.sort(pointsNew);
            count = 0;
        }

        // the number of line segments
        public int numberOfSegments() {
            return count;
        }

        // the line segments
        public LineSegment[] segments() {
            LineSegment Segments [] = new LineSegment[length*length];
            for (int i = 0; i < length; i++)
                for (int j = i + 1; j < length; j++)
                    for (int k = j + 1; k < length; k++)
                        for (int s = k + 1; s < length; s++) {
                            if (pointsNew[i].slopeTo(pointsNew[j]) == pointsNew[j].slopeTo(pointsNew[k])
                                    && pointsNew[j].slopeTo(pointsNew[k]) == pointsNew[k].slopeTo(pointsNew[s]))
                            {
                                Segments[count] = new LineSegment(pointsNew[i], pointsNew[s]);
                                count++;
                            }
                        }
            LineSegment Segments2 [] = new LineSegment[count];
                        for (int i = 0; i < count; i++)
                            Segments2[i] = Segments[i];
            return Segments2;
        }
}
