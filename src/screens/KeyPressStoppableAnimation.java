//ID:316012624

package screens;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Animation;

/**
 * A wrapper class for an Animation. Able to stop a given animation by user input.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean isAlreadyPressed;
    private boolean stop;

    /**
     * Constructor.
     * @param sensor a KeyboardSensor for user input.
     * @param key the key needed to be pressed to stop the animation.
     * @param animation the wrapped animation.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.isAlreadyPressed = true;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // User input.
        if (this.sensor.isPressed(key)) {
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            this.isAlreadyPressed = false;
        }
        // Delegate to the wrapped animation.
        this.animation.doOneFrame(d);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
