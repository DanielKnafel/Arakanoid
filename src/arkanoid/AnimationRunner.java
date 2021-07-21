// ID:316012624

package arkanoid;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import interfaces.Animation;

/**
 * A class responsible for running Animation type objects.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class AnimationRunner {
    private GUI gui;
    private Sleeper sleeper;

    private int framesPerSecond;

    /**
     * Constructs a new AnimationRunner.
     * @param framesPerSecond the amount of frames to play each second.
     * @param gui the GUI object to perform the animation.
     */
    public AnimationRunner(int framesPerSecond, GUI gui) {
        this.framesPerSecond = framesPerSecond;
        this.sleeper = new Sleeper();
        this.gui = gui;
    }

    /**
     * Run an animation.
     * @param animation the animation to run.
     */
    public void run(Animation animation) {
        // Calculate the duration of every frame.
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        // Animate as long as the animation should be played.
        while (!animation.shouldStop()) {
            // timing
            long startTime = System.currentTimeMillis();

            // Perform the animation of the screen.
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d);
            gui.show(d);

            // If needed, hold the current frame to match the required frame duration.
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
              this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
