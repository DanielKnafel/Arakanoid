//ID:316012624
package arkanoidutils;

import arkanoid.GameLevel;
import interfaces.Sprite;
import biuoop.DrawSurface;
import geometry.Rectangle;

/**
* A main class for the Arkanoid game.
* @author Daniel Knafel
* ID: 316012624
*/
public class ScoreIndicator implements Sprite {
    private Counter currentScore;
    private Rectangle rect;
    private int textFont = 15;

    /**
     * Constructs a new Score Indicator.
     * @param currentScore the provided score counter to be shown.
     * @param rect the position of the score counter.
     */
    public ScoreIndicator(Counter currentScore, Rectangle rect) {
        this.currentScore = currentScore;
        this.rect = rect;
    }

    @Override
    public void drawOn(DrawSurface d) {
        // Create the text to be printed.
        String str = "Score: " + String.valueOf(this.currentScore.getValue());
        d.setColor(java.awt.Color.BLACK);
        // Put the text on the center of the surrounding rectangle.
        int xAdjustment = 30;
        int yAdjustment = 5;
        int xPos = (int) (this.rect.getWidth() / 2) - xAdjustment;
        int yPos = (int) this.rect.getHeight() - yAdjustment;
        // Draw the text.
        d.drawText(xPos, yPos, str, this.textFont);
    }

    @Override
    public void timePassed() {
        return;
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
