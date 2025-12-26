package model.box;

import model.enums.Letter;
import model.enums.Direction;
import core.exceptions.UnmovableFixedBoxException;

/**
 * FixedBox is a special box that cannot be rolled or stamped.
 * 
 * Generation probability: 5%
 * Tool probability: 0% (always empty)
 * 
 * Characteristics:
 * - CANNOT be rolled - throws UnmovableFixedBoxException when attempting to roll
 * - Can be opened but is always empty (inherits from Box via IOpenable)
 * - Does NOT implement IStampable - cannot be stamped
 * - Blocks the domino-effect from passing through it during rolling operations
 * - Top side letter never changes throughout the game
 * - Marked as empty from the start (containedTool is always null)
 */
public class FixedBox extends Box {
    
    /**
     * Default constructor that creates a FixedBox with random surfaces.
     * Always empty (no tool inside).
     */
    public FixedBox() {
        super();
    }
    
    /**
     * Constructor that allows manual specification of all 6 surfaces.
     * Always empty (no tool inside).
     * 
     * @param top the letter on the top surface
     * @param bottom the letter on the bottom surface
     * @param front the letter on the front surface
     * @param back the letter on the back surface
     * @param left the letter on the left surface
     * @param right the letter on the right surface
     */
    public FixedBox(Letter top, Letter bottom, Letter front, Letter back, Letter left, Letter right) {
        super(top, bottom, front, back, left, right);
    }
    
    /**
     * Initializes the tool content of the FixedBox.
     * FixedBox is always empty - never contains a SpecialTool.
     */
    @Override
    protected void initializeTool() {
        containedTool = null;
    }
    
    /**
     * Overrides the roll method to prevent rolling (IRollable implementation).
     * FixedBox cannot be rolled - always throws UnmovableFixedBoxException.
     * 
     * @param direction the direction to roll (ignored)
     * @throws UnmovableFixedBoxException always thrown when attempting to roll a FixedBox
     */
    @Override
    public void roll(Direction direction) throws UnmovableFixedBoxException {
        throw new UnmovableFixedBoxException("FixedBox cannot be rolled!");
    }
    
    /**
     * Checks if this box can be rolled (IRollable implementation).
     * FixedBox always returns false.
     * 
     * @return false (FixedBox cannot be rolled)
     */
    @Override
    public boolean canRoll() {
        return false;
    }
    
    /**
     * Overrides flipUpsideDown to prevent flipping.
     * Attempting to flip a FixedBox should throw an exception.
     * This method is called by BoxFlipper tool.
     * 
     * @throws UnmovableFixedBoxException when attempting to flip a FixedBox
     */
    @Override
    public void flipUpsideDown() throws UnmovableFixedBoxException {
        throw new UnmovableFixedBoxException("FixedBox cannot be flipped!");
    }
    
    /**
     * Returns the type identifier for FixedBox.
     * 
     * @return 'X' for FixedBox
     */
    @Override
    public char getTypeIdentifier() {
        return 'X';
    }
    
    /**
     * Returns a string representation of the FixedBox for grid display.
     * Format: | X-LETTER-O |
     * - STATUS is always 'O' (empty) since FixedBox never contains tools
     * 
     * @return formatted string for grid display
     */
    @Override
    public String getGridDisplay() {
        return String.format(" X-%c-O ", topSide.toChar());
    }

}

