// ID: 316012624

package levelspecifications;

import java.awt.Color;
import java.awt.Image;
import arkanoid.Block;
import geometry.Rectangle;
import interfaces.BlockCreator;

/**
* Can create blocks with images.
* @author Daniel Knafel
* ID: 316012624
*/
public class ImageBlock implements BlockCreator {
    private int width, height;
    private Image img;
    private Color stroke;

    /**
     * Constructor.
     * @param width width of the block.
     * @param height height of the block.
     * @param img the background image for the block.
     */
    public ImageBlock(int width, int height, Image img) {
        this.width = width;
        this.height = height;
        this.img = img;
    }

    @Override
    public Block create(int xpos, int ypos) {
        Rectangle r = new Rectangle(xpos, ypos, this.width, this.height);
        return new Block(r, this.img, stroke);
    }

    @Override
    public void setStroke(Color strokeColor) {
        this.stroke = strokeColor;
    }
}
