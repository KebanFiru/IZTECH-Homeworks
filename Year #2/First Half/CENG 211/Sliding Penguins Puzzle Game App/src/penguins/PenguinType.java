package penguins;

/**
 * Enum representing different types of penguins.
 * Each type has unique abilities and characteristics.
 */
public enum PenguinType {
    /** King Penguin - special ability stops on 5th square */
    KING,
    /** Emperor Penguin - special ability stops on 3rd square */
    EMPEROR,
    /** Royal Penguin - special ability moves one square then slides */
    ROYAL,
    /** Rockhopper Penguin - special ability can jump over obstacles */
    ROCKHOPPER;

    /**
     * Gets a random penguin type.
     * 
     * @return A randomly selected penguin type
     */
    public static PenguinType getRandom() {
        PenguinType[] values = PenguinType.values();
        int index = (int)(Math.random() * values.length);
        return values[index];
    }
}