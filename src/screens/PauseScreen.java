//ID:316012624

package screens;

import biuoop.DrawSurface;
import interfaces.Animation;

/**
 * A pause screen for the Arkanoid game.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class PauseScreen implements Animation {
    private boolean stop;

    /**
     * Constructor.
     */
    public PauseScreen() {
       this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // Draw Paused text in the middle of the screen.
       d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
