//ID:316012624

package arkanoidutils;

import java.util.List;
import arkanoid.AnimationRunner;
import arkanoid.GameLevel;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import highscoretracker.HighScoreWriter;
import interfaces.Animation;
import interfaces.LevelInformation;
import interfaces.Menu;
import interfaces.Task;
import screens.KeyPressStoppableAnimation;
import screens.YouWin;
import tasks.ShowHighScoresTask;
import screens.GameOver;
import screens.HighScoresAnimation;

/**
 * Controls the flow of the game (i.e switching levels, ending the game..).
 * @author Daniel Knafel
 * ID: 316012624
 */
public class GameFlow {
    // A counter to keep score. Carried throughout levels.
    private Counter score;
    // the FPS rate of the game.
    private int framesPerSecond = 60;
    private GUI gui;
    // Winning flag.
    private boolean hasWon;
    private final int width = 800, height = 600;

    /**
     * Constructor.
     */
    public GameFlow() {
        this.score = new Counter();
        this.hasWon = true;
        this.gui = new GUI("Arkanoid", this.width, this.height);
    }

    /**
     * Runs the menu and adds functionality to it.
     * @param levels the list of levels to run.
     */
    public void runMenu(List<LevelInformation> levels) {
        // Initiate the GUI and runner.
        AnimationRunner runner = new AnimationRunner(this.framesPerSecond, this.gui);;
        Menu<Task<Void>> menu = new MenuAnimation<>(gui.getKeyboardSensor());

        // Create a KeyPressStoppableAnimation for the highScore.
        Animation highScore = new KeyPressStoppableAnimation(gui.getKeyboardSensor(), KeyboardSensor.SPACE_KEY,
                                                                 new HighScoresAnimation());
        // Create a task for the high score option in the menu.
        Task<Void> highScoreTask = new ShowHighScoresTask(runner, highScore);

        // Create the functionality for the menu using Task<>.
        menu.addSelection("s", "Start New Game", new Task<Void>() {
            @Override
            public Void run() {
                runLevels(levels);
                highScoreTask.run();
                return null;
            }
        });
        menu.addSelection("h", "Show High Score", highScoreTask);
        menu.addSelection("q", "Quit", new Task<Void>() {
            @Override
            public Void run() {
                gui.close();
                System.exit(0);
                return null;
            }
        });

        // Run the menu animation
         runner.run(menu);
         // Receive user input.
         Task<Void> result = menu.getStatus();
         // Execute user choice.
         result.run();
         // Run menu again.
         runMenu(levels);
    }

    /**
     * Runs a given list of levels in succession, and monitors rather the user lost or won the game.
     * @param levels list of levels to play.
     */
    public void runLevels(List<LevelInformation> levels) {
        // Make sure there are levels to be played.
        if (levels.isEmpty()) {
            return;
        }
        this.score = new Counter();
        AnimationRunner runner = null;
        // Play each level on the list.
        for (LevelInformation levelInfo : levels) {
           runner = new AnimationRunner(this.framesPerSecond, this.gui);
           GameLevel level = new GameLevel(levelInfo, runner, gui.getKeyboardSensor(), score);

           level.initialize();
           level.playOneTurn();

           // Check if the level ended due to the user loosing. if so, stop the game.
           if (level.isOutOfBalls()) {
               this.hasWon = false;
               break;
           }
        }

        // Show matching end screen (win/ lose).
        if (this.hasWon) {
            runner.run(new KeyPressStoppableAnimation(gui.getKeyboardSensor(),
                        KeyboardSensor.SPACE_KEY, new YouWin(this.score)));
        } else {
            runner.run(new KeyPressStoppableAnimation(gui.getKeyboardSensor(),
                        KeyboardSensor.SPACE_KEY, new GameOver(this.score)));
        }

        HighScoreWriter writer = new HighScoreWriter();
        writer.storeHighScore(this.score.getValue());
    }
}