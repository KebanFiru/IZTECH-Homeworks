package terrain;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

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