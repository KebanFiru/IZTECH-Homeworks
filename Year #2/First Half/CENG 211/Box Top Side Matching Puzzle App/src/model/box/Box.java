package model.box;

import model.enums.Letter;
import model.enums.Direction;
import model.tools.SpecialTool;
import interfaces.IRollable;
import interfaces.IOpenable;
import core.exceptions.UnmovableFixedBoxException;
import core.exceptions.EmptyBoxException;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract base class representing a cubic box with 6 surfaces.
 * Each surface has a letter stamped on it (A-H).
 * Implements IRollable interface - FixedBox will override to prevent rolling.
 * Implements IOpenable interface - all boxes can be opened.
 * 
 * Box orientation:
 * - Top: The currently visible top surface
 * - Bottom: The opposite of top
 * - Front: The surface facing forward
 * - Back: The opposite of front
 * - Left: The left side surface
 * - Right: The right side surface
 * 
 * Constraint: No box can have the same letter on more than 2 surfaces.
 * Example valid: C, C, E, E, G, G
 * Example invalid: B, D, D, D, F, G (D appears 3 times)
 */
public abstract class Box implements IRollable, IOpenable {
    
    // Six surfaces of the cubic box
    protected Letter topSide;
    protected Letter bottomSide;
    protected Letter frontSide;
    protected Letter backSide;
    protected Letter leftSide;
    protected Letter rightSide;
    
    // Opening behavior fields (IOpenable)
    protected SpecialTool containedTool;
    protected boolean opened;
    protected boolean rolledThisTurn;
    
    // Random generator for box initialization
    protected static final Random random = new Random();
    
    /**
     * Constructor that initializes a box with random letters on all 6 surfaces.
     * Ensures that no letter appears more than twice on the surfaces.
     * Also initializes the tool content for the box.
     */
    public Box() {
        generateValidSurfaces();
        this.opened = false;
        this.rolledThisTurn = false;
        initializeTool();
    }
    
    /**
     * Constructor that allows manual specification of all 6 surfaces.
     * Used for testing or specific box configurations.
     * 
     * @param top the letter on the top surface
     * @param bottom the letter on the bottom surface
     * @param front the letter on the front surface
     * @param back the letter on the back surface
     * @param left the letter on the left surface
     * @param right the letter on the right surface
     * @throws IllegalArgumentException if any letter appears more than twice
     */
    public Box(Letter top, Letter bottom, Letter front, Letter back, Letter left, Letter right) {
        this.topSide = top;
        this.bottomSide = bottom;
        this.frontSide = front;
        this.backSide = back;
        this.leftSide = left;
        this.rightSide = right;
        this.opened = false;
        this.rolledThisTurn = false;
        
        if (!isValidLetterDistribution()) {
            throw new IllegalArgumentException("Invalid box: A letter appears more than twice on the surfaces.");
        }
        
        initializeTool();
    }
    
    /**
     * Generates random letters for all 6 surfaces.
     * Ensures no letter appears more than twice.
     */
    private void generateValidSurfaces() {
        do {
            topSide = Letter.getRandomLetter();
            bottomSide = Letter.getRandomLetter();
            frontSide = Letter.getRandomLetter();
            backSide = Letter.getRandomLetter();
            leftSide = Letter.getRandomLetter();
            rightSide = Letter.getRandomLetter();
        } while (!isValidLetterDistribution());
    }
    
    /**
     * Validates that no letter appears more than twice on the box surfaces.
     * 
     * Implementation uses Map interface with HashMap for efficient letter counting:
     * 
     * Why Map<Letter, Integer> interface?
     * - Programming to an interface (not implementation) is a best practice in OOP
     * - Provides flexibility to change the underlying implementation if needed
     * - Makes the code more maintainable and testable
     * - Map is the general contract for key-value pair storage
     * 
     * Why HashMap implementation?
     * - Provides O(1) average time complexity for put() and get() operations
     * - Most efficient for counting occurrences of items
     * - Constant time lookup makes this validation fast even with multiple checks
     * - No ordering required, so HashMap is more efficient than TreeMap
     * 
     * How it works:
     * 1. Uses Letter enum as key (each unique letter A-H)
     * 2. Uses Integer as value (count of occurrences on the 6 surfaces)
     * 3. getOrDefault(key, 0) safely increments count without null checks
     * 4. Iterates through all counts to ensure none exceed 2
     * 
     * Collections Framework usage:
     * This demonstrates proper use of the Collections framework as required
     * by the homework - using Map for data aggregation and validation.
     * 
     * @return true if the distribution is valid (no letter appears more than twice), false otherwise
     */
    private boolean isValidLetterDistribution() {
        // Map interface for flexibility, HashMap for O(1) performance
        Map<Letter, Integer> letterCount = new HashMap<>();
        
        // Count occurrences of each letter on all 6 surfaces
        letterCount.put(topSide, letterCount.getOrDefault(topSide, 0) + 1);
        letterCount.put(bottomSide, letterCount.getOrDefault(bottomSide, 0) + 1);
        letterCount.put(frontSide, letterCount.getOrDefault(frontSide, 0) + 1);
        letterCount.put(backSide, letterCount.getOrDefault(backSide, 0) + 1);
        letterCount.put(leftSide, letterCount.getOrDefault(leftSide, 0) + 1);
        letterCount.put(rightSide, letterCount.getOrDefault(rightSide, 0) + 1);
        
        // Check if any letter appears more than twice
        for (Integer count : letterCount.values()) {
            if (count > 2) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Gets the letter currently on the top surface of the box.
     * 
     * @return the letter on the top side
     */
    public Letter getTopSide() {
        return topSide;
    }
    
    /**
     * Gets the letter on the bottom surface of the box.
     * 
     * @return the letter on the bottom side
     */
    public Letter getBottomSide() {
        return bottomSide;
    }
    
    /**
     * Gets the letter on the front surface of the box.
     * 
     * @return the letter on the front side
     */
    public Letter getFrontSide() {
        return frontSide;
    }
    
    /**
     * Gets the letter on the back surface of the box.
     * 
     * @return the letter on the back side
     */
    public Letter getBackSide() {
        return backSide;
    }
    
    /**
     * Gets the letter on the left surface of the box.
     * 
     * @return the letter on the left side
     */
    public Letter getLeftSide() {
        return leftSide;
    }
    
    /**
     * Gets the letter on the right surface of the box.
     * 
     * @return the letter on the right side
     */
    public Letter getRightSide() {
        return rightSide;
    }
    
    /**
     * Sets the letter on the top surface.
     * Used by stamping operations.
     * 
     * @param letter the new letter for the top side
     */
    protected void setTopSide(Letter letter) {
        this.topSide = letter;
    }
    
    /**
     
    
    /**
     * Opens the box and retrieves the SpecialTool inside (IOpenable interface implementation).
     * After opening, the box is marked as empty and opened.
     * The box must have been rolled during the current turn to be opened.
     * 
     * @return the SpecialTool that was inside the box
     * @throws EmptyBoxException if the box is empty
     */
    @Override
    public SpecialTool open() throws EmptyBoxException {
        if (isEmpty()) {
            throw new EmptyBoxException("The box is empty!");
        }
        SpecialTool tool = containedTool;
        containedTool = null;
        opened = true;
        return tool;
    }
    
    /**
     * Checks if the box is empty (IOpenable interface implementation).
     * 
     * @return true if the box contains no SpecialTool, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return containedTool == null;
    }
    
    /**
     * Checks if the box has been opened (IOpenable interface implementation).
     * Used for display purposes (showing 'O' vs 'M' on the grid).
     * 
     * @return true if the box has been opened, false otherwise
     */
    @Override
    public boolean isOpened() {
        return opened;
    }
    
    /**
     * Marks the box as having been rolled during the current turn (IOpenable interface implementation).
     * A box can only be opened if it was rolled in the same turn.
     * 
     * @param rolled true to mark as rolled, false to reset for next turn
     */
    @Override
    public void setRolledThisTurn(boolean rolled) {
        this.rolledThisTurn = rolled;
    }
    
    /**
     * Checks if the box was rolled during the current turn (IOpenable interface implementation).
     * 
     * @return true if the box was rolled this turn, false otherwise
     */
    @Override
    public boolean wasRolledThisTurn() {
        return rolledThisTurn;
    }
    
    /**
     * Initializes the tool content of the box based on box type and probabilities.
     * This is an abstract method that must be implemented by subclasses.
     * - RegularBox: 75% chance of containing a tool (15% each of 5 tools)
     * - UnchangingBox: 100% chance of containing a tool (20% each of 5 tools)
     * - FixedBox: 0% chance of containing a tool (always empty)
     */
    protected abstract void initializeTool();
    
    /**
    /* Flips the box upside down.
     * The top becomes the bottom, and the bottom becomes the top.
     * Used by BoxFlipper tool.
     */
    public void flipUpsideDown() {
        Letter temp = topSide;
        topSide = bottomSide;
        bottomSide = temp;
    }
    
    /**
     * Returns a string representation of all surfaces in a cube diagram format.
     * The format matches the homework specification:
     * <pre>
     *     -----
     *     | C |
     * -------------
     * | H | B | E |
     * -------------
     *     | F |
     *     -----
     *     | H |
     *     -----
     * </pre>
     * Where the middle position corresponds to the top side.
     * 
     * @return a formatted string showing all 6 surfaces
     */
    public String displayAllSurfaces() {
        StringBuilder sb = new StringBuilder();
        sb.append("    -----     \n");
        sb.append("    | ").append(backSide.toChar()).append(" |\n");
        sb.append("-------------\n");
        sb.append("| ").append(leftSide.toChar()).append(" | ").append(topSide.toChar()).append(" | ").append(rightSide.toChar()).append(" |\n");
        sb.append("-------------\n");
        sb.append("    | ").append(frontSide.toChar()).append(" |\n");
        sb.append("    -----\n");
        sb.append("    | ").append(bottomSide.toChar()).append(" |\n");
        sb.append("    -----");
        return sb.toString();
    }
    
    /**
     * Rolls the box in the specified direction (IRollable interface implementation).
     * Changes which letter appears on the top side by rotating the box.
     * FixedBox will override this method to throw an exception.
     * 
     * @param direction the direction to roll the box
     * @throws UnmovableFixedBoxException if attempting to roll a FixedBox
     */
    @Override
    public void roll(Direction direction) throws UnmovableFixedBoxException {
        performRoll(direction);
    }
    
    /**
     * Checks if this box can be rolled (IRollable interface implementation).
     * Returns true by default. FixedBox overrides this to return false.
     * 
     * @return true if the box can be rolled, false otherwise
     */
    @Override
    public boolean canRoll() {
        return true;
    }
    
    /**
     * Performs the rolling operation by rotating the box surfaces.
     * This is a protected helper method that handles the actual surface rotation.
     * 
     * @param direction the direction to roll
     */
    protected void performRoll(Direction direction) {
        Letter temp;
        switch (direction) {
            case RIGHT:
                // Right side becomes top, top becomes left, left becomes bottom, bottom becomes right
                temp = topSide;
                topSide = leftSide;
                leftSide = bottomSide;
                bottomSide = rightSide;
                rightSide = temp;
                break;
            case LEFT:
                // Left side becomes top, top becomes right, right becomes bottom, bottom becomes left
                temp = topSide;
                topSide = rightSide;
                rightSide = bottomSide;
                bottomSide = leftSide;
                leftSide = temp;
                break;
            case UP:
                // Back side becomes top, top becomes front, front becomes bottom, bottom becomes back
                temp = topSide;
                topSide = frontSide;
                frontSide = bottomSide;
                bottomSide = backSide;
                backSide = temp;
                break;
            case DOWN:
                // Front side becomes top, top becomes back, back becomes bottom, bottom becomes front
                temp = topSide;
                topSide = backSide;
                backSide = bottomSide;
                bottomSide = frontSide;
                frontSide = temp;
                break;
        }
    }
    
    /**
     * Returns the type identifier for this box.
     * Used for display in the grid.
     * 
     * @return 'R' for RegularBox, 'U' for UnchangingBox, 'X' for FixedBox
     */
    public abstract char getTypeIdentifier();
    
    /**
     * Returns a string representation of the box for display in the grid.
     * Format: | TYPE-LETTER-STATUS |
     * Example: | R-E-M | or | X-D-O |
     * 
     * @return formatted string for grid display
     */
    public abstract String getGridDisplay();
}
