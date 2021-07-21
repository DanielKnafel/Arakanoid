//ID:316012624

package arkanoidutils;

import arkanoid.GameLevel;
import biuoop.DrawSurface;
import geometry.Rectangle;
import interfaces.Sprite;

/**
 * The level name info sprite. will be shown in the info bar when level is played.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class LevelName implements Sprite {
    private String name;
    private Rectangle rect;
    private int textFont = 15;

    /**
     * Constructs a new Level Name.
     * @param name the name of the level.
     * @param rect the position of the score counter.
     */
    public LevelName(String name, Rectangle rect) {
        this.name = name;
        this.rect = rect;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(java.awt.Color.BLACK);
        // Put the text on the right side of the surrounding rectangle.
        int xAdjustment = 30;
        int yAdjustment = 5;
        int xPos = (int) (this.rect.getWidth() * 3 / 5) - xAdjustment;
        int yPos = (int) this.rect.getHeight() - yAdjustment;
        // Draw the text.
        d.drawText(xPos, yPos, "Level Name: " + this.name, this.textFont);
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
