// ID: 316012624

package interfaces;

/**
 * A menu of choices fro user interaction.
 * @author Daniel Knafel
 * ID: 316012624
 * @param <T> Return type of the menu (may be Task<T>).
 */
public interface Menu<T> extends Animation {
    /**
     * Add a selection for the menu. consists of 3 parts.
     * @param key what key to press for choosing this option.
     * @param message the option message to be shown to the user.
     * @param returnVal the return value of the option (may be Task<T>).
     */
    void addSelection(String key, String message, T returnVal);
    /**
     * Return user choice, if they made one.
     * @return what to execute according to the users choice.
     */
    T getStatus();
}
