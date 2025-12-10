package food;

import model.ITerrainObject;

public class FoodItem implements ITerrainObject {
    private FoodType type;
    private int weight;
    private int x, y;   // optional, for grid location

    /**
     * Constructor using random type and random weight (for terrain generation).
     */
    public FoodItem() {
        // Random type:
        FoodType[] types = FoodType.values();
        this.type = types[(int)(Math.random() * types.length)];
        // Random weight: 1 to 5
        this.weight = 1 + (int)(Math.random() * 5);
        this.x = 0;
        this.y = 0;
    }

    /**
     * Explicit constructor (for testing/etc.)
     */
    public FoodItem(FoodType type, int weight) {
        this.type = type;
        this.weight = weight;
        this.x = 0;
        this.y = 0;
    }

    public FoodType getType() {
        return type;
    }

    public int getWeight() {
        return weight;
    }

    // Optional notation: derive from enum name if needed
    public String getNotation() {
        return type.name().substring(0, 1); // "K" for KRILL, etc.
    }

    // Optional position methods
    public int[] getPosition() {
        return new int[]{x, y};
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}