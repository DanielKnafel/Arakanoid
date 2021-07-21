// ID: 316012624

package arkanoid;

import geometry.Point;
import interfaces.Collidable;

/**
 * An object storing information about a collision between a collidable and a sprite.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * Constructs a new CollisionInfo from the point of collision and the object collided with.
     * @param collisionPoint point of collision.
     * @param collisionObject object collided with.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * Returns the point at which the collision occurred.
     * @return the point at which the collision occurred.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * Returns the collidable object involved in the collision.
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
