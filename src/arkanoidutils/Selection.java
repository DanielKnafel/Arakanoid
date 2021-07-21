// ID: 316012624
package arkanoidutils;

/**
 * A tuple of 3 generic elements.
 * @author Daniel Knafel
 * ID: 316012624
 * @param <E> first argument type.
 * @param <F> first argument type.
 * @param <G> first argument type.
 */
public class Selection<E, F, G> {
    private E key;
    private F message;
    private G returnVal;

    /**
     * Constructor.
     * @param key first argument.
     * @param message second argument.
     * @param returnVal third argument.
     */
    public Selection(E key, F message, G returnVal) {
        this.key = key;
        this.message = message;
        this.returnVal = returnVal;
    }

    /**
     * Returns the first argument.
     * @return the first argument.
     */
    public E getKey() {
        return this.key;
    }

    /**
     * Returns the second argument.
     * @return the second argument.
     */
    public F getMessage() {
        return this.message;
    }

    /**
     * Returns the third argument.
     * @return the third argument.
     */
    public G getReturnVal() {
        return this.returnVal;
    }
}
