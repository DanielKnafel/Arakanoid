// ID: 316012624

package listeners;

import arkanoid.Ball;
import arkanoid.Block;
import arkanoid.GameLevel;
import arkanoidutils.Counter;

/**
 * Removes balls from the game, as well as keeping count of the number of balls remaining.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    /**
     * Constructs a new Ball Remover from a game and a counter.
     * @param game the game to remove balls from.
     * @param remainingBalls the amount of remaining balls in the game.
     */
    public BallRemover(GameLevel game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // Remove the ball from the game.
        hitter.removeFromGame(this.game);
        // Update the ball counter.
        this.remainingBalls.decrease(1);
    }

}
