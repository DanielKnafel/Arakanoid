// ID: 316012624

package interfaces;

import java.awt.Color;

import arkanoid.Block;

/**
 * A creator of blocks.
 * @author Daniel Knafel
 * ID: 316012624
 */
public interface BlockCreator {
    /**
     * Create a block at the specified location.
     * @param xpos x location of the block.
     * @param ypos y location of the block.
     * @return a block with location (x,y).
     */
    Block create(int xpos, int ypos);

    /**
     * sets the stroke color of the color.
     * @param strokeColor the required color for the stroke.
     */
    void setStroke(Color strokeColor);
}