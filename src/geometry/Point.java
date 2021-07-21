package geometry;
// ID: 316012624
import java.util.Random;

/**
 * This class represents a Point in a 2D plane.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class Point {
    private double x, y;

    /**
     * Constructs a new Point.
     * @param x x value for the point.
     * @param y y value for the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Return the distance of this point to the other point.
     * @param other other point to calculate distance from.
     * @return the distance between the 2 points.
     */
    public double distance(Point other) {
        // d = sqrt((x1-x2)^2 + (y1-y2)^2)
        return Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2));
    }

    /**
     * Return true if the points are equal, false otherwise.
     * @param other other point to evaluate equality to.
     * @return true if the point are equal and false otherwise.
     */
    public boolean equals(Point other) {
        if (other.x == this.x && other.y == this.y) {
            return true;
        }
        return false;
    }

    /**
     * Creates a random point in a given range.
     * @param maxX the maximal x index of the random point
     * @param maxY the maximal y index of the random point
     * @param minX the minimal x index of the random point
     * @param minY the minimal y index of the random point
     * @return a random point in a 2D plane.
     */
    public static Point getRandomPoint(int maxX, int maxY, int minX, int minY) {
        Random rand = new Random();
        int x = rand.nextInt(maxX - minX) + minX;
        int y = rand.nextInt(maxY - minY) + minY;
        return new Point(x, y);
    }

    /**
     * Return the x value of this point.
     * @return x coordinate.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Return the y values of this point.
     * @return y coordinate.
     */
    public double getY() {
        return this.y;
    }
}