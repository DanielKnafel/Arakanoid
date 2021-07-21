// ID:316012624

package interfaces;

import biuoop.DrawSurface;

/**
 * Interface for all animation in the game (i.e levels, countdowns..).
 * @author Daniel Knafel
 * ID: 316012624
 */
public interface Animation {
    /**
     * Draws one frame onto a given DrawSurface.
     * @param d DrawSurface to be drawn on.
     */
    void doOneFrame(DrawSurface d);

    /**
     * Stopping condition for the animation.
     * @return boolean value indicating if the anummiation should be stopped or not.
     */
    boolean shouldStop();
}
