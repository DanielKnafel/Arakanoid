// ID: 316012624

package arkanoid;

import arkanoidutils.Regions;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Point;
import geometry.Rectangle;
import interfaces.Collidable;
import interfaces.Sprite;

/**
 * A user controlled paddle.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Block paddle;
    // Size of the paddle.
    private double paddleWidth, paddleHeight;
    private double frameWidth, frameHeight;
    // The width of the border of the screen.
    private double buffer;
    private double paddleSpeed;

    /**
     * Constructs a new paddle from a KeyboardSensor and and screen dimensions.
     * @param keyboard a KeyboardSensor for reading user input.
     * @param frameWidth the width of the screen.
     * @param frameHeight the height of the screen.
     * @param buffer the edge-buffer of the screen.
     * @param paddleWidth the width of the paddle.
     * @param paddleHeight the height of the paddle.
     * @param paddleSpeed the speed of the paddle.
     */
    public Paddle(biuoop.KeyboardSensor keyboard, double frameWidth, double frameHeight,
                    double buffer, double paddleWidth, double paddleHeight, double paddleSpeed) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.keyboard = keyboard;
        this.buffer = buffer;
        this.paddleWidth = paddleWidth;
        this.paddleHeight = paddleHeight;
        this.paddleSpeed = paddleSpeed;
        // Place the paddle in the bottom-middle of the screen.
        double x = this.frameWidth / 2 - paddleWidth / 2;
        double y = this.frameHeight - this.paddleHeight - this.buffer;
        Point p = new Point(x, y);
        this.paddle = new Block(new Rectangle(p, this.paddleWidth, this.paddleHeight), java.awt.Color.CYAN);
    }

    /**
     * Shift the paddle a bit to the left.
     */
    public void moveLeft() {
        Point current = this.paddle.getCollisionRectangle().getUpperLeft();
        // A left shift would take it 1/15 of its width to the left.
        double next = current.getX() - this.paddleSpeed;

        // If the next position is still inside the frame, perform the shift.
        if (next >= this.buffer) {
            this.paddle.getCollisionRectangle().setUpperLeft(next, current.getY());
        // Otherwise, move the paddle to the left edge of the frame.
        } else {
            this.paddle.getCollisionRectangle().setUpperLeft(this.buffer, current.getY());
        }
    }

    /**
     * Shift the paddle a bit to the right.
     */
    public void moveRight() {
        // The current position on the paddle.
        Point current = this.paddle.getCollisionRectangle().getUpperLeft();
        // A right shift would take it 1/15 of its width to the right.
        double next = current.getX() + this.paddleSpeed;

        // If the next position is still inside the frame, perform the shift.
        if (next + this.paddleWidth <= this.frameWidth - this.buffer) {
            this.paddle.getCollisionRectangle().setUpperLeft(next, current.getY());
        // Otherwise, move the paddle to the right edge of the frame.
        } else {
            this.paddle.getCollisionRectangle().setUpperLeft(this.frameWidth - this.paddleWidth - this.buffer,
                                                                current.getY());
        }
    }

    @Override
    public void timePassed() {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        } else if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        this.paddle.drawOn(d);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.paddle.getCollisionRectangle();
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // Divide the paddle to 5 regions.
        int numOfRegions = 5;
        Regions regions = new Regions(numOfRegions, this.paddleWidth / numOfRegions);
        // Calculate the point of impact, relative too the left side of the paddle.
        double regionX = collisionPoint.getX() - this.paddle.getCollisionRectangle().getUpperLeft().getX();
        int region = regions.inWhichRange(regionX);

        // Manage the return angle from the paddle, according to the region of the paddle being hit.
        switch (region) {
            case 1: return Velocity.fromAngleAndSpeed(300, currentVelocity.getSpeed());
            case 2: return Velocity.fromAngleAndSpeed(330, currentVelocity.getSpeed());
            case 3: return Velocity.fromAngleAndSpeed(0, currentVelocity.getSpeed());
            case 4: return Velocity.fromAngleAndSpeed(30, currentVelocity.getSpeed());
            case 5: return Velocity.fromAngleAndSpeed(60, currentVelocity.getSpeed());
            default: return new Velocity(-currentVelocity.getDx(), -currentVelocity.getDy());
        }
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Returns the width of the paddle.
     * @return width of the paddle.
     */
    public double getWidth() {
        return this.paddleWidth;
    }

    /**
     * Returns the height of the paddle.
     * @return height of the paddle.
     */
    public double getHeight() {
        return this.paddleHeight;
    }
}
