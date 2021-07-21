// ID: 316012624
package arkanoidutils;

import java.awt.Color;

import arkanoid.SpriteCollection;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import interfaces.Animation;


/**
 * The CountdownAnimation will display the given gameScreen for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will appear on the screen for
 * (numOfSeconds / countFrom) seconds, before it is replaced with the next digit.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class CountdownAnimation implements Animation {
    private int countFrom, secondsToShow;
    private SpriteCollection gameScreen;
    private boolean stop;
    private Sleeper sleeper;
    private long timePerFrame;

    /**
     * Constructor.
     * @param numOfSeconds the total length of the count down.
     * @param countFrom the number to count down from.
     * @param gameScreen the level that is about to be played, in order to show it behind the counter.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.countFrom = countFrom;
        this.secondsToShow = countFrom;
        this.gameScreen = gameScreen;
        this.stop = false;
        this.sleeper = new Sleeper();
        // Calculate the duration each digit will be shown for, in ms.
        this.timePerFrame = (long) (1000 * numOfSeconds / countFrom);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        if (this.countFrom >= 0) {
            // Draw the level behind the counter.
            this.gameScreen.drawAllOn(d);
            d.setColor(Color.RED);
            // Display the current digit in the countdown.
            d.drawText(d.getWidth() / 2, d.getHeight() / 2, String.valueOf(this.countFrom), 32);
            // Hold the digit for the required amount of time.
            if (this.countFrom != secondsToShow) {
                this.sleeper.sleepFor(this.timePerFrame);
            }
            this.countFrom--;
        } else {
            this.stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}
