package interfaces;

import model.enums.Letter;

/**
 * Interface for boxes whose surfaces can be stamped with new letters.
 * Used by SpecialTools to change the letters on box surfaces.
 * 
 * RegularBox: Can be stamped, and the letter changes normally.
 * UnchangingBox: Can be "stamped", but the letter doesn't actually change.
 * FixedBox: Cannot be stamped (does not implement this interface).
 * 
 * This interface allows SpecialTools to work with boxes polymorphically.
 */
public interface IStampable {
    
    /**
     * Stamps the top side of the box with a new letter.
     * For RegularBox, this changes the top side letter.
     * For UnchangingBox, this operation has no visible effect.
     * 
     * @param letter the letter to stamp on the top side
     */
    void stampTopSide(Letter letter);
    
    /**
     * Checks if this box can be stamped with new letters.
     * Returns true for RegularBox and UnchangingBox.
     * Note: UnchangingBox returns true but doesn't actually change.
     * 
     * @return true if the box can be stamped, false otherwise
     */
    boolean canBeStamped();
}
