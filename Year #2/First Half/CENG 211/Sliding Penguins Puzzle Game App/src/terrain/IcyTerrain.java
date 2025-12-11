package terrain;

import penguins.*;
import food.FoodItem;
import hazards.*;
import model.ITerrainObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the icy terrain grid and object placement.
 * Does NOT handle game flow, UI, or user input.
 */
public class IcyTerrain {
    /** The size of the square grid (10x10) */
    public static final int GRID_SIZE = 10;
    private final List<List<ITerrainObject>> grid;
    private final List<Penguin> penguins;
    private final List<FoodItem> foodItems;
    private final List<Hazard> hazards;

    public IcyTerrain() {
        grid = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            List<ITerrainObject> row = new ArrayList<>();
            for (int j = 0; j < GRID_SIZE; j++) {
                row.add(null);
            }
            grid.add(row);
        }
        penguins = new ArrayList<>();
        foodItems = new ArrayList<>();
        hazards = new ArrayList<>();
    }

    // --- Grid Management ---

    /**
     * Gets the size of the grid.
     * 
     * @return The grid size (10x10)
     */
    public int getGridSize() {
        return GRID_SIZE;
    }

    /**
     * Checks if a position is valid within the grid bounds.
     * 
     * @param row The row index (0-9)
     * @param column The column index (0-9)
     * @return true if the position is valid, false otherwise
     */
    public boolean isValidPosition(int row, int column) {
        return row >= 0 && row < GRID_SIZE && column >= 0 && column < GRID_SIZE;
    }

    /**
     * Gets the next position based on current position and direction.
     * 
     * @param row The current row index
     * @param column The current column index
     * @param dir The direction of movement
     * @return An array [nextRow, nextColumn] representing the next position
     */
    public int[] getNextPosition(int row, int column, Direction dir) {
        switch (dir) {
            case UP: return new int[]{row - 1, column};
            case DOWN: return new int[]{row + 1, column};
            case LEFT: return new int[]{row, column - 1};
            case RIGHT: return new int[]{row, column + 1};
            default: return new int[]{row, column};
        }
    }

    /**
     * Gets the object at a specific position on the grid.
     * 
     * @param row The row index
     * @param column The column index
     * @return The object at that position, or null if empty or invalid position
     */
    public ITerrainObject getObjectAt(int row, int column) {
        if (!isValidPosition(row, column)) return null;
        return grid.get(row).get(column);
    }

    /**
     * Places an object at a specific position on the grid.
     * 
     * @param obj The object to place
     * @param row The row index
     * @param column The column index
     */
    public void placeObject(ITerrainObject obj, int row, int column) {
        if (isValidPosition(row, column)) {
            grid.get(row).set(column, obj);
        }
    }

    /**
     * Removes an object from a specific position on the grid.
     * 
     * @param row The row index
     * @param column The column index
     */
    public void removeObject(int row, int column) {
        if (isValidPosition(row, column)) {
            grid.get(row).set(column, null);
        }
    }

    // --- Entity Management ---

    /**
     * Gets the list of all penguins on the terrain.
     * 
     * @return List of penguins
     */
    public List<Penguin> getPenguins() {
        return penguins;
    }

    /**
     * Gets the list of all hazards on the terrain.
     * 
     * @return List of hazards
     */
    public List<Hazard> getHazards() {
        return hazards;
    }

    /**
     * Gets the list of all food items on the terrain.
     * 
     * @return List of food items
     */
    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    /**
     * Adds a penguin to the terrain.
     * 
     * @param p The penguin to add
     */
    public void addPenguin(Penguin p) {
        penguins.add(p);
    }

    /**
     * Adds a hazard to the terrain.
     * 
     * @param h The hazard to add
     */
    public void addHazard(Hazard h) {
        hazards.add(h);
    }

    /**
     * Adds a food item to the terrain.
     * 
     * @param f The food item to add
     */
    public void addFoodItem(FoodItem f) {
        foodItems.add(f);
    }

    // --- Movement Logic ---

    /**
     * Slides a penguin in a given direction across the icy terrain.
     * The penguin continues sliding until it hits an obstacle, food, hazard, another penguin,
     * or falls off the edge.
     * 
     * @param penguin The penguin to slide
     * @param dir The direction to slide
     * @param stopSquare The square number to stop at (positive value), or -1 to slide until obstacle
     * @param canJump Whether the penguin can jump over obstacles (special ability)
     */
    public void slidePenguin(Penguin penguin, Direction dir, int stopSquare, boolean canJump) {
        if (penguin == null || dir == null) {
            System.err.println("Error: Cannot slide - penguin or direction is null");
            return;
        }
        
        removeObject(penguin.getRow(), penguin.getColumn());
        int squareCount = 0;
        int currentRow = penguin.getRow();
        int currentColumn = penguin.getColumn();
        boolean hasJumped = false;

        while (true) {
            int[] next = getNextPosition(currentRow, currentColumn, dir);
            squareCount++;

            if (!isValidPosition(next[0], next[1])) {
                penguin.setRemoved(true);
                System.out.println(penguin.getName() + " falls into the water!");
                System.out.println("*** " + penguin.getName() + " IS REMOVED FROM THE GAME!");
                return;
            }

            ITerrainObject obj = getObjectAt(next[0], next[1]);

            if (stopSquare > 0 && squareCount == stopSquare && (obj == null || obj instanceof FoodItem)) {
                currentRow = next[0];
                currentColumn = next[1];
                if (obj instanceof FoodItem) {
                    penguin.collectFood((FoodItem) obj);
                    System.out.println(penguin.getName() + " takes the " + ((FoodItem)obj).getType() +
                            " on the ground. (Weight=" + ((FoodItem)obj).getWeight() + " units)");
                    foodItems.remove(obj);
                }
                penguin.setPosition(currentRow, currentColumn);
                placeObject(penguin, currentRow, currentColumn);
                return;
            }

            if (obj != null) {
                if (obj instanceof FoodItem) {
                    penguin.collectFood((FoodItem) obj);
                    System.out.println(penguin.getName() + " takes the " + ((FoodItem)obj).getType() +
                        " on the ground. (Weight=" + ((FoodItem)obj).getWeight() + " units)");
                    foodItems.remove(obj);
                    penguin.setPosition(next[0], next[1]);
                    placeObject(penguin, next[0], next[1]);
                    return;
                } else if (obj instanceof Hazard) {
                    // Check if penguin can jump over this hazard
                    if (canJump && !hasJumped) {
                        System.out.println(penguin.getName() + " jumps over " + ((Hazard)obj).getHazardType() + " in its path.");
                        hasJumped = true;
                        // Continue sliding past the hazard
                        currentRow = next[0];
                        currentColumn = next[1];
                        continue;
                    } else {
                        ((Hazard)obj).onPenguinLand(penguin, this, dir);
                        return;
                    }
                } else if (obj instanceof Penguin) {
                    // Penguin collision: one stops, other starts sliding
                    penguin.setPosition(currentRow, currentColumn);
                    placeObject(penguin, currentRow, currentColumn);
                    System.out.println(penguin.getName() + " collides with " + ((Penguin)obj).getName() + ".");
                    System.out.println("Movement is transferred to " + ((Penguin)obj).getName() + ".");
                    // Transfer movement to the other penguin
                    Penguin otherPenguin = (Penguin)obj;
                    removeObject(otherPenguin.getRow(), otherPenguin.getColumn());
                    slidePenguin(otherPenguin, dir, -1, false);
                    return;
                }
            }
            currentRow = next[0];
            currentColumn = next[1];
        }
    }

    // --- Setup Helpers ---

    /**
     * Creates a penguin of the specified type.
     * Factory method for penguin creation.
     * 
     * @param name The name for the penguin
     * @param type The type of penguin to create
     * @return A new penguin instance of the specified type
     */
    public Penguin createPenguin(String name, PenguinType type) {
        switch (type) {
            case KING: return new KingPenguin(name);
            case EMPEROR: return new EmperorPenguin(name);
            case ROYAL: return new RoyalPenguin(name);
            case ROCKHOPPER: return new RockhopperPenguin(name);
            default: return new KingPenguin(name);
        }
    }

    /**
     * Gets a list of all available edge positions on the grid.
     * Edge positions are useful for placing penguins at the start of the game.
     * 
     * @return List of [row, column] arrays representing edge positions
     */
    public List<int[]> getAvailableEdgePositions() {
        List<int[]> positions = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            positions.add(new int[]{i, 0});
            positions.add(new int[]{i, GRID_SIZE - 1});
        }
        for (int i = 1; i < GRID_SIZE - 1; i++) {
            positions.add(new int[]{0, i});
            positions.add(new int[]{GRID_SIZE - 1, i});
        }
        return positions;
    }

    /**
     * Finds a random empty position on the grid.
     * 
     * @param avoidPenguins If true, avoids positions occupied by penguins
     * @return An array [row, column] representing an empty position, or null if none available
     */
    public int[] findEmptyPosition(boolean avoidPenguins) {
        List<int[]> emptyPositions = new ArrayList<>();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                ITerrainObject obj = getObjectAt(row, col);
                if (obj == null || (!avoidPenguins && obj instanceof FoodItem)) {
                    emptyPositions.add(new int[]{row, col});
                } else if (avoidPenguins && obj instanceof Penguin) {
                    continue;
                }
            }
        }
        if (emptyPositions.isEmpty()) return null;
        return emptyPositions.get((int) (Math.random() * emptyPositions.size()));
    }
}