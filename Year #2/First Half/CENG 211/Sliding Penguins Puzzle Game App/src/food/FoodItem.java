package food;

import model.ITerrainObject;

/**
 * Represents a food item on the terrain that penguins can collect.
 * Each food item has a type and weight value.
 */
public class FoodItem implements ITerrainObject {
    private FoodType type;
    private int weight;
    private int row, column;

    /**
     * Constructs a food item with random type and weight.
     * Weight is randomly generated between 1 and 5 units.
     */
    public FoodItem() {
        FoodType[] types = FoodType.values();
        this.type = types[(int)(Math.random() * types.length)];
        this.weight = 1 + (int)(Math.random() * 5);
        this.row = 0;
        this.column = 0;
    }

    /**
     * Constructs a food item with specific type and weight.
     * 
     * @param type The type of food
     * @param weight The weight value (in units)
     */
    public FoodItem(FoodType type, int weight) {
        this.type = type;
        this.weight = weight;
        this.row = 0;
        this.column = 0;
    }

    /**
     * Gets the type of this food item.
     * 
     * @return The food type
     */
    public FoodType getType() {
        return type;
    }

    /**
     * Gets the weight of this food item.
     * 
     * @return The weight in units
     */
    public int getWeight() {
        return weight;
    }

    @Override
    public String getNotation() {
        switch (type) {
            case KRILL: return "Kr";
            case CRUSTACEAN: return "Cr";
            case ANCHOVY: return "An";
            case SQUID: return "Sq";
            case MACKEREL: return "Ma";
            default: return "??";
        }
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }
}