package geometry;

// ID: 316012624
import java.util.List;
import biuoop.DrawSurface;

/**
 * This class represents a Line in a 2D plane.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class Line {
    private Point start, end;

    /**
     * Constructs a new line from 2 given Points.
     * @param start starting point of the line.
     * @param end ending point of the line.
     */
    public Line(Point start, Point end) {
        this.start = new Point(start.getX(), start.getY());
        this.end = new Point(end.getX(), end.getY());
    }

    /**
     * Constructs a new line from 4 given coordinates.
     * @param x1 x value of first coordinate.
     * @param y1 y value of first coordinate.
     * @param x2 x value of second coordinate.
     * @param y2 y value of second coordinate.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the start of the line.
     * @param rect the rectangle to check intersections with.
     * @return the closest intersection point to the start of the line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        Point closest;
        double minDistance, d;
        List<Point> points = rect.intersectionPoints(this);

        // if no intersection were found, return null.
        if (points.size() == 0) {
            return null;
        }

        // initialize
        closest = points.get(0);
        minDistance = closest.distance(this.start);

        // find the closest point of intersection to the start of the line.
        for (Point p : points) {
            d = p.distance(this.start);
            if (d < minDistance) {
                closest = p;
                minDistance = d;
            }
        }
        return closest;
    }

    /**
     * Return the length of the line.
     * @return the length of the line.
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Returns the middle point of the line.
     * @return the middle point of the line.
     */
    public Point middle() {
        double p1X = this.start.getX();
        double p1Y = this.start.getY();
        double p2X = this.end.getX();
        double p2Y = this.end.getY();

        double xMid = (p1X + p2X) / 2;
        double yMid = (p1Y + p2Y) / 2;

        return (new Point(xMid, yMid));
    }

    /**
     * Returns the start point of the line.
     * @return end point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the end point of the line.
     * @return end point of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * Calculates the line's slope.
     * @return the line's slope.
     */
    private double slope() {
        double p1X = this.start.getX();
        double p1Y = this.start.getY();
        double p2X = this.end.getX();
        double p2Y = this.end.getY();

        // this is perpendicular line (infinite slope).
        if (p2X == p1X) {
            return Double.POSITIVE_INFINITY;
        }
        // m = (y2-y1) / (x2-x1)
        return (p2Y - p1Y) / (p2X - p1X);
    }

    /**
     * Returns true if the lines intersect, false otherwise.
     * @param other the other line to check intersection with.
     * @return true of false.
     */
    public boolean isIntersecting(Line other) {
        Point p = this.intersectionWith(other);
        // If there is no intersection, the point of intersection returned by intersectionWith will be null.
        return p == null ? false : true;
    }

    /**
     * Checks if a point is on the line segment.
     * @param p the point to check
     * @return true or false
     */
    public boolean isPointOnLine(Point p) {
        // the values of the line segments start& end (with epsilon error margin).
        double maxX = Math.max(this.start.getX(), this.end.getX()) + Epsilon.EPSILON;
        double maxY = Math.max(this.start.getY(), this.end.getY()) + Epsilon.EPSILON;
        double minX = Math.min(this.start.getX(), this.end.getX()) - Epsilon.EPSILON;
        double minY = Math.min(this.start.getY(), this.end.getY()) - Epsilon.EPSILON;

        // Is a point within the line segment.
        if (p.getX() >= minX && p.getX() <= maxX && p.getY() >= minY && p.getY() <= maxY) {
            return true;
        }
        return false;
    }

    /**
     * Calculates the b factor in a lines equation (y = mx + b).
     * @param slope the slope if the line.
     * @return the b factor in a lines equation (intersection with the Y axis).
     */
    private double calculateYIntersection(double slope) {
        // calculate the line equation (y=mx+b) of each line.
        if (slope == Double.POSITIVE_INFINITY || slope == Double.NEGATIVE_INFINITY) {
            return Double.NaN;
        //  if the slope is smaller than epsilon we'll consider it to be 0.
        } else if (Math.abs(slope) < Epsilon.EPSILON) {
            return this.start.getY();
        } else {
            return slope * (-this.start().getX()) + this.start().getY();
        }
    }

    /**
     * Returns the point of intersection between 2 lines, if the lines intersect, and null otherwise.
     * @param other other line to find intersection point with.
     * @return Point of intersection.
     */
    public Point intersectionWith(Line other) {
        double interX, interY;
        double b1, b2, m1, m2;

        // calculate the slope of each line
        m1 = this.slope();
        m2 = other.slope();

        // if one of the lines is a point (start=end), we'll check if this point is on the other line.
        if (this.length() == 0 || other.length() == 0) {
            if (this.isPointOnLine(other.start)) {
                return other.start;
            }
            if (other.isPointOnLine(this.end)) {
                return this.end;
            }
            return null;
        }

        b1 = this.calculateYIntersection(m1);
        b2 = other.calculateYIntersection(m2);

        // if the line has infinite slope, the intersection can only occur at a single x coordinate.
        if (Double.isNaN(b1)) {
            // if the line is of form (x = a), then any intersection it may have will be at x = a.
            interX = this.start.getX();
        } else if (Double.isNaN(b2)) {
            interX = other.start.getX();
        } else {
            /* if neither of the lines are of form (x = a),
               calculate the x coordinate of the intersection point using the equations.
            */
            interX = (b2 - b1) / (m1 - m2);
        }

        // calculate the y coordinate of the intersection based on the x coordinate.
        if (Double.isNaN(b1)) {
            /* if the first line is of form (x = a),
               calculate the y coordinate of the intersection using the other line's formula.
            */
            interY = m2 * interX + b2;
        } else {
            interY = m1 * interX + b1;
        }

        Point intersection = new Point(interX, interY);

        // check if the point of intersection is on the line segments.
        if (this.isPointOnLine(intersection) && other.isPointOnLine(intersection)) {
            return intersection;
        }
        return null;
    }

    /**
     * Return true if the lines are equal, false otherwise.
     * @param other other line to check equality with.
     * @return true if the Lines are equal and false otherwise.
     */
    public boolean equals(Line other) {
        if (this.start.equals(other.start) && this.end.equals(other.end)) {
            return true;
        }
        return false;
    }

    /**
     * Draws the line of a given DrawSurface.
     * @param d the DrawSurface to draw on.
     * @param color the color of the desired line.
     */
    public void drawLine(DrawSurface d, java.awt.Color color) {
        d.setColor(color);
        d.drawLine((int) this.start.getX(), (int) this.start.getY(), (int) this.end.getX(), (int) this.end.getY());
    }
    /**
     * Draws a circle at the middle of the line.
     * @param d the drawing surface to be drawn on.
     * @param color the color of the circle to draw.
     * @param radius the radius of the circle.
     */
    public void drawMiddle(DrawSurface d, java.awt.Color color, int radius) {
        d.setColor(color);
        if (this.length() > 0) {
            d.fillCircle((int) this.middle().getX(), (int) this.middle().getY(), radius);
        }
    }

    /**
     * Draws a circle at the point of intersection of two lines.
     * @param other the line to check for intersection with.
     * @param d the drawing surface to be drawn on.
     * @param color the color of the circle to draw.
     * @param radius the radius of the circle.
     */
    public void drawIntersection(Line other, DrawSurface d, java.awt.Color color, int radius) {
        d.setColor(color);
        Point intersection = this.intersectionWith(other);
        if (intersection != null) {
            // make a dot at the point of intersection
            d.fillCircle((int) intersection.getX(), (int) intersection.getY(), radius);
        }
    }
}
