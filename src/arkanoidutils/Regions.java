// ID: 316012624

package arkanoidutils;

import java.time.temporal.ValueRange;

/**
 * A collection of Ranges.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class Regions {
    private long[] pointOfSeparation;
    private ValueRange[] regions;

    /**
     * Constructs a new Region from a given width of each region and the amount on regions.
     * @param amount the amount of regions required.
     * @param width the width of each region.
     */
    public Regions(int amount, double width) {
        this.pointOfSeparation = new long[amount + 1];
        this.regions = new ValueRange[amount];

        // Initialize the pointOfSeparation array.
        pointOfSeparation[0] = 0;
        for (int i = 1; i < pointOfSeparation.length; i++) {
            pointOfSeparation[i] = pointOfSeparation[i - 1] + (long) width;
        }

        // Initialize the regions.
        for (int i = 0; i < this.regions.length; i++) {
            this.regions[i] = ValueRange.of(pointOfSeparation[i], pointOfSeparation[i + 1]);
        }
    }

    /**
     * Returns the region of the range containing a given number, or -1 in it does not contain it.
     * @param num the number to be searched.
     * @return the region containing the number.
     */
    public int inWhichRange(double num) {
        for (int i = 0; i < this.regions.length; i++) {
            if (this.regions[i].isValidIntValue((long) num)) {
                return i + 1;
            }
        }
        // If none of the regions include the number, return -1;
        return -1;
    }

}