// ID:316012624

package levels;

import java.awt.Color;
import java.awt.Image;

import geometry.Point;
import geometry.Rectangle;
import java.util.ArrayList;
import java.util.List;
import arkanoid.Block;
import arkanoid.Velocity;
import interfaces.LevelInformation;
import interfaces.Sprite;

/**
 * A base class for level of the Arkanoid game. Holds basic features or every level.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class Level implements LevelInformation {
    private int numberOfBalls;
    private List<Velocity> initialBallVelocities;
    private int paddleSpeed;
    private int paddleWidth;
    private String levelName;
    private Sprite background;
    private List<Block> blocks;
    private int width = 800, height = 600;
    // Spacer on the edge of the frame.
    private int edgeBuffer = 10;
    private int paddleHeight = 20;
    private int ballSize = 5;
    private Color ballColor = Color.WHITE;
    private int numOfBlocksToClear;
    private int rowHeight;
    private int blocksStartX;
    private int blocksStartY;

    /**
     * Constructor.
     * @param paddleSpeed speed of the paddle in the current level.
     * @param paddleWidth width of the paddle in the current level.
     * @param levelName name of the current level.
     * @param backgroundColor background color of the current level.
     * @param numOfBlocksToClear the amount of blocks needed to be destroyed for level to clear.
     * @param rowHeight space between each 2 rows of blocks.
     * @param startingBlock the starting point of the top left block.
     */
    public Level(int paddleSpeed, int paddleWidth, String levelName, Color backgroundColor, int numOfBlocksToClear,
                        int rowHeight, Point startingBlock) {
        this.numberOfBalls = 0;
        this.initialBallVelocities = new ArrayList<>();
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.levelName = levelName;
        this.blocks = new ArrayList<>();
        this.background = new Block(new Rectangle(0, 0, this.width, this.height), backgroundColor);
        this.numOfBlocksToClear = numOfBlocksToClear;
        this.rowHeight = rowHeight;
        this.blocksStartX = (int) startingBlock.getX();
        this.blocksStartY = (int) startingBlock.getY();
    }

    /**
     * Constructor.
     * @param paddleSpeed speed of the paddle in the current level.
     * @param paddleWidth width of the paddle in the current level.
     * @param levelName name of the current level.
     * @param backgroundImage background image of the current level.
     * @param numOfBlocksToClear the amount of blocks needed to be destroyed for level to clear.
     * @param rowHeight space between each 2 rows of blocks.
     * @param startingBlock the starting point of the top left block.
     */
    public Level(int paddleSpeed, int paddleWidth, String levelName, Image backgroundImage, int numOfBlocksToClear,
                    int rowHeight, Point startingBlock) {
        this.numberOfBalls = 0;
        this.initialBallVelocities = new ArrayList<>();
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.levelName = levelName;
        this.blocks = new ArrayList<>();
        this.background = new Block(new Rectangle(0, 0, this.width, this.height), backgroundImage);
        this.numOfBlocksToClear = numOfBlocksToClear;
        this.rowHeight = rowHeight;
        this.blocksStartX = (int) startingBlock.getX();
        this.blocksStartY = (int) startingBlock.getY();
    }

    // Getters:
    @Override
    public int numberOfBalls() {
        return this.numberOfBalls;
    }
    @Override
    public List<Velocity> initialBallVelocities() {
        return this.initialBallVelocities;
    }
    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }
    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }
    @Override
    public String levelName() {
        return this.levelName;
    }
    @Override
    public Sprite getBackground() {
        return this.background;
    }
    @Override
    public List<Block> blocks() {
        return this.blocks;
    }
    @Override
    public int width() {
        return this.width;
    }
    @Override
    public int height() {
        return this.height;
    }
    @Override
    public int edgeBuffer() {
        return this.edgeBuffer;
    }
    @Override
    public int paddleHeight() {
        return this.paddleHeight;
    }
    @Override
    public int ballSize() {
        return this.ballSize;
    }
    @Override
    public Color ballColor() {
        return this.ballColor;
    }
    @Override
    public int numBlocks() {
        return this.numOfBlocksToClear;
    }
    @Override
    public int rowHeight() {
        return this.rowHeight;
    }
    @Override
    public int blocksStartY() {
        return this.blocksStartY;
    }
    @Override
    public int blocksStartX() {
        return this.blocksStartX;
    }

    // Setters:
    /**
     * Adds a block to the blocks list.
     * @param b block to add.
     */
    public void addBlock(Block b) {
        this.blocks.add(b);
    }

    /**
     * Sets a velocity for a ball in the level.
     * @param v the required velocity to be assigned for a ball.
     */
    public void addVelocity(Velocity v) {
        this.initialBallVelocities.add(v);
        this.numberOfBalls++;
    }

    /**
     * Sets a list of ball velocities for level.
     * @param v the ball velocities to be added to the level.
     */
    public void addVelocities(List<Velocity> v) {
        this.initialBallVelocities.addAll(v);
        this.numberOfBalls = v.size();
    }
}
