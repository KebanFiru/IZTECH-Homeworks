package hazards;

import penguins.Penguin;
import terrain.Direction;
import terrain.IcyTerrain;

/**
 * Hole in the ice hazard.
 * Cannot slide. Can be in plugged or unplugged state.
 */
public class HoleInIce extends Hazard {
    private boolean plugged = false;
    
    /**
     * Constructs a new HoleInIce hazard.
     */
    public HoleInIce() {
        super();
    }
    
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
    
    @Override
    public void onPenguinLand(Penguin penguin, IcyTerrain terrain, Direction dir) {
        if (penguin == null || terrain == null) {
            return;
        }
        if (plugged) {
            // Penguin can pass through plugged hole - continues sliding
            System.out.println(penguin.getName() + " passes over a plugged hole.");
            // Penguin was stopped before the hole, now continues sliding through it
            terrain.slidePenguin(penguin, dir, -1, false);
        } 
        else {
            // Penguin falls into hole
            System.out.println(penguin.getName() + " falls into a " + getHazardType() + "!");
            System.out.println("*** " + penguin.getName() + " IS REMOVED FROM THE GAME!");
            penguin.setRemoved(true);
            // NOTE: Hole is NOT plugged by penguins, only by sliding hazards (LightIceBlock/SeaLion)
        }
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