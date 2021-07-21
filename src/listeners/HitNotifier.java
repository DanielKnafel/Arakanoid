// ID: 316012624
package listeners;

/**
 * An interface of objects that can notify about being hit.
 * @author Daniel Knafel
 * ID: 316012624
 */
public interface HitNotifier {
    /**
     * Add a given Hit Listener to the list of listeners to hit events.
     * @param hl the HitListener to be removed.
     */
    void addHitListener(HitListener hl);

    /**
     * Remove a given Hit Listener from the list of listeners to hit events.
     * @param hl the HitListener to be removed.
     */
    void removeHitListener(HitListener hl);
}
