// ID: 316012624

package tasks;

import arkanoid.AnimationRunner;
import interfaces.Animation;
import interfaces.Task;

/**
 * A task to show the current high score.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class ShowHighScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation highScoresAnimation;

    /**
     * Constructor.
     * @param runner an animation runner to run the animation.
     * @param highScoresAnimation an animation showing current high score.
     */
    public ShowHighScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
     }

    @Override
     public Void run() {
        this.runner.run(this.highScoresAnimation);
        return null;
     }
}