// ID: 316012624

package levelspecifications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import arkanoid.Block;
import arkanoid.Velocity;
import geometry.Point;
import interfaces.LevelInformation;
import levels.Level;

/**
* Reads A level specification file and constructs a list of level information object from it.
* @author Daniel Knafel
* ID: 316012624
*/
public class LevelSpecificationReader {
    private List<LevelInformation> levels;

    /**
     * Constructor.
     */
    public LevelSpecificationReader() {
        this.levels = new ArrayList<>();
    }

    /**
     * Decode all levels from an input text file.
     * @param reader an input file containing coded levels.
     * @return a list of LevelInfirmation objects decoded from the file.
     */
    public List<LevelInformation> fromReader(Reader reader) {
        // Make a buffered reader to read lines from the file.
        BufferedReader buffer = new BufferedReader(reader);
        try {
            String line = null;
            line = buffer.readLine();
            //System.out.println(line);
            while (line != null) {
                this.levels.add(getLinesFromFile(buffer));
                line = buffer.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error reading levels file!\n");
            e.printStackTrace();
        } finally {
            if (buffer != null) {
                 try {
                     buffer.close();
                 } catch (IOException e) {
                     System.out.println("Failed closing the file!");
                 }
            }
        }
        return this.levels;
    }

    /**
     * Interpret a single level from the file.
     * @param buffer input text file.
     * @return a LevelInformation object from the file.
     */
    private LevelInformation getLinesFromFile(BufferedReader buffer) {
        Map<String, String> map = new HashMap<>();
        String line = null;

        try {
            // Ignore the "START_LEVEL" line.
            line = buffer.readLine();

            while (!line.equals("START_BLOCKS")) {
                // Split the string by ":" and store the different parts.
                String[] splitByDots = line.split(":");
                map.put(splitByDots[0], splitByDots[1]);
                line = buffer.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error reading file!\n");
            e.printStackTrace();
        }
        return createLevelFromText(map, buffer);
    }

    /**
     * After decoding each line in string, extract the data to its final form.
     * @param map a string to string mapping of the game parameters.
     * @param buffer the input file reader.
     * @return the completed LevelInformation from the file.
     */
    private LevelInformation createLevelFromText(Map<String, String> map, BufferedReader buffer) {
        Level level = null;

        try {
            // Convert all fields from the map to their correct types.
            String velocitiesString = map.get("ball_velocities");
            int paddleSpeed = Integer.parseInt(map.get("paddle_speed"));
            int paddleWidth = Integer.parseInt(map.get("paddle_width"));
            String levelName = map.get("level_name");
            int blocksStartX = Integer.parseInt(map.get("blocks_start_x"));
            int blocksStartY = Integer.parseInt(map.get("blocks_start_y"));
            int rowHeight = Integer.parseInt(map.get("row_height"));
            int numBlocks = Integer.parseInt(map.get("num_blocks"));
            String backgroundString = map.get("background");

            // Create a list of ball velocities from the tuples.
            List<Velocity> initialVelocities = getVelocitiesFromFile(velocitiesString);

            Point blocksStart = new Point(blocksStartX, blocksStartY);

            // check if the background is a Color of an Image, to decide which constructor to use.
            if (checkBackgroundImage(backgroundString)) {
                level = new Level(paddleSpeed, paddleWidth, levelName, ColorsParser.imageFromString(backgroundString),
                                    numBlocks, rowHeight, blocksStart);
            } else {
                level = new Level(paddleSpeed, paddleWidth, levelName, ColorsParser.colorFromString(backgroundString),
                                    numBlocks, rowHeight, blocksStart);
            }
            // Add the ball velocities to the level.
            level.addVelocities(initialVelocities);

            // The path to the block definitions file.
            String blockDefinitions = map.get("block_definitions");
            // Create a list of blocks from the file and add it to the level.
            blocksLayoutDecoder(buffer, blockDefinitions, level);
        } catch (Exception e) {
            System.out.println("missing paramaters if config file\n");
            e.printStackTrace();
            System.exit(0);
        }
        return level;
    }

    /**
     * Decode and create all the blocks for the level.
     * @param buffer input file.
     * @param blocksDefPath the path for the block Definition file.
     * @param level the level information object to add the blocks to.
     */
    private void blocksLayoutDecoder(BufferedReader buffer, String blocksDefPath, Level level) {
        BlocksFromSymbolsFactory factory = null;
        // Decode the block Definition file and create a block factory for it.
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(blocksDefPath);
        factory = BlocksDefinitionReader.fromReader(new InputStreamReader(is));

        String line = null;
        int currentY = level.blocksStartY();
        try {
            line = buffer.readLine();
            // Read the text file.
            while (!line.equals("END_BLOCKS")) {
                // Starting position of the first block in each row.
                int currentX = level.blocksStartX();
                // Height of a block.
                int height = 0;
                // Iterate over the line. each char can be either a block or a spacer.
                for (int i = 0; i < line.length(); i++) {
                    String s = String.valueOf(line.charAt(i));
                    // if its a block
                    if (factory.isBlockSymbol(s)) {
                        // Create a block from the factory.
                        Block b = factory.getBlock(s, currentX, currentY);
                        level.addBlock(b);
                        // Advance to next column.
                        currentX += b.getCollisionRectangle().getWidth();
                    // if its a spacer
                    } else if (factory.isSpaceSymbol(s)) {
                        // insert spacer.
                        currentX += factory.getSpaceWidth(s);
                    }
                }
                // Next row.
                currentY += height + level.rowHeight();
                line = buffer.readLine();
            }
            // skip lines for next level (if exists).
            buffer.readLine();
            buffer.readLine();
        } catch (IOException e) {
            System.out.println("Error reading file!\n");
            e.printStackTrace();
        }
    }

    /**
     * Extract ball velocities from the string representing them.
     * @param velocitiesString the string representation of the ball velocities.
     * @return a list of the velocities.
     */
    private List<Velocity> getVelocitiesFromFile(String velocitiesString) {
        List<Velocity> initialVelocities = new ArrayList<>();
        // Extract the tuples of (angle,speed) as string
        String[] splitBySpace = velocitiesString.split(" ");
        // For each tuple of speeds, extract the angle and speed parts and add the velocity to the list.
        for (String velocity : splitBySpace) {
            String[] splitByComa = velocity.split(",");
            int angle = Integer.valueOf(splitByComa[0]);
            int speed = Integer.parseInt(splitByComa[1]);
            initialVelocities.add(Velocity.fromAngleAndSpeed(angle, speed));
        }
        return initialVelocities;
    }

    /**
     * Checks if the string contains the word "image" of not (other option is "color").
     * @param backgroundString the string representing the background for the level.
     * @return true if its an image, false otherwise.
     */
    private boolean checkBackgroundImage(String backgroundString) {
        // if the first letter is an i, its an image.
        if (backgroundString.charAt(0) == 'i') {
            return true;
        }
        // its its not an i, its a color.
        return false;
    }
}