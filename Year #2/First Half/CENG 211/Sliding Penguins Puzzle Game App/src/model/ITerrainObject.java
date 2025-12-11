package model;

/**
 * Interface for all objects that can be placed on the terrain grid.
 * Includes penguins, hazards, and food items.
 */
public interface ITerrainObject {

    /**
     * Gets the notation string for displaying this object on the grid.
     * 
     * @return A short string (typically 2 characters) representing this object
     */
    String getNotation();

    /**
     * Gets the row position of this object on the grid.
     * 
     * @return The row index (0-9)
     */
    int getRow();

    /**
     * Gets the column position of this object on the grid.
     * 
     * @return The column index (0-9)
     */
    int getColumn();

    /**
     * Sets the position of this object on the grid.
     * 
     * @param row The row index
     * @param column The column index
     */
    void setPosition(int row, int column);
}
