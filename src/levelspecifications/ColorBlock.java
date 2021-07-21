// ID: 316012624

package levelspecifications;

import java.awt.Color;

import arkanoid.Block;
import geometry.Rectangle;
import interfaces.BlockCreator;

/**
* Can create blocks with Colors.
* @author Daniel Knafel
* ID: 316012624
*/
public class ColorBlock implements BlockCreator {
    private int width, height;
    private Color fill;
    private Color stroke;

    /**
     * Constructor.
     * @param width width of the block.
     * @param height height of the block.
     * @param fill the background color for the block.
     */
    public ColorBlock(int width, int height, Color fill) {
        this.width = width;
        this.height = height;
        this.fill = fill;
    }

    @Override
    public Block create(int xpos, int ypos) {
        Rectangle r = new Rectangle(xpos, ypos, this.width, this.height);
        return new Block(r, this.fill, stroke);
    }

    @Override
    public void setStroke(Color strokeColor) {
        this.stroke = strokeColor;
    }
}
