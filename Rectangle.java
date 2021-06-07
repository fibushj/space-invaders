import java.util.ArrayList;
import java.util.List;

/**
 * The Class Rectangle.
 *
 * @author Jonathan Fibush
 */
public class Rectangle {

    /** The upper left. */
    private Point upperLeft;

    /** The width. */
    private double width;

    /** The height. */
    private double height;

    /**
     * Instantiates a new rectangle.
     *
     * @param upperLeft
     *            the upper left
     * @param width
     *            the width
     * @param height
     *            the height
     */
    // Create a new rectangle with location and width/height.
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * . Returns a (possibly empty) List of intersection points
     *
     * @param line
     *            the line
     * @return the list of intersection points
     */
    // with the specified line.
    public List<Point> intersectionPoints(Line line) {
        Point upperRight, downLeft, downRight, intersection;
        int counter = 0;
        Line[] lines = new Line[4];
        upperRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        downLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        downRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height);
        // creating the 4 lines of the rectangle
        lines[0] = new Line(this.upperLeft, upperRight);
        lines[1] = new Line(this.upperLeft, downLeft);
        lines[2] = new Line(downRight, downLeft);
        lines[3] = new Line(downRight, upperRight);
        List<Point> intersections = new ArrayList<Point>();
        // checking all the possible intersections - the maximum of which is 2
        for (int i = 0; i < 4 && counter <= 2; i++) {
            intersection = line.intersectionWith(lines[i]);
            if (intersection != null) {
                if (lines[i].isLineVertical()) {
                    intersection.setX(lines[i].start().getX());
                }
                if (lines[i].isLineHorizontal()) {
                    intersection.setY(lines[i].start().getY());
                }
                intersections.add(intersection);
                counter++;
            }
        }
        return intersections;
    }

    /**
     * Gets the width.
     *
     * @return the width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Gets the height.
     *
     * @return the height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * . Gets the upper left point of the rectangle
     *
     * @return the upper left
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Gets the down right point of the rectangle.
     *
     * @return the down right
     */
    public Point getDownRight() {
        return new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height);
    }

    /**
     * Sets the upper left.
     *
     * @param p
     *            the new upper left
     */
    public void setUpperLeft(Point p) {
        this.upperLeft = p;
    }

}
