// ID: 316012624
package screens;

import biuoop.DrawSurface;
import highscoretracker.HighScoreWriter;
import interfaces.Animation;

/**
 * An animation showing the high score stored in the file.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class HighScoresAnimation implements Animation {
    private boolean stop;

    /**
     * Constructor.
     */
    public HighScoresAnimation() {
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // Retrieve the current high score from the file.
        HighScoreWriter writer = new HighScoreWriter();
        int currentHighScore = writer.getCurrentHighScore();
        // Print the high score to the screen.
        d.drawText(10, d.getHeight() / 2, "Current high score is: " + currentHighScore, 32);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}
