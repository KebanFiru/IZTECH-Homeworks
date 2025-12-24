package interfaces;

import model.enums.Direction;
import core.exceptions.UnmovableFixedBoxException;

/**
 * Interface for boxes that can be rolled in different directions.
 * All Box types implement this interface through the Box abstract class.
 * - RegularBox and UnchangingBox: Can roll normally
 * - FixedBox: Implements the interface but overrides to throw UnmovableFixedBoxException
 * 
 * When a box rolls, its orientation changes:
 * - Rolling RIGHT: right side becomes top, top becomes left, left becomes bottom, bottom becomes right
 * - Rolling LEFT: left side becomes top, top becomes right, right becomes bottom, bottom becomes left
 * - Rolling UP: front side becomes top, top becomes back, back becomes bottom, bottom becomes front
 * - Rolling DOWN: back side becomes top, top becomes front, front becomes bottom, bottom becomes back
 */
public interface IRollable {
    
    /**
     * Rolls the box in the specified direction.
     * This changes which letter appears on the top side of the box.
     * 
     * @param direction the direction to roll the box
     * @throws UnmovableFixedBoxException if attempting to roll a FixedBox
     */
    void roll(Direction direction) throws UnmovableFixedBoxException;
    
    /**
     * Checks if this box can be rolled.
     * Returns false for FixedBox, true for RegularBox and UnchangingBox.
     * 
     * @return true if the box can be rolled, false otherwise
     */
    boolean canRoll();
}
