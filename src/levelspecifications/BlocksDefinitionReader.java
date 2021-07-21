// ID: 316012624

package levelspecifications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import interfaces.BlockCreator;

/**
* Reads A block specification file and constructs a list of blocks from it.
* @author Daniel Knafel
* ID: 316012624
*/
public class BlocksDefinitionReader {

    /**
     * Creates a Blocks Symbols Factory from text input tile.
     * @param reader a reader for the text file.
     * @return a Blocks Symbols Factory capable of producing blocks for a specific level.
     */
    public static BlocksFromSymbolsFactory fromReader(Reader reader) {
        // Default define map.
        Map<String, String> defaultMap = new HashMap<>();
        // Block define list.
        List<Map<String, String>> bdefList = new ArrayList<>();
        // Spacer define list.
        List<Map<String, String>> sdefList = new ArrayList<>();
        // Fill the maps with info.
        getInfoFromFile(new BufferedReader(reader), defaultMap, bdefList, sdefList);

        Map<String, BlockCreator> blockCreators  = new HashMap<>();

        for (Map<String, String> map : bdefList) {
            int width = parseInt("width", defaultMap, map);
            int height = parseInt("height", defaultMap, map);
            String stroke = parseString("stroke", defaultMap, map);
            String fill = parseString("fill", defaultMap, map);

            BlockCreator creator;
            // Image block.
            if (checkFillImage(fill)) {
                creator = new ImageBlock(width, height, ColorsParser.imageFromString(fill));
            // Color block.
            } else {
                creator = new ColorBlock(width, height, ColorsParser.colorFromString(fill));
            }

            // Add stroke if needed.
            if (stroke != null) {
                creator.setStroke(ColorsParser.colorFromString(stroke));
            }
            blockCreators.put(parseString("symbol", defaultMap, map), creator);
        }

        Map<String, Integer> spacerWidths = new HashMap<>();
        for (Map<String, String> map : sdefList) {
            spacerWidths.put(map.get("symbol"), Integer.parseInt(map.get("width")));
        }

        return new BlocksFromSymbolsFactory(spacerWidths, blockCreators);
    }

    /**
     * Extracts information from a config file. fills 3 collections from the information.
     * @param buffer a Buffered Reader for the text file.
     * @param defaultMap A map that will hold the default section.
     * @param bdefList a List of maps that will hold the block def information.
     * @param sdefList a List of maps that will hold the spacer def information.
     */
    private static void getInfoFromFile(BufferedReader buffer, Map<String, String> defaultMap,
                                            List<Map<String, String>> bdefList, List<Map<String, String>> sdefList) {
        String line = null;

        try {
            // read entire file.
            while ((line = buffer.readLine()) != null) {
                // Ignore the "#" and empty lines.
                if (line.isBlank() || line.charAt(0) == '#') {
                    continue;
                }

                // Split each line by spaces and remove the first part ("default, "bdef" or "sdef").
                String[] splitBySpace = line.split(" ");
                String[] arr = Arrays.copyOfRange(splitBySpace, 1, splitBySpace.length);

                // Check which part is currently being read and assing its information to the correct collection.
                if (splitBySpace[0].equals("default")) {
                    defaultMap.putAll(getInfoFromLine(arr));
                } else if (splitBySpace[0].equals("bdef")) {
                    bdefList.add(getInfoFromLine(arr));
                } else if (splitBySpace[0].equals("sdef")) {
                    sdefList.add(getInfoFromLine(arr));
                }
            }
        }  catch (IOException e) {
            System.out.println("Error reading file!\n");
            e.printStackTrace();
        // Close the file.
        } finally {
            try {
                buffer.close();
            } catch (IOException e) {
                System.out.println("Error closing file!\n");
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns the corresponding integer value for a given key. If an argument was not provided in the "bdef" section,
     * it will reside in the "default" section.
     * @param str the key word to parse.
     * @param defaultMap the map to take default values from.
     * @param map the map to take values from.
     * @return the corresponding integer value for a given key.
     */
    private static int parseInt(String str, Map<String, String> defaultMap, Map<String, String> map) {
        String parse = map.get(str);
        if (parse == null && defaultMap != null) {
            parse = defaultMap.get(str);
        }
        return Integer.parseInt(parse);
    }

    /**
     * Returns a string value corresponding to a string key. If an argument was not provided in the "bdef" section,
     * it will reside in the "default" section.
     * @param str the key word to parse.
     * @param defaultMap the map to take default values from.
     * @param map the map to take values from.
     * @return the corresponding string value for a given key
     */
    private static String parseString(String str, Map<String, String> defaultMap, Map<String, String> map) {
        String parse = map.get(str);
        if (parse == null && defaultMap != null) {
            parse = defaultMap.get(str);
        }
        return parse;
    }

    /**
     * Checks if the string contains the word "image" of not (other option is "color").
     * @param fillString the string representing the filling for the block.
     * @return true if its an image, false otherwise.
     */
    private static boolean checkFillImage(String fillString) {
        if (fillString.charAt(0) == 'i') {
            return true;
        }
        return false;
    }

    /**
     * Splits each part of a line by ":", and creates a map between each pair.
     * @param s a line split by " ".
     * @return a map between each pair of arguments in the line.
     */
    private static Map<String, String> getInfoFromLine(String[] s) {
        /* Example: the line is "symbol:G fill:color(lightGray)".
         *          will return [symbol:G], [fill:color(lightGray)].
         */
        Map<String, String> map = new HashMap<>();
        for (String str : s) {
            String[] splitByDots = str.split(":");
            map.put(splitByDots[0], splitByDots[1]);
        }
        return map;
    }
}
