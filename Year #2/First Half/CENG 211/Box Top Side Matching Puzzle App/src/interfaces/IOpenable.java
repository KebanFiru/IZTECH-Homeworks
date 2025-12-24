package interfaces;

import model.tools.SpecialTool;
import core.exceptions.EmptyBoxException;

/**
 * Interface for boxes that can be opened to retrieve SpecialTools.
 * All box types (RegularBox, UnchangingBox, FixedBox) can be opened,
 * but only RegularBox and UnchangingBox may contain SpecialTools.
 * FixedBox is always empty.
 * 
 * A box can only be opened if it was rolled during the current turn.
 * Once opened, the box is marked as empty and cannot be opened again
 * until it potentially gets a new tool (which doesn't happen in this game).
 */
public interface IOpenable {
    
    /**
     * Opens the box and retrieves the SpecialTool inside.
     * After opening, the box is marked as empty (the tool is removed).
     * 
     * @return the SpecialTool that was inside the box
     * @throws EmptyBoxException if the box is empty
     */
    SpecialTool open() throws EmptyBoxException;
    
    /**
     * Checks if the box is empty (no SpecialTool inside).
     * 
     * @return true if the box is empty, false otherwise
     */
    boolean isEmpty();
    
    /**
     * Checks if the box has been opened during the current game.
     * Used for display purposes (showing 'O' vs 'M' on the grid).
     * 
     * @return true if the box has been opened, false otherwise
     */
    boolean isOpened();
    
    /**
     * Marks the box as having been rolled during the current turn.
     * A box can only be opened if it was rolled in the same turn.
     * 
     * @param rolled true to mark as rolled, false to reset for next turn
     */
    void setRolledThisTurn(boolean rolled);
    
    /**
     * Checks if the box was rolled during the current turn.
     * 
     * @return true if the box was rolled this turn, false otherwise
     */
    boolean wasRolledThisTurn();
}