package hazards;

/**
 * Enum representing different types of hazards on the terrain.
 */
public enum HazardType {
    /** Light ice block - can be pushed/slid */
    LIGHT_ICE_BLOCK,
    /** Heavy ice block - harder to move */
    HEAVY_ICE_BLOCK,
    /** Sea lion - predator hazard */
    SEALION,
    /** Hole in the ice - can be plugged */
    HOLE_IN_ICE
}