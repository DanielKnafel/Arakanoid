// ID: 316012624

package levelspecifications;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
* Parses color from string to a Color object.
* @author Daniel Knafel
* ID: 316012624
*/
public class ColorsParser {
    /**
     * Parse color definition and return the specified color.
     * @param s string representing a color.  can be one of: RGB values or color name.
     * @return a Color object corresponding to the string input.
     */
    public static Color colorFromString(String s) {
        // If the string is a name of a color.
        Color c = stringColor(s);
        if (c != null) {
            return c;
        }
        return rgbColor(s);
    }

    /**
     * Converts a color's RGB values as a string to the correct Color object.
     * @param s s string representing a color name.
     * @return a Color object corresponding to the string input.
     */
    private static Color rgbColor(String s) {
        // Extract RGB values split by ",".
        s = s.substring("color(RGB(".length(), s.length() - 2);
        // If the color is an RGB value.
        String[] arr = s.split(",");
        int r = Integer.valueOf(arr[0]);
        int g = Integer.valueOf(arr[1]);
        int b = Integer.valueOf(arr[2]);
        return new Color(r, g, b);
    }

    /**
     * Converts a color name as a string to the correct Color object.
     * @param s s string representing a color name.
     * @return a Color object corresponding to the string input.
     */
    private static Color stringColor(String s) {
        try {
            // Extract color name.
            String str = s.substring("color(".length(), s.length() - 1);
            return (Color) Color.class.getField(str).get(null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Loads an image from a path on to an Image object.
     * @param s image path.
     * @return an Image object corresponding to the string input.
     */
    public static Image imageFromString(String s) {
        // Extract image path.
        String imagePath = "resources\\" + s.substring("image(".length(), s.length() - 1);
        // Load the image data into an java.awt.Image object.
        Image img = null;
        try {
            img = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("Failed loading image from file!");
            e.printStackTrace();
        }
        return img;
    }


}
