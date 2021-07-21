// ID: 316012624

package arkanoid;

import java.util.List;
import java.util.ArrayList;
import geometry.Point;
import interfaces.Collidable;
import geometry.Line;


/**
 * A collection of collidables in a game of Arkanoid.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * Constructs a new GameEnvironment of a given size.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Adds a given collidable to the environment.
     * @param c a collidable to add to the collection.
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     *  Assume an object moving from line.start() to line.end().
     *  If this object will not collide with any of the collidables in this collection, return null.
     *  Else, return the information about the closest collision that is going to occur.
     * @param trajectory the trajectory to be checked for collisions.
     * @return a CollisionInfo containing the collision point and the object collided with.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point p;
        // make a list to store possible collision points.
        List<CollisionInfo> collisions = new ArrayList<>();
        // make a copy of the list.
        List<Collidable> copy = new ArrayList<>(this.collidables);

        for (Collidable c : copy) {
            p = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (p != null) {
                collisions.add(new CollisionInfo(p, c));
            }
        }
        return getShortestDistance(collisions, trajectory.start());
    }

    /**
     * Return the shortest distance between a list of points and a given point.
     * @param collisions a list of point.
     * @param start the point to calculate the distance to.
     * @return the closest point from the list to the start point.
     */
    private CollisionInfo getShortestDistance(List<CollisionInfo> collisions, Point start) {
        if (collisions.size() == 0) {
            return null;
        }

        CollisionInfo info = collisions.get(0);
        double shortest = start.distance(info.collisionPoint());

        for (CollisionInfo hitObject : collisions) {
            Point p = hitObject.collisionPoint();
            double currentDistance = start.distance(p);
            if (currentDistance < shortest) {
                shortest = currentDistance;
                info = hitObject;
            }
        }
        return info;
    }

    /**
     * Removes a given collidable from the collection.
     * @param c collidable to be removed.
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }
}
