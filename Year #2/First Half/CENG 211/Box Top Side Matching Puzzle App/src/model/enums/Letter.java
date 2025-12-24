package model.enums;

import java.util.Random;

/**
 * Enum representing the 8 possible letters that can be stamped on box surfaces.
 * Each box has 6 surfaces, and each surface can have one of these letters.
 */
public enum Letter {
    A, B, C, D, E, F, G, H;
    
    /**
     * Returns a random letter from the available 8 letters.
     * Used for randomly selecting target letters and generating box surfaces.
     * 
     * @return a random Letter
     */
    public static Letter getRandomLetter() {
        Letter[] letters = values();
        Random random = new Random();
        return letters[random.nextInt(letters.length)];
    }
    
    /**
     * Converts a character to a Letter enum.
     * Case-insensitive conversion.
     * 
     * @param c the character to convert
     * @return the corresponding Letter
     * @throws IllegalArgumentException if the character is not a valid letter (A-H)
     */
    public static Letter fromChar(char c) {
        char upperChar = Character.toUpperCase(c);
        if (upperChar >= 'A' && upperChar <= 'H') {
            return Letter.valueOf(String.valueOf(upperChar));
        }
        throw new IllegalArgumentException("Invalid letter: " + c + ". Must be between A and H.");
    }
    
    /**
     * Converts this Letter to its character representation.
     * 
     * @return the character representation of this Letter
     */
    public char toChar() {
        return this.name().charAt(0);
    }
}
