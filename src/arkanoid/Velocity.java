// ID: 316012624

package arkanoid;

import geometry.Epsilon;
import geometry.Point;

/**
 * Velocity is used to move an object in the Cartesian axes system.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class Velocity {
    // Cartesian components of a 2D moving object
    private double dx;
    private double dy;

   /**
    * Constructs a new Velocity with x and y parts.
    * @param dx x component of the velocity.
    * @param dy y component of the velocity.
    */
   public Velocity(double dx, double dy) {
       this.dx = dx;
       this.dy = dy;
   }

   /**
    * Constructs a new Velocity with x and y parts, given by a vector (angle and size).
    * @param angle the angel of the velocity vector.
    * @param speed the length of the velocity vector.
    * @return new Velocity with x and y parts.
    */
   public static Velocity fromAngleAndSpeed(double angle, double speed) {
       double dx = speed * Math.sin(Math.toRadians(angle));
       double dy = -speed * Math.cos(Math.toRadians(angle));
       if (Math.abs(dy) < Epsilon.EPSILON) {
           dy = 0;
       }
       if (Math.abs(dx) < Epsilon.EPSILON) {
           dx = 0;
       }
       return new Velocity(dx, dy);
    }

   /**
    * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
    * @param p point to move by velocity.
    * @return moved point.
    */
   public Point applyToPoint(Point p) {
       return new Point(p.getX() + dx, p.getY() + dy);
   }

   /**
   * returns the x component on the velocity.
   * @return x component on the velocity.
   */
   public double getDx() {
       return this.dx;
   }

   /**
    * returns the y component on the velocity.
    * @return y component on the velocity.
    */
   public double getDy() {
       return this.dy;
   }

   /**
    * Return the size of the speed vector.
    * @return size of the speed vector.
    */
   public double getSpeed() {
       return Math.sqrt(Math.pow(this.dx, 2) + Math.pow(this.dy, 2));
   }
}
