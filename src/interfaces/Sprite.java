// ID: 316012624

package interfaces;

import arkanoid.GameLevel;
import biuoop.DrawSurface;

/**
 * An interface for a sprite.
 * @author Daniel Knafel
 * ID: 316012624
 */
public interface Sprite {

   /**
    * draw the sprite to the screen.
    * @param d the DrawSurface to be drawn on.
    */
   void drawOn(DrawSurface d);

   /**
    * notify the sprite that time has passed.
    */
   void timePassed();

   /**
    * Adds the sprite to a game.
    * @param g a game to add the sprite to.
    */
   void addToGame(GameLevel g);
}