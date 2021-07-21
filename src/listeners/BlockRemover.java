// ID: 316012624

package listeners;

import arkanoid.Ball;
import arkanoid.Block;
import arkanoid.GameLevel;
import arkanoidutils.Counter;

/**
 * Removes blocks from the game, as well as keeping count of the number of blocks that remain.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * Constructs a new Block Remover from a game and a counter.
     * @param game the game to remove blocks from.
     * @param removedBlocks the amount of remaining blocks in the game.
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // Remove block from the game.
        beingHit.removeFromGame(this.game);
        // Update the block counter.
        remainingBlocks.increase(1);
    }

}
