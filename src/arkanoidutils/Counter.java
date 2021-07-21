// ID: 316012624

package arkanoidutils;

/**
 * A counter, used for counting things.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class Counter {
    private int counter;

    /**
     * Constructs a new counter and assigns it to 0.
     */
    public Counter() {
        this.counter = 0;
    }

    /**
     * Increase current count by a given value.
     * @param number value to increase counter by.
     */
    public void increase(int number) {
        this.counter += number;
    }

    /**
     * Decrease current count by a given value.
     * @param number value to decrease counter by.
     */
    public void decrease(int number) {
        this.counter -= number;
    }

    /**
     * Get the current count.
     * @return current counter value.
     */
    public int getValue() {
        return this.counter;
    }
}
