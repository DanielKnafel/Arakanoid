// ID: 316012624

package interfaces;
import geometry.Rectangle;
import arkanoid.Ball;
import arkanoid.Velocity;
import geometry.Point;

/**
 * An interface for collidable objects.
 * @author Daniel Knafel
 * ID: 316012624
 */
public interface Collidable {
    /**
     * Returns the "collision shape" of the object.
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that it has been collided with at collisionPoint with a given velocity.
     * @param hitter the ball that hit the block.
     * @param collisionPoint the point of collision.
     * @param currentVelocity the velocity of the ball at the moment of collision.
     * @return a new velocity that is expected after the hit (based on the force the object inflicted on the ball).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
