package geometry;
// ID: 316012624
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;

/**
 * This class represents a Rectangle in a 2D plane.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class Rectangle {
    private double width, height;
    private Point upperLeft;

    /**
     * Constructs a new rectangle with a location, width and height.
     * @param upperLeft the location of the upper-left corner.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Constructs a new rectangle with an (x, y), width and height.
     * @param upperLeftX x coordinate of the upper-left corner.
     * @param upperLeftY y coordinate of the upper-left corner.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     */
    public Rectangle(int upperLeftX, int upperLeftY, double width, double height) {
        this.upperLeft = new Point(upperLeftX, upperLeftY);
        this.width = width;
        this.height = height;
    }

    /**
     * Return a (possibly empty) list of intersection points with a specified line.
     * @param line a line to checks intersections with.
     * @return a list of intersection points with a the line (might be empty).
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> points = new ArrayList<>();
        List<Line> lines = getRetangleLines();

        for (Line l : lines) {
            Point p = l.intersectionWith(line);
            if (p != null) {
                points.add(p);
            }
        }
        return points;
    }

    /**
     * Makes a list of 4 lines representing the rectangles edges.
     * @return a list of the rectangles edges.
     */
    private List<Line> getRetangleLines() {
        List<Line> lines = new ArrayList<>();
        Point p = this.upperLeft;

        // Make a line representing each of the rectangle's sides.
        Line top = new Line(p.getX(), p.getY(), p.getX() + this.width, p.getY());
        Line bottom = new Line(p.getX(), p.getY() + this.height, p.getX() + this.width, p.getY() + this.height);
        Line left = new Line(p.getX(), p.getY(), p.getX(), p.getY() + this.height);
        Line right = new Line(p.getX() + this.width, p.getY(), p.getX() + this.width, p.getY() + this.height);

        // Add the lines to the list.
        lines.add(top);
        lines.add(bottom);
        lines.add(left);
        lines.add(right);

        return lines;
    }

    /**
     * Returns the width of the rectangle.
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the rectangle.
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the upper-left point of the rectangle.
     * @return the upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Sets a new upper left corner.
     * @param x new x value
     * @param y new y value
     */
    public void setUpperLeft(double x, double y) {
        this.upperLeft = new Point(x, y);
    }

    /**
     * Fills the rectangle on a given DrawSurface.
     * @param d the DrawSurface to draw on.
     */
    public void drawOn(DrawSurface d) {
        int upperX = (int) this.upperLeft.getX();
        int upperY =  (int) this.upperLeft.getY();

        d.fillRectangle(upperX, upperY, (int) this.width, (int) this.height);
    }

    /**
     * Draws the rectangle on a given DrawSurface.
     * @param d the DrawSurface to draw on.
     */
    public void drawStroke(DrawSurface d) {
        int upperX = (int) this.upperLeft.getX();
        int upperY =  (int) this.upperLeft.getY();
        d.drawRectangle(upperX, upperY, (int) this.width, (int) this.height);
    }
}
