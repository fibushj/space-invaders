import java.util.List;

/**
 * A line.
 *
 * @author Jonathan Fibush
 */

public class Line {

    /** The start. */
    private Point start;

    /** The end. */
    private Point end;

    /** The slope. */
    private double slope;

    /**
     * . Constructor 1 - receives two points
     *
     * @param start
     *            point
     * @param end
     *            point
     */
    public Line(Point start, Point end) {
        // the point between the 2 that is more left will be the start, and the other -
        // the end
        this.start = start;
        this.end = end;

        // the slope is calculated only if the line isn't vertical
        if (!this.isLineVertical()) {
            slope = (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
        }
    }

    /**
     * . Constructor 2 - receives the values of each point
     *
     * @param x1
     *            value
     * @param y1
     *            value
     * @param x2
     *            value
     * @param y2
     *            value
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * . Returns the length of the line
     *
     * @return length
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * . Returns the middle point of the line
     *
     * @return middle point
     */
    public Point middle() {
        return new Point((this.start.getX() + this.end.getX()) / 2, (this.start.getY() + this.end.getY()) / 2);
    }

    /**
     * . Returns the start point of the line
     *
     * @return start
     */
    public Point start() {
        return this.start;
    }

    /**
     * . Returns the end point of the line
     *
     * @return end
     */
    public Point end() {
        return this.end;
    }

    /**
     * . Returns the slope of the line
     *
     * @return slope
     */
    public double slope() {
        return this.slope;
    }

    /**
     * . Returns true if the lines intersect, false otherwise
     *
     * @param other
     *            line
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        return intersectionWith(other) != null;
    }

    /**
     * . Returns the intersection point if the lines intersect, and null otherwise.
     * I decided that in case at least one of the lines is a point, the 2 lines
     * don't intersect. Additionally, if the lines are collinear or meet at the
     * edges, they don't intersect
     *
     * @param other
     *            line
     * @return intersection
     */
    public Point intersectionWith(Line other) {
        boolean firstIsVertical, secondIsVertical;
        // in case of the lines is a point
        if (this.isLineAPoint() || other.isLineAPoint()) {
            return null;
        }
        firstIsVertical = this.isLineVertical();
        secondIsVertical = other.isLineVertical();
        // in case at least one of the lines is vertical
        if (firstIsVertical || secondIsVertical) {
            // in case both lines are vertical
            if (firstIsVertical && secondIsVertical) {
                return null;
            }
            // in case only one of the lines is vertical
            if (firstIsVertical) {
                return verticalIntersection(this, other);
            }
            if (secondIsVertical) {
                return verticalIntersection(other, this);
            }
        }
        // in case the lines are parallel or collinear
        if (this.slope == other.slope()) {
            return null;
        }
        return intersectionTwoLines(other);
    }

    /**
     * . Returns true is the lines are equal, false otherwise
     *
     * @param other
     *            line
     * @return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        // all 4 coordinates of the lines should be equal between the 2 lines in order
        // for the lines to be equal
        if (this.start.getX() != other.start.getX() || this.start.getY() != other.start.getY()
                || this.end.getX() != other.end.getX() || this.end.getY() != other.end.getY()) {
            return false;
        }
        boolean firstIsVertical = this.isLineVertical();
        boolean secondIsVertical = other.isLineVertical();
        // in case only one of the lines is vertical
        if (firstIsVertical ^ secondIsVertical) {
            return false;
        }
        // in case both lines are vertical (and have the same coordinates)
        if (firstIsVertical && secondIsVertical) {
            return true;
        }
        // now the equality or inequality of the slope decides whether or not the lines
        // are equal
        return this.slope == other.slope();
    }

    /**
     * . Returns true if the line is a point, false otherwise
     *
     * @return true if the line is a point, false otherwise
     */
    public boolean isLineAPoint() {
        return this.start.getX() == this.end.getX() && this.start.getY() == this.end.getY();
    }

    /**
     * . Returns true if the line is vertical, false otherwise
     *
     * @return true if the line is vertical, false otherwise
     */
    public boolean isLineVertical() {
        return this.start.getX() == this.end.getX();
    }

    /**
     * Checks if is line horizontal.
     *
     * @return true, if is line horizontal
     */
    public boolean isLineHorizontal() {
        return this.start.getY() == this.end.getY();
    }

    /**
     * . Returns the intersection point of a vertical line and a non-vertical line.
     * If there's no intersection, returns null
     *
     * @param vertical
     *            line
     * @param other
     *            line
     * @return intersection or null
     */
    public Point verticalIntersection(Line vertical, Line other) {
        double y = other.slope() * (vertical.start.getX() - other.start.getX()) + other.start.getY();
        // checking if the intersection of the lines is in the range of the segments
        if (y > Math.max(vertical.start.getY(), vertical.end.getY())
                || y < Math.min(vertical.start.getY(), vertical.end.getY())
                || vertical.start().getX() < Math.min(other.start().getX(), other.end().getX())
                || vertical.start().getX() > Math.max(other.start().getX(), other.end().getX())) {
            return null;
        }
        return new Point(vertical.start.getX(), y);

    }

    /**
     * . Returns the intersection of two non-vertical lines. If there's no
     * intersection, returns null
     *
     * @param other
     *            line
     * @return intersection or null
     */
    public Point intersectionTwoLines(Line other) {
        double startX1 = this.start.getX();
        double startY1 = this.start.getY();
        double endX1 = this.end.getX();
        double endY1 = this.end.getY();
        double startX2 = other.start.getX();
        double startY2 = other.start.getY();
        double endX2 = other.end.getX();
        double endY2 = other.end.getY();
        double slope1 = this.slope;
        double slope2 = other.slope();
        // calculating the coordinates of the intersection
        double intersectionX = (startY1 - startY2 + slope2 * startX2 - slope1 * startX1) / (slope2 - slope1);
        double intersectionY = slope1 * (intersectionX - startX1) + startY1;
        // checking if the intersection is the in the range of the segments
        if (intersectionX >= Math.min(startX1, endX1) && intersectionX <= Math.max(startX1, endX1)
                && intersectionX >= Math.min(startX2, endX2) && intersectionX <= Math.max(startX2, endX2)) {
            if (intersectionY >= Math.min(startY1, endY1) && intersectionY <= Math.max(startY1, endY1)
                    && ((intersectionY >= Math.min(startY2, endY2) && intersectionY <= Math.max(startY2, endY2))
                            || Math.abs(intersectionY - startY2) < 0.0001)) {
                return new Point(intersectionX, intersectionY);
            }
        }
        return null;
    }

    /**
     * Closest intersection to start of line.
     *
     * @param rect the rectangle
     * @return closeset intersection point
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersections = rect.intersectionPoints(this);
        if (intersections.size() == 0) {
            return null;
        }
        if (intersections.size() == 1) {
            return intersections.get(0);
        }
        //if there are 2 intersection, return the closest one
        if (intersections.get(0).distance(this.start) <= intersections.get(1).distance(this.start)) {
            return intersections.get(0);
        }
        return intersections.get(1);
    }

}
