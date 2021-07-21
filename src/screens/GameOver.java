//ID:316012624

package screens;

import arkanoidutils.Counter;
import biuoop.DrawSurface;
import interfaces.Animation;

/**
 * The losing end screen for the Arkanoid game.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class GameOver implements Animation {
    private boolean stop;
    private Counter score;

    /**
     * Constructor.
     * @param score the score accumulated during the game to be displayed.
     */
    public GameOver(Counter score) {
       this.score  = score;
       this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // Print the message to the screen.
        String str = "Game Over. Your score is " + this.score.getValue();
        d.drawText(d.getWidth() / 2 - 10 * str.length(), d.getHeight() / 2, str, 32);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
