// ID: 316012624
package arkanoidutils;

import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Menu;

/**
 * A menu for user interaction. can show options and receive user choice.
 * @author Daniel Knafel
 * ID: 316012624
 * @param <T> Menu return type.
 */
public class MenuAnimation<T> implements Menu<T> {
    private List<Selection<String, String, T>> selections;
    private boolean stop;
    private T status;
    private KeyboardSensor keyboard;

    /**
     * Constructor.
     * @param keyboard a KeyboardSensor for receiving user input.
     */
    public MenuAnimation(KeyboardSensor keyboard) {
        this.selections = new ArrayList<>();
        this.stop = false;
        this.keyboard = keyboard;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        for (int i = 0; i < this.selections.size(); i++) {
            String str = "Press " + this.selections.get(i).getKey().toString() + " to "
                            + this.selections.get(i).getMessage().toString();
            d.drawText(10, d.getHeight() / 3 + 50 * i, str, 32);
        }

        // If the user entered an input, find the matching entry in the list.
        for (Selection<String, String, T> tpl : this.selections) {
            if (this.keyboard.isPressed(tpl.getKey())) {
                this.status = tpl.getReturnVal();
                this.stop = true;
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.selections.add(new Selection<String, String, T>(key, message, returnVal));
    }

    @Override
    public T getStatus() {
        return this.status;
    }
}
