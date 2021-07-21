// ID: 316012624

package arkanoid;

import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;
import interfaces.Sprite;

/**
 * A collection of sprites in a game of Arkanoid.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * Constructs a new SpriteCollection.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Adds a new sprite to the list.
     * @param s the sprite to add.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Removes a given sprite from the collection.
     * @param s sprite to be removed.
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * calls timePassed function on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> copy = new ArrayList<>(this.sprites);
        for (Sprite s: copy) {
            s.timePassed();
        }
    }

    /**
     * calls drawOn function on all sprites.
     * @param d the DrawSurface to be drawn on.
     */
    public void drawAllOn(DrawSurface d) {
        // make a copy of the list.
        List<Sprite> copy = new ArrayList<>(this.sprites);
        for (Sprite s: copy) {
            s.drawOn(d);
        }
    }
}
