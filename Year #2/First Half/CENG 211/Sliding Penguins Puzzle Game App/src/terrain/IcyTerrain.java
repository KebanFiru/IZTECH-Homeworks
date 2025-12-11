package terrain;

import penguins.*;
import food.FoodItem;
import hazards.*;
import model.ITerrainObject;

import java.util. ArrayList;
import java.util. List;

/**
 * Manages the icy terrain grid and object placement.
 * Does NOT handle game flow, UI, or user input.
 */
public class IcyTerrain {
    private static final int GRID_SIZE = 10;
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

    public int getGridSize() {
        return GRID_SIZE;
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE;
    }

    public int[] getNextPosition(int x, int y, Direction dir) {
        switch (dir) {
            case UP: return new int[]{x, y - 1};
            case DOWN: return new int[]{x, y + 1};
            case LEFT: return new int[]{x - 1, y};
            case RIGHT: return new int[]{x + 1, y};
            default: return new int[]{x, y};
        }
    }

    public ITerrainObject getObjectAt(int x, int y) {
        if (!isValidPosition(x, y)) return null;
        return grid.get(y).get(x);
    }

    public void placeObject(ITerrainObject obj, int x, int y) {
        if (isValidPosition(x, y)) {
            grid.get(y).set(x, obj);
        }
    }

    public void removeObject(int x, int y) {
        if (isValidPosition(x, y)) {
            grid.get(y).set(x, null);
        }
    }

    // --- Entity Management ---

    public List<Penguin> getPenguins() {
        return penguins;
    }

    public List<Hazard> getHazards() {
        return hazards;
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void addPenguin(Penguin p) {
        penguins. add(p);
    }

    public void addHazard(Hazard h) {
        hazards.add(h);
    }

    public void addFoodItem(FoodItem f) {
        foodItems.add(f);
    }

    // --- Movement Logic ---

    public void slidePenguin(Penguin penguin, Direction dir, int stopSquare, boolean canJump) {
        removeObject(penguin. getRow(), penguin.getColumn());
        int squareCount = 0;
        int currentX = penguin.getRow();
        int currentY = penguin. getColumn();

        while (true) {
            int[] next = getNextPosition(currentX, currentY, dir);
            squareCount++;

            if (!isValidPosition(next[0], next[1])) {
                penguin.setRemoved(true);
                return;
            }

            ITerrainObject obj = getObjectAt(next[0], next[1]);

            if (stopSquare > 0 && squareCount == stopSquare && (obj == null || obj instanceof FoodItem)) {
                currentX = next[0];
                currentY = next[1];
                if (obj instanceof FoodItem) {
                    penguin.collectFood((FoodItem) obj);
                    foodItems.remove(obj);
                }
                penguin.setPosition(currentX, currentY);
                placeObject(penguin, currentX, currentY);
                return;
            }

            if (obj != null) {
                if (obj instanceof FoodItem) {
                    penguin.collectFood((FoodItem) obj);
                    foodItems.remove(obj);
                    penguin.setPosition(next[0], next[1]);
                    placeObject(penguin, next[0], next[1]);
                    return;
                } else if (obj instanceof Hazard) {
                    ((Hazard)obj).onPenguinLand(penguin, this, dir);
                    return;
                } else if (obj instanceof Penguin) {
                    penguin.setPosition(currentX, currentY);
                    placeObject(penguin, currentX, currentY);
                    return;
                }
            }
            currentX = next[0];
            currentY = next[1];
        }
    }

    // --- Setup Helpers (Can be here or in controller, your choice) ---

    public Penguin createPenguin(String name, PenguinType type) {
        switch (type) {
            case KING: return new KingPenguin(name);
            case EMPEROR: return new EmperorPenguin(name);
            case ROYAL: return new RoyalPenguin(name);
            case ROCKHOPPER: return new RockhopperPenguin(name);
            default: return new KingPenguin(name);
        }
    }

    public List<int[]> getAvailableEdgePositions() {
        List<int[]> positions = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            positions. add(new int[]{i, 0});
            positions.add(new int[]{i, GRID_SIZE - 1});
        }
        for (int i = 1; i < GRID_SIZE - 1; i++) {
            positions.add(new int[]{0, i});
            positions. add(new int[]{GRID_SIZE - 1, i});
        }
        return positions;
    }

    public int[] findEmptyPosition(boolean avoidPenguins) {
        List<int[]> emptyPositions = new ArrayList<>();
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                ITerrainObject obj = getObjectAt(x, y);
                if (obj == null || (! avoidPenguins && obj instanceof FoodItem)) {
                    emptyPositions.add(new int[]{x, y});
                } else if (avoidPenguins && obj instanceof Penguin) {
                    continue;
                }
            }
        }
        if (emptyPositions.isEmpty()) return null;
        return emptyPositions.get((int) (Math.random() * emptyPositions.size()));
    }
}