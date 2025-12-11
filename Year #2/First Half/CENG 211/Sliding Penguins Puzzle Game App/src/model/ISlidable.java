package model;

import terrain.Direction;
import terrain.IcyTerrain;

/**
 * Interface for objects that can slide across the icy terrain.
 * Implemented by penguins and slidable hazards (ice blocks).
 */
public interface ISlidable {

    /**
     * Initiates a slide in the specified direction.
     * 
     * @param direction The direction to slide
     * @param terrain The terrain on which to slide
     */
    void slide(Direction direction, IcyTerrain terrain);

    /**
     * Checks if the object is currently sliding.
     * 
     * @return true if sliding, false otherwise
     */
    boolean isSliding();

    /**
     * Sets the sliding state of the object.
     * 
     * @param sliding The new sliding state
     */
    void setSliding(boolean sliding);
}