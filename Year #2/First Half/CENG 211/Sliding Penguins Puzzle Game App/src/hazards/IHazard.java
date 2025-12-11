package hazards;

import model.ITerrainObject;

/**
 * Interface for hazard objects on the terrain.
 * Extends ITerrainObject to include hazard-specific behavior.
 */
public interface IHazard extends ITerrainObject {
    /**
     * Checks if this hazard can slide/be pushed.
     * 
     * @return true if the hazard can slide, false otherwise
     */
    boolean canSlide();
    
    /**
     * Gets the type name of this hazard.
     * 
     * @return String representation of the hazard type
     */
    String getHazardType();
}