package src.hazards;

import src.model.ITerrainObject;

/**
 * IHazard Interface
 * 
 * Represents a hazard on the icy terrain that penguins should avoid.
 * All hazards are terrain objects with positions on the grid.
 * 
 * There are 4 types of hazards:
 * - LightIceBlock: Can slide, stuns penguin on collision
 * - HeavyIceBlock: Cannot move, penguin loses lightest food
 * - SeaLion: Can slide, penguin bounces back
 * - HoleInIce: Anything falling in is removed, can be plugged
 * 
 * Some hazards (LightIceBlock, SeaLion) can slide on ice after being hit.
 */
public interface IHazard extends ITerrainObject {
    
    /**
     * Gets the type of this hazard.
     * 
     * @return the HazardType
     */
    HazardType getHazardType();
    
    /**
     * Checks if this hazard can slide on ice.
     * LightIceBlock and SeaLion can slide.
     * HeavyIceBlock and HoleInIce cannot.
     * 
     * @return true if the hazard can slide
     */
    boolean canSlide();
    
    /**
     * Gets the display name of this hazard type.
     * Used for game messages and output.
     * 
     * @return the display name (e.g., "LightIceBlock", "SeaLion")
     */
    String getHazardName();
}
