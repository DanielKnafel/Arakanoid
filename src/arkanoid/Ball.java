package arkanoid;

// ID: 316012624
import java.util.Random;
import biuoop.DrawSurface;
import geometry.Point;
import interfaces.Sprite;
import geometry.Line;

/**
 * A class representing a Ball as a circle in 2D space.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity velocity;
    private GameEnvironment environment;

    /**
     * Constructs a new Ball with a center point (as integers), a radius and a color.
     * @param centerX x coordinate center point of the ball.
     * @param centerY y coordinate center point of the ball.
     * @param r radius of the ball.
     * @param color the color of the ball.
     */
    public Ball(int centerX, int centerY, int r, java.awt.Color color) {
        Random rnd = new Random();
        this.center = new Point(centerX, centerY);
        this.radius = r;
        this.color = color;
        this.setVelocityFromAngle(rnd.nextInt(180) - 90 , 5);
    }


    /**
     * Constructs a new Ball with a random center point, a radius and a color.
     * @param r radius of the ball.
     * @param center the center point of the ball.
     * @param color the color of the ball.
     * @param v the velocity of the ball.
     * @param g the GameEnvironment for the ball to operate in.
     */
    public Ball(int r, Point center, Velocity v, java.awt.Color color, GameEnvironment g) {
        this.radius = r;
        this.color = color;
        this.center = center;
        this.velocity = v;
        this.environment = g;
    }

    /**
     * Moves a balls 1 frame on the drawing surface, according to its velocity.
     * @param d the drawing surface.
     * @param maxX the maximal x coordinate of the ball.
     * @param maxY the maximal y coordinate of the ball.
     * @param minX the minimal x coordinate of the ball.
     * @param minY the minimal y coordinate of the ball.
     */
    public void makeMovment(DrawSurface d, int maxX, int maxY, int minX, int minY) {
        // apply movement to the ball
        this.moveOneStep(maxX, maxY, minX, minY);
        // draw the ball in the drawing surface.
        this.drawOn(d);
    }

    /**
     * Moves an array of balls 1 frame on the drawing surface, according to their velocities.
     * @param array the array of balls to move.
     * @param d the drawing surface.
     * @param maxX the maximal x coordinate of a ball.
     * @param maxY the maximal y coordinate of a ball.
     * @param minX the minimal x coordinate of a ball.
     * @param minY the minimal y coordinate of a ball.
     */
    public static void makeMovmentOnArray(Ball[] array, DrawSurface d, int maxX, int maxY, int minX, int minY) {
        // draw each ball in its location on the drawing surface.
        for (int i = 0; i < array.length; i++) {
            // apply movement to the ball
            array[i].makeMovment(d, maxX, maxY, minX, minY);
        }
    }

    /**
     * Inverts a ball's velocity when it hits the edge of the frame.
     * @param maxX the maximal x coordinate of a ball.
     * @param maxY the maximal y coordinate of a ball.
     * @param minX the minimal x coordinate of a ball.
     * @param minY the minimal y coordinate of a ball.
     */
    private void invertVelocity(int maxX, int maxY, int minX, int minY) {
        double x = this.center.getX();
        double y = this.center.getY();
        int r = this.radius;
        double dx = this.velocity.getDx();
        double dy = this.velocity.getDy();

        // if the ball hits the size-edges, reverse its velocity on the X-axis
        if (x + r + dx >= maxX || x - r + dx <= minX) {
            this.setVelocity(-this.getVelocity().getDx(), this.getVelocity().getDy());
        }
        // if the ball hits the top/bottom-edges, reverse its velocity on the Y-axis
        if (y + r + dy >= maxY || y - r + dy <= minY) {
            this.setVelocity(this.getVelocity().getDx(), -this.getVelocity().getDy());
        }
    }

    /**
     * Calculates and draws one step for the ball on a given drawSurface.
     * @param d a drawSurface to draw the next frame on.
     */
    public void makeMovment(DrawSurface d) {
        // apply movement to the ball
        this.moveOneStep();
        // draw the ball in the drawing surface.
        this.drawOn(d);
    }

    /**
     * Applies a movement on the ball, and calculates the new center.
     */
    public void moveOneStep() {
        // Calculate the velocity vector for the ball.
        Point start = this.center;
        Point end = new Point(this.center.getX() + this.getVelocity().getDx(),
                                this.center.getY() + this.getVelocity().getDy());

        // The trajectory that the ball is expected to follow in the current frame.
        Line trajectory = new Line(start, end);

        // Check if the ball's movement will cause a collision.
        CollisionInfo info = this.environment.getClosestCollision(trajectory);

        // if no collisions occur, move the ball as intended.
        if (info == null) {
            this.center = this.getVelocity().applyToPoint(this.center);
        // Otherwise, a collision will occur. change the ball's velocity according to angle of impact.
        } else {
            this.setCenter(setCenterAfterHit(info.collisionPoint()));
            this.setVelocity(info.collisionObject().hit(this, info.collisionPoint(), this.velocity));
        }
    }

    /**
     * Calculates a new center point for the ball at the point of collision.
     * @param collisionPoint the point of collision with a collidable.
     * @return the new center point after impact.
     */
    private Point setCenterAfterHit(Point collisionPoint) {
        double x = this.center.getX();
        double y = this.center.getY();
        double colX = collisionPoint.getX();
        double colY = collisionPoint.getY();

        double newX, newY;

        // If the ball has collided with the object from the side.
        if (x < colX) {
            newX = colX - 1;
        } else if (x > colX) {
            newX = colX + 1;
        } else {
            newX = colX;
        }

        // If the ball has collided with the object from the top/bottom.
        if (y < colY) {
            newY = colY - 1;
        } else if (y > colY) {
            newY = colY + 1;
        } else {
            newY = colY;
        }

        return new Point(newX, newY);
    }

    /**
     * Applies a movement on the ball, and calculates the new center.
     * @param maxX the maximal x coordinate of the ball.
     * @param maxY the maximal y coordinate of the ball.
     * @param minX the minimal x coordinate of the ball.
     * @param minY the minimal y coordinate of the ball.
     */
    public void moveOneStep(int maxX, int maxY, int minX, int minY) {
        // Check if the ball will hit the wall, and make the it bounce if needed.
        invertVelocity(maxX, maxY, minX, minY);
        // apply the velocity to the ball's movement.
        moveOneStep();
    }

    /**
     * Sets a velocity for the ball to move in space from a given Velocity object.
     * @param v the velocity to be set.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Sets a velocity for the ball to move in space from 2 Cartesian components.
     * @param dx x component of the velocity.
     * @param dy y component of the velocity.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Sets a velocity for the ball to move in space from a velocity vector (angle and size).
     * @param angle the angle of the velocity vector
     * @param speed the size of the velocity vector
     */
    public void setVelocityFromAngle(double angle, double speed) {
        this.velocity = Velocity.fromAngleAndSpeed(angle, speed);
    }

    /**
     * Sets a velocity for the ball to move in space from a velocity vector (angle and size).
     * @param angle the angle of the velocity vector
     * @param speed the size of the velocity vector
     */
    public void setVelocityFromAngle(int angle, int speed) {
        this.velocity = Velocity.fromAngleAndSpeed(angle, speed);
    }

    /**
     * Assigns a GameEnvironment to a ball.
     * @param env GameEnvironment to be assigned.
     */
    public void setGameEnvironment(GameEnvironment env) {
        this.environment = env;
    }

    /**
     * Returns the current velocity of the ball.
     * @return the current velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Returns the x coordinate of the center of the ball.
     * @return x coordinate of the center of the ball.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Returns the y coordinate of the center of the ball.
     * @return y coordinate of the center of the ball.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Returns the radius of the center of the ball.
     * @return Radius of the center of the ball.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * Returns the color of the ball.
     * @return the color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Sets the center of the ball to the given point.
     * @param p new ball center
     */
    public void setCenter(Point p) {
        this.center = p;
    }

    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillCircle((int) center.getX(), (int) center.getY(), radius);
        d.setColor(java.awt.Color.BLACK);
        d.drawCircle((int) center.getX(), (int) center.getY(), radius);
    }

    @Override
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    /**
     * Removes the ball from the game.
     * @param game game to remove the ball from.
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }
}
