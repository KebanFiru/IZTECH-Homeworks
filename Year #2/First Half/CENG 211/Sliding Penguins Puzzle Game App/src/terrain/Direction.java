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

    public Direction getOpposite() {
        switch (this) {
            case UP: return DOWN;
            case DOWN: return UP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
            default: return null;
        }
    }
}