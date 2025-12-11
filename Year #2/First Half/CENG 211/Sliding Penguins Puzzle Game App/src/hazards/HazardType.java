package src.hazards;

/**
 * HazardType Enum
 * 
 * Represents the four types of hazards that can exist on the icy terrain.
 * Each hazard type has a display symbol and indicates whether it can slide.
 */
public enum HazardType {
    
    /**
     * Light Ice Block - can slide when hit, stuns penguin
     */
    LIGHT_ICE_BLOCK("LB", true),
    
    /**
     * Heavy Ice Block - cannot move, penguin loses lightest food
     */
    HEAVY_ICE_BLOCK("HB", false),
    
    /**
     * Sea Lion - slides when hit, penguin bounces back
     */
    SEA_LION("SL", true),
    
    /**
     * Hole In Ice - anything falling in is removed, can be plugged
     */
    HOLE_IN_ICE("HI", false);
    
    // The display symbol for the grid (2 characters)
    private final String displaySymbol;
    
    // Whether this hazard type can slide on ice
    private final boolean canSlide;
    
    /**
     * Constructor for HazardType enum.
     * 
     * @param displaySymbol the 2-character symbol for grid display
     * @param canSlide whether this hazard type can slide
     */
    HazardType(String displaySymbol, boolean canSlide) {
        this.displaySymbol = displaySymbol;
        this.canSlide = canSlide;
    }
    
    /**
     * Gets the display symbol for the grid.
     * 
     * @return the 2-character display symbol (LB, HB, SL, HI)
     */
    public String getDisplaySymbol() {
        return displaySymbol;
    }
    
    /**
     * Checks if this hazard type can slide on ice.
     * 
     * @return true if it can slide (LightIceBlock, SeaLion), false otherwise
     */
    public boolean canSlide() {
        return canSlide;
    }
    
    /**
     * Factory method to create a hazard instance based on type.
     * 
     * @param type the hazard type to create
     * @param row the initial row position
     * @param column the initial column position
     * @return the created IHazard instance
     */
    public static IHazard createHazard(HazardType type, int row, int column) {
        switch (type) {
            case LIGHT_ICE_BLOCK:
                return new LightIceBlock(row, column);
            case HEAVY_ICE_BLOCK:
                return new HeavyIceBlock(row, column);
            case SEA_LION:
                return new SeaLion(row, column);
            case HOLE_IN_ICE:
                return new HoleInIce(row, column);
            default:
                throw new IllegalArgumentException("Unknown hazard type: " + type);
        }
    }
}
