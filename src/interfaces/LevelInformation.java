//ID:316012624

package interfaces;

import java.awt.Color;
import java.util.List;
import arkanoid.Block;
import arkanoid.Velocity;

/**
 * An interface for all arkanoid game levels.
 * @author Daniel Knafel
 * ID: 316012624
 */
public interface LevelInformation {
    /**
     * The amount of balls in the level.
     * @return the amount of balls in the level.
     */
    int numberOfBalls();

    /**
     * Returns a list of velocities for the balls in the level.
     * @return a list of velocities for the balls in the level.
     */
    List<Velocity> initialBallVelocities();

    /**
     * Getter for the speed of the paddle in the level.
     * @return the speed of the paddle in the level.
     */
    int paddleSpeed();

    /**
     * Getter for the width of the paddle in the level.
     * @return the width of the paddle in the level.
     */
    int paddleWidth();

    /**
     * Getter for the height of the paddle in the level.
     * @return the width of the paddle in the level.
     */
    int paddleHeight();

    /**
     * The level name. it will be displayed at the top of the screen when played.
     * @return the current level's name.
     */
    String levelName();

    /**
     * Returns a sprite with the background of the level.
     * @return The background sprite.
     */
    Sprite getBackground();

    /**
     * The Blocks that make up this level. each block contains its size, color and location.
     * @return A list of blocks in the current level.
     */
    List<Block> blocks();

    /**
     * Getter for the width of the GUI in the current level.
     * @return the width of the GUI in the current level.
     */
    int width();

    /**
     * Getter for the height of the GUI in the current level.
     * @return the height of the GUI in the current level.
     */
    int height();

    /**
     * Getter for the size of the edge buffer (edges of the screen) in the current level.
     * @return the size of the edge buffer in the current level.
     */
    int edgeBuffer();

    /**
     * Getter for the size of the balls in the current level.
     * @return the size of the balls in the current level.
     */
    int ballSize();

    /**
     * Returns the color of the balls in the level.
     * @return the color of the balls in the level.
     */
    Color ballColor();

    /**
     * Return the number of blocks required to clear the level.
     * @return the number of blocks required to clear the level.
     */
    int numBlocks();

    /**
     * Return the height between each two rows of blocks.
     * @return the height between each two rows of blocks.
     */
    int rowHeight();

    /**
     * Return the starting y coordinate of the first block in a row.
     * @return the y coordinate of the first block in a row.
     */
    int blocksStartY();

    /**
     * Return the starting x coordinate of the first block in a row.
     * @return the x coordinate of the first block in a row.
     */
    int blocksStartX();
}
