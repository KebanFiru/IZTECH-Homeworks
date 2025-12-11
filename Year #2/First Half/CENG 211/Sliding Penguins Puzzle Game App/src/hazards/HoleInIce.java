package hazards;

/**
 * Hole in the ice hazard.
 * Cannot slide. Can be in plugged or unplugged state.
 */
public class HoleInIce extends Hazard {
    private boolean plugged = false;
    
    @Override
    public String getNotation() {
        return plugged ? "PH" : "HI";
    }
    
    @Override
    public boolean canSlide() {
        return false;
    }
    
    @Override
    public String getHazardType() {
        return HazardType.HOLE_IN_ICE.name();
    }
    
    /**
     * Checks if this hole is plugged.
     * 
     * @return true if plugged, false otherwise
     */
    public boolean isPlugged() {
        return plugged;
    }
    
    /**
     * Sets the plugged state of this hole.
     * 
     * @param plugged The new plugged state
     */
    public void setPlugged(boolean plugged) {
        this.plugged = plugged;
    }
}