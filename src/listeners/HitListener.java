// ID: 316012624

package listeners;

import arkanoid.Block;
import arkanoid.Ball;

/**
 * An interface of objects that can notified about other objects being hit.
 * @author Daniel Knafel
 * ID: 316012624
 */
public interface HitListener {
    /**
     *  This method is called whenever the beingHit object is hit.
     * @param beingHit the block that was hit.
     * @param hitter the ball that hit the block.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
