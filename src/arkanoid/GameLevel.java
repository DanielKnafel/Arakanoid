// ID: 316012624

package arkanoid;

import java.awt.Color;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Point;
import geometry.Rectangle;
import interfaces.Animation;
import interfaces.Collidable;
import interfaces.LevelInformation;
import interfaces.Sprite;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;
import arkanoidutils.CountdownAnimation;
import arkanoidutils.Counter;
import arkanoidutils.LevelName;
import screens.KeyPressStoppableAnimation;
import screens.PauseScreen;
import arkanoidutils.ScoreIndicator;

/**
 * A level of Arkanoid.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class GameLevel implements Animation {
    private LevelInformation level;
    private AnimationRunner runner;
    // A KeyboardSensor for user input.
    private KeyboardSensor keyboard;
    // Indicates if the level should stop running (i.e level cleared/ level failed..).
    private boolean running;
    private Paddle paddle;
    // Collections of sprites and collidable.
    private SpriteCollection sprites;
    private GameEnvironment environment;
    // Counters
    private Counter removedBlockCounter;
    private Counter ballsCounter;
    private Counter currentScore;
    private int levelClearedScore = 100;

    /**
     * Constructs a new GameLevel.
     * @param level the level information.
     * @param runner an AnimationRunner to run the level.
     * @param keyboard a KeyboardSensor for user input.
     * @param score a score counter.
     */
    public GameLevel(LevelInformation level, AnimationRunner runner, KeyboardSensor keyboard, Counter score) {
        this.level = level;
        this.runner = runner;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.keyboard = keyboard;
        this.paddle = new Paddle(keyboard, level.width(), level.height(), this.level.edgeBuffer(),
                                    level.paddleWidth(), level.paddleHeight(), level.paddleSpeed());
        this.removedBlockCounter = new Counter();
        this.ballsCounter = new Counter();
        this.currentScore = score;
    }

    /**
     * Adds a collidable to the game.
     * @param c the collidable to add.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adds a sprite to the game.
     * @param s the sprite to add.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Remove's a collidable from the game.
     * @param c collidable to be removed.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Remove's a Sprite from the game.
     * @param s sprite to be removed.
     */
    public void removeSprite(Sprite s) {
       this.sprites.removeSprite(s);
    }

    /**
     * Adds the 4 edges of the screen as collidables.
     */
    private void addFrameEdges() {
        int width = this.level.width(), height = this.level.height();
        // top edge
        Block top = new Block(new Rectangle(0, this.level.edgeBuffer(), width, this.level.edgeBuffer()),
                                                Color.DARK_GRAY);
        // bottom edge
        Block bottom = new Block(new Rectangle(0, height, width, this.level.edgeBuffer()), Color.DARK_GRAY);
        // left edge
        Block left = new Block(new Rectangle(0, this.level.edgeBuffer(), this.level.edgeBuffer(), height),
                                                Color.DARK_GRAY);
        // right edge
        Block right = new Block(new Rectangle(width - this.level.edgeBuffer(), this.level.edgeBuffer(),
                                    this.level.edgeBuffer(), height), Color.DARK_GRAY);

        top.addToGame(this);
        bottom.addToGame(this);
        left.addToGame(this);
        right.addToGame(this);
        // Ball death-Zone.
        bottom.addHitListener(new BallRemover(this, this.ballsCounter));
    }

    /**
     * Adds the playing blocks to the game.
     */
    private void addBlocks() {
        BlockRemover remover = new BlockRemover(this, this.removedBlockCounter);
        ScoreTrackingListener scoreListener = new ScoreTrackingListener(this.currentScore);
        for (Block b : this.level.blocks()) {
            b.addToGame(this);
            // Add listeners to each block.
            b.addHitListener(remover);
            b.addHitListener(scoreListener);
        }
    }

    /**
     * Adds balls to the game.
     */
    private void addBalls() {
        Point p = new Point(this.level.width() / 2, this.level.height() - 2 * this.level.paddleHeight());
        for (Velocity v : this.level.initialBallVelocities()) {
            Ball b = new Ball(this.level.ballSize(), p, v, this.level.ballColor(), this.environment);
            b.addToGame(this);
            // Update the ball counter.
            this.ballsCounter.increase(1);
        }
    }

    /**
     * Create and add the score indicator to the game.
     */
    private void addInfoBar() {
        Rectangle infoBarRect = new Rectangle(0, 0, this.level.width(), 20);
        Block infoBar = new Block(infoBarRect, Color.LIGHT_GRAY);

        ScoreIndicator indicator = new ScoreIndicator(this.currentScore, infoBarRect);
        LevelName levelName = new LevelName(this.level.levelName(), infoBarRect);

        addSprite(infoBar);
        this.addSprite(indicator);
        this.addSprite(levelName);
    }

    /**
     * Updates the current score by a given value.
     * @param number the value to increase/ decrease the current score by.
     */
    public void updateScore(int number) {
        this.currentScore.increase(number);
    }

    /**
     *  Initialize a new level: create the edges, blocks, balls, paddle and info bar, and add them to the game.
     */
    public void initialize() {
        this.addSprite(level.getBackground());
        // Create edge blocks.
        addFrameEdges();
        // Add playing blocks to the game.
        addBlocks();
        // Add balls to the game.
        addBalls();
        // Add the paddle.
        this.paddle.addToGame(this);
        // Add the score indicator, lives counter and level name.
        addInfoBar();
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // Place all sprites on the drawSurface.
        this.sprites.drawAllOn(d);
        // Move all the sprites to their next location.
        this.sprites.notifyAllTimePassed();

        // User cleared the level.
        if (this.removedBlockCounter.getValue() == level.numBlocks()) {
            // Add 100 point for clearing the level.
            this.updateScore(this.levelClearedScore);
            this.running = false;
        }

        // User lost all his balls.
        if (this.ballsCounter.getValue() <= 0) {
            this.running = false;
        }

        // Pause screen toggle.
        if (this.keyboard.isPressed("p") || this.keyboard.isPressed("P")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                                                                new PauseScreen()));
        }
    }

    /**
     * Initiates a countdown and runs one level of Arkanoid.
     */
    public void playOneTurn() {
        // A 3-second countdown before turn starts.
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        // use our runner to run the current animation -- which is one turn of the game.
        this.runner.run(this);
     }

    /**
     * Returns a boolean value if the user lost all his balls or not.
     * @return a boolean value if the user lost all his balls or not.
     */
    public boolean isOutOfBalls() {
        if (this.ballsCounter.getValue() == 0) {
            return true;
        }
        return false;
    }
}