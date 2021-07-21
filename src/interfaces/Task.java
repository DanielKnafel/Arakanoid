// ID: 316012624

package interfaces;

/**
 * A task to be ran.
 * @author Daniel Knafel
 * ID: 316012624
 * @param <T> Return type of the task (may be Void).
 */
public interface Task<T> {
    /**
     * Run current task.
     * @return a parameter returned from the task (may be Void).
     */
    T run();
}
