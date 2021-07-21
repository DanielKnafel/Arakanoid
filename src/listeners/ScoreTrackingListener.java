package listeners;

import arkanoid.Ball;
import arkanoid.Block;
import arkanoidutils.Counter;

/**
 * A listener that removes a ball when it enters the Dead-Zone.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;
    private int hitScore = 5;

    /**
     * Constructs a new Score Tracking Listener from a score counter.
     * @param scoreCounter the current score counter in the game.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
       this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(this.hitScore);
    }
}
