// ID: 316012624

package arkanoid;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import interfaces.Collidable;
import interfaces.Sprite;
import listeners.HitNotifier;
import listeners.HitListener;

/**
 * A block in a game of Arkanoid.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rect;
    private List<HitListener> hitListeners;
    private boolean hasImage;
    private Color color;
    private Image img;
    private Color stroke;

    /**
     * Creates a new block from a rectangle and a color.
     * @param rect the location and size of the block.
     * @param color the color of the block.
     */
     public Block(Rectangle rect, Color color) {
        this.rect = rect;
        this.hitListeners = new ArrayList<HitListener>();
        this.color  = color;
        this.img = null;
        this.hasImage = false;
        this.stroke = null;
    }

     /**
      * Creates a new block from a rectangle, a color and a stroke.
      * @param rect the location and size of the block.
      * @param color the color of the block.
      * @param stroke the color of the outline for the block.
      */
      public Block(Rectangle rect, Color color, Color stroke) {
         this.rect = rect;
         this.hitListeners = new ArrayList<HitListener>();
         this.color  = color;
         this.img = null;
         this.hasImage = false;
         this.stroke = stroke;
     }

    /**
     * Creates a new block from a rectangle.
     * @param rect the location and size of the block.
     * @param img the image of the block background.
     */
    public Block(Rectangle rect, Image img) {
        this.rect = rect;
        this.hitListeners = new ArrayList<HitListener>();
        this.img = img;
        this.hasImage = true;
        this.color = null;
        this.stroke = null;
    }

    /**
     * Creates a new block from a rectangle.
     * @param rect the location and size of the block.
     * @param img the image of the block background.
     * @param stroke the color of the outline for the block.
     */
    public Block(Rectangle rect, Image img, Color stroke) {
        this.rect = rect;
        this.hitListeners = new ArrayList<HitListener>();
        this.img = img;
        this.hasImage = true;
        this.color = null;
        this.stroke = stroke;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // Collision point
        double x = collisionPoint.getX();
        double y = collisionPoint.getY();
        double newDx = currentVelocity.getDx();
        double newDy = currentVelocity.getDy();
        Point p = this.rect.getUpperLeft();

        // Hit on top
        if (x >= p.getX() && y == p.getY()) {
            newDy = -newDy;
        }
        // Hit from bottom
        if (x >= p.getX() && y == p.getY() + this.getCollisionRectangle().getHeight()) {
            newDy = -newDy;
        }
        // Hit from left
        if (x == p.getX() && y >= p.getY()) {
            newDx = -newDx;
        }
        // Hit from right
        if (x == p.getX() + this.getCollisionRectangle().getWidth() && y >= p.getY()) {
            newDx = -newDx;
        }

        this.notifyHit(hitter);

        return new Velocity(newDx, newDy);
    }

    @Override
    public void drawOn(DrawSurface d) {
        // Draw image.
        if (this.hasImage) {
            double x = this.rect.getUpperLeft().getX();
            double  y = this.rect.getUpperLeft().getY();
            d.drawImage((int) x, (int) y, this.img);
        // Draw color.
        } else {
            d.setColor(this.color);
            this.rect.drawOn(d);
        }

        // Draw stroke.
        if (this.stroke != null) {
            d.setColor(this.stroke);
            this.rect.drawStroke(d);
        }
    }

    @Override
    public void timePassed() {
        return;
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Removes current block from a given game.
     * @param g the game for the the block to be removed from.
     */
    public void removeFromGame(GameLevel g) {
        g.removeCollidable(this);
        g.removeSprite(this);
    }

    /**
     * Notifies all of its listeners about a hit.
     * @param hitter the ball that hit the block.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
           hl.hitEvent(this, hitter);
        }
     }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

}
