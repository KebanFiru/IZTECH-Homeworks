package food;

import model.ITerrainObject;

public class FoodItem implements ITerrainObject {
    private FoodType type;
    private int weight;
    private int row, column;  // Grid location

    public FoodItem() {
        FoodType[] types = FoodType.values();
        this.type = types[(int)(Math.random() * types.length)];
        this.weight = 1 + (int)(Math.random() * 5);
        this.row = 0;
        this.column = 0;
    }

    public FoodItem(FoodType type, int weight) {
        this.type = type;
        this.weight = weight;
        this.row = 0;
        this.column = 0;
    }

    public FoodType getType() {
        return type;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String getNotation() {
        // Single letter notation from type name (take the first character)
        return type.name().substring(0, 1);
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