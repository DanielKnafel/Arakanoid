// ID:316012624

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import arkanoidutils.GameFlow;
import interfaces.LevelInformation;
import levelspecifications.LevelSpecificationReader;

/**
 * A main class for the Arkanoid game.
 * @author Daniel Knafel
 * ID: 316012624
 */
public class Ass7Game {

    /**
     * Main function.
     * @param args command-line argument.
     */
    public static void main(String[] args) {
        LevelSpecificationReader reader = new LevelSpecificationReader();
        List<LevelInformation> levels = new ArrayList<>();

        InputStream is = null;

        if (args.length > 0) {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(args[0]);
        }

        if (is == null) {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream("level.txt");
        }

        levels = reader.fromReader(new InputStreamReader(is));

        GameFlow gf = new GameFlow();
        gf.runMenu(levels);
    }
}