// ID: 316012624

package levelspecifications;

import java.util.Map;
import arkanoid.Block;
import interfaces.BlockCreator;

/**
* A factory for producing blocks and spacers according to a given symbol.
* @author Daniel Knafel
* ID: 316012624
*/
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Constructor.
     * @param spacerWidths a map between spacer symbols and their width.
     * @param blockCreators a map between block symbols and their Block Creators.
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    /**
     * Returns true if 's' is a valid space symbol.
     * @param s symbol to check.
     * @return true if 's' is a valid space symbol, false otherwise.
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }

    /**
     * Returns true if 's' is a valid block symbol.
     * @param s symbol to check.
     * @return true if 's' is a valid block symbol, false otherwise.
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }

    /**
     * Return a block according to the definitions associated with symbol "s".
     * The block will be located at position (x, y).
     * @param s symbol for the block.
     * @param x x position of the block.
     * @param y y position of the block.
     * @return the correct block type to "s" in the specified location.
     */
    public Block getBlock(String s, int x, int y) {
        return this.blockCreators.get(s).create(x, y);
     }

    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     * @param s spacer-symbol.
     * @return width in pixels associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
     }
}

