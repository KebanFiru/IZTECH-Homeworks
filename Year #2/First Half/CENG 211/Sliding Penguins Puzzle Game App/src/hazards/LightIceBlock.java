package src.hazards;

import src.model.IMovable;
import src.terrain.Direction;

/**
 * LightIceBlock Class
 * 
 * Represents a light ice block hazard on the icy terrain.
 * This ice block starts moving in the transmitted direction after a penguin
 * or another sliding hazard collides into it.
 * 
 * Behavior:
 * - Moving penguins or other sliding hazards stop on the square at the moment of collision
 * - A sliding LightIceBlock can fall from the edges
 * - The colliding penguin is temporarily stunned and their next turn is skipped
 * - If a sliding hazard comes across a food item, that food is removed
 * - If a sliding hazard collides into another penguin, the hazard stops
 * 
 * Implements both IHazard and IMovable since it can slide on ice.
 */
public class LightIceBlock implements IHazard, IMovable {
    
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
     * Constructor for LightIceBlock.
     * 
     * @param row the initial row position
     * @param column the initial column position
     */
    public LightIceBlock(int row, int column) {
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
     * @return HazardType.LIGHT_ICE_BLOCK
     */
    @Override
    public HazardType getHazardType() {
        return HazardType.LIGHT_ICE_BLOCK;
    }
    
    /**
     * Checks if this hazard can slide.
     * 
     * @return true (LightIceBlock can slide)
     */
    @Override
    public boolean canSlide() {
        return true;
    }
    
    /**
     * Gets the display name of this hazard.
     * 
     * @return "LightIceBlock"
     */
    @Override
    public String getHazardName() {
        return "LightIceBlock";
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
     * @return "LB"
     */
    @Override
    public String getDisplaySymbol() {
        return HazardType.LIGHT_ICE_BLOCK.getDisplaySymbol();
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
        return "LightIceBlock at (" + row + ", " + column + ")";
    }
}
