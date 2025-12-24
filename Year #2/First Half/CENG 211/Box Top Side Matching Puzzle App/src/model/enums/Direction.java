package model.enums;

/**
 * Enum representing the four cardinal directions for box rolling.
 * Used to specify the direction in which boxes roll during gameplay.
 */
public enum Direction {
    /**
     * Upward direction (towards Row 1)
     */
    UP,
    
    /**
     * Downward direction (towards Row 8)
     */
    DOWN,
    
    /**
     * Left direction (towards Column 1)
     */
    LEFT,
    
    /**
     * Right direction (towards Column 8)
     */
    RIGHT;
    
    /**
     * Returns the opposite direction.
     * Useful for determining rollback or reverse operations.
     * 
     * @return the opposite Direction
     */
    public Direction getOpposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                throw new IllegalStateException("Unknown direction: " + this);
        }
    }
}
