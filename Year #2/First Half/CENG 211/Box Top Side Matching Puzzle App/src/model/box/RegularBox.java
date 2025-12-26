package model.box;

import model.enums.Letter;
import model.tools.*;
import interfaces.IStampable;

/**
 * RegularBox is the most common type of box in the game.
 * 
 * Generation probability: 85%
 * Tool probability: 75% chance of containing a SpecialTool (15% each of 5 tools)
 *                   25% chance of being empty
 * 
 * Characteristics:
 * - Can be rolled in any direction (inherits from Box via IRollable)
 * - Can be opened to retrieve tools (inherits from Box via IOpenable)
 * - Can be stamped with new letters (implements IStampable)
 * - Letters on surfaces can be changed by SpecialTools
 */
public class RegularBox extends Box implements IStampable {
    
    /**
     * Default constructor that creates a RegularBox with random surfaces and tool content.
     * Has a 75% chance of containing a randomly selected SpecialTool.
     */
    public RegularBox() {
        super();
    }
    
    /**
     * Constructor that allows manual specification of all 6 surfaces.
     * Tool content is still randomly generated.
     * 
     * @param top the letter on the top surface
     * @param bottom the letter on the bottom surface
     * @param front the letter on the front surface
     * @param back the letter on the back surface
     * @param left the letter on the left surface
     * @param right the letter on the right surface
     */
    public RegularBox(Letter top, Letter bottom, Letter front, Letter back, Letter left, Letter right) {
        super(top, bottom, front, back, left, right);
    }
    
    /**
     * Initializes the tool content of the RegularBox.
     * 75% chance of containing a tool:
     * - 15% chance for each of the 5 SpecialTool types
     * 25% chance of being empty
     */
    @Override
    protected void initializeTool() {
        double probability = random.nextDouble();
        
        if (probability < 0.75) {
            // Has a tool - select one of 5 tools (each 15% = 0.15 / 0.75 = 0.2 normalized)
            double toolProbability = random.nextDouble();
            
            if (toolProbability < 0.2) {
                containedTool = new PlusShapeStamp();
            } else if (toolProbability < 0.4) {
                containedTool = new MassRowStamp();
            } else if (toolProbability < 0.6) {
                containedTool = new MassColumnStamp();
            } else if (toolProbability < 0.8) {
                containedTool = new BoxFlipper();
            } else {
                containedTool = new BoxFixer();
            }
        } else {
            // 25% chance of being empty
            containedTool = null;
        }
    }
    
    /**
     * Stamps the top side of the RegularBox with a new letter (IStampable implementation).
     * The letter is actually changed on the top surface.
     * 
     * @param letter the letter to stamp on the top side
     */
    @Override
    public void stampTopSide(Letter letter) {
        this.topSide = letter;
    }
    
    /**
     * Checks if this box can be stamped with new letters (IStampable implementation).
     * RegularBox always returns true.
     * 
     * @return true (RegularBox can be stamped)
     */
    @Override
    public boolean canBeStamped() {
        return true;
    }
    
    /**
     * Returns the type identifier for RegularBox.
     * 
     * @return 'R' for RegularBox
     */
    @Override
    public char getTypeIdentifier() {
        return 'R';
    }
    
    /**
     * Returns a string representation of the RegularBox for grid display.
     * Format: | R-LETTER-STATUS |
     * - STATUS is 'M' (Mystery) if not opened, 'O' if opened/empty
     * 
     * @return formatted string for grid display
     */
    @Override
    public String getGridDisplay() {
        char status = isOpened() || isEmpty() ? 'O' : 'M';
        return String.format(" R-%c-%c ", topSide.toChar(), status);
    }
}

