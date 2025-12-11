package terrain;

/**
 * Enum representing the four cardinal directions for movement on the terrain.
 */
public enum Direction {
    /** Up direction (decreases column index) */
    UP,
    /** Down direction (increases column index) */
    DOWN,
    /** Left direction (decreases row index) */
    LEFT,
    /** Right direction (increases row index) */
    RIGHT;

    /**
     * Converts a character to a Direction.
     * 
     * @param c The character (U, D, L, or R, case-insensitive)
     * @return The corresponding Direction, or null if invalid
     */
    public static Direction fromChar(char c) {
        switch (Character.toUpperCase(c)) {
            case 'U': return UP;
            case 'D': return DOWN;
            case 'L': return LEFT;
            case 'R': return RIGHT;
            default: return null;
        }
    }
}