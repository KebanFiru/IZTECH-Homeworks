package src.hazards;

import src.model.IMovable;
import src.terrain.Direction;

/**
 * SeaLion Class
 * 
 * Represents a sea lion hazard on the icy terrain.
 * A penguin that hits a SeaLion bounces from it and starts sliding in the opposite direction.
 * Meanwhile, the penguin's movements are transmitted to the SeaLion.
 * 
 * Behavior:
 * - When a penguin collides: penguin bounces back (opposite direction), SeaLion slides forward
 * - After collision, both animals can fall from edges and collide into anything else
 * - If a LightIceBlock collides with a SeaLion:
 *   - LightIceBlock's movement is transmitted to SeaLion
 *   - LightIceBlock stops moving
 *   - SeaLion starts sliding
 * - If SeaLion slides into a HoleInIce, it falls and plugs the hole
 * 
 * Implements both IHazard and IMovable since it can slide on ice.
 */
public class SeaLion implements IHazard, IMovable {
    
    // Current row position on the grid
    private int row;
    
    // Current column position on the grid
    private int column;
    
    // Whether this hazard has been removed from the game
    private boolean removed;
    
    // Whether this hazard is currently sliding
    private boolean sliding;
    
    // Current direction of movement (null if stationary)
    private Direction currentDirection;
    
    /**
     * Constructor for SeaLion.
     * 
     * @param row the initial row position
     * @param column the initial column position
     */
    public SeaLion(int row, int column) {
        this.row = row;
        this.column = column;
        this.removed = false;
        this.sliding = false;
        this.currentDirection = null;
    }
    
    // ==================== IHazard Implementation ====================
    
    /**
     * Gets the hazard type.
     * 
     * @return HazardType.SEA_LION
     */
    @Override
    public HazardType getHazardType() {
        return HazardType.SEA_LION;
    }
    
    /**
     * Checks if this hazard can slide.
     * 
     * @return true (SeaLion can slide)
     */
    @Override
    public boolean canSlide() {
        return true;
    }
    
    /**
     * Gets the display name of this hazard.
     * 
     * @return "SeaLion"
     */
    @Override
    public String getHazardName() {
        return "SeaLion";
    }
    
    // ==================== ITerrainObject Implementation ====================
    
    @Override
    public int getRow() {
        return row;
    }
    
    @Override
    public int getColumn() {
        return column;
    }
    
    @Override
    public void setRow(int row) {
        this.row = row;
    }
    
    @Override
    public void setColumn(int column) {
        this.column = column;
    }
    
    @Override
    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    /**
     * Gets the display symbol for the grid.
     * 
     * @return "SL"
     */
    @Override
    public String getDisplaySymbol() {
        return HazardType.SEA_LION.getDisplaySymbol();
    }
    
    @Override
    public boolean isRemoved() {
        return removed;
    }
    
    @Override
    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
    
    // ==================== IMovable Implementation ====================
    
    /**
     * Initiates sliding movement in the specified direction.
     * The actual movement logic is handled by GameController.
     * 
     * @param direction the direction to slide
     */
    @Override
    public void slide(Direction direction) {
        this.sliding = true;
        this.currentDirection = direction;
    }
    
    @Override
    public boolean isSliding() {
        return sliding;
    }
    
    @Override
    public void setSliding(boolean sliding) {
        this.sliding = sliding;
    }
    
    @Override
    public Direction getCurrentDirection() {
        return currentDirection;
    }
    
    @Override
    public void setCurrentDirection(Direction direction) {
        this.currentDirection = direction;
    }
    
    /**
     * Checks if this hazard can move.
     * 
     * @return true if not removed from the game
     */
    @Override
    public boolean canMove() {
        return !removed;
    }
    
    @Override
    public String toString() {
        return "SeaLion at (" + row + ", " + column + ")";
    }
}
