package model.box;

import model.enums.Letter;
import model.tools.*;
import interfaces.IStampable;

/**
 * UnchangingBox is a special box whose surfaces cannot be changed.
 * 
 * Generation probability: 10%
 * Tool probability: 100% chance of containing a SpecialTool (20% each of 5 tools)
 * 
 * Characteristics:
 * - Can be rolled in any direction (inherits from Box via IRollable)
 * - Can be opened to retrieve tools (inherits from Box via IOpenable)
 * - Implements IStampable but stamping has no effect (top side letter remains the same)
 * - Letters on surfaces CANNOT be changed by SpecialTools
 * - Guaranteed to contain a SpecialTool
 */
public class UnchangingBox extends Box implements IStampable {
    
    /**
     * Default constructor that creates an UnchangingBox with random surfaces.
     * Guaranteed to contain a randomly selected SpecialTool (100% chance).
     */
    public UnchangingBox() {
        super();
    }
    
    /**
     * Constructor that allows manual specification of all 6 surfaces.
     * Tool content is still randomly generated (guaranteed to have a tool).
     * 
     * @param top the letter on the top surface
     * @param bottom the letter on the bottom surface
     * @param front the letter on the front surface
     * @param back the letter on the back surface
     * @param left the letter on the left surface
     * @param right the letter on the right surface
     */
    public UnchangingBox(Letter top, Letter bottom, Letter front, Letter back, Letter left, Letter right) {
        super(top, bottom, front, back, left, right);
    }
    
    /**
     * Initializes the tool content of the UnchangingBox.
     * 100% chance of containing a tool:
     * - 20% chance for each of the 5 SpecialTool types
     */
    @Override
    protected void initializeTool() {
        double probability = random.nextDouble();
        
        // Always has a tool - select one of 5 tools (each 20%)
        if (probability < 0.2) {
            containedTool = new PlusShapeStamp();
        } else if (probability < 0.4) {
            containedTool = new MassRowStamp();
        } else if (probability < 0.6) {
            containedTool = new MassColumnStamp();
        } else if (probability < 0.8) {
            containedTool = new BoxFlipper();
        } else {
            containedTool = new BoxFixer();
        }
    }
    
    /**
     * Attempts to stamp the top side of the UnchangingBox with a new letter (IStampable implementation).
     * However, the letter does NOT actually change - UnchangingBox surfaces are immutable.
     * This method accepts the stamping operation but has no effect.
     * 
     * @param letter the letter to stamp (ignored, no effect)
     */
    @Override
    public void stampTopSide(Letter letter) {
        // UnchangingBox: stamping operation is accepted but has no effect
        // The top side letter remains unchanged
        // This is intentional behavior as specified in the homework
    }
    
    /**
     * Checks if this box can be stamped with new letters (IStampable implementation).
     * UnchangingBox returns true (it can be "stamped"), but the operation has no effect.
     * 
     * @return true (UnchangingBox can be stamped, but it doesn't change)
     */
    @Override
    public boolean canBeStamped() {
        return true;
    }
    
    /**
     * Returns the type identifier for UnchangingBox.
     * 
     * @return 'U' for UnchangingBox
     */
    @Override
    public char getTypeIdentifier() {
        return 'U';
    }
    
    /**
     * Returns a string representation of the UnchangingBox for grid display.
     * Format: | U-LETTER-STATUS |
     * - STATUS is 'M' (Mystery) if not opened, 'O' if opened
     * 
     * @return formatted string for grid display
     */
    @Override
    public String getGridDisplay() {
        char status = isOpened() ? 'O' : 'M';
        return String.format(" U-%c-%c ", topSide.toChar(), status);
    }
}

