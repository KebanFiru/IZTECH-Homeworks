package penguins;

import model.ITerrainObject;
import food.FoodItem;
import terrain.Direction;
import terrain.IcyTerrain;

import java.util.ArrayList;
import java.util.List;

public abstract class Penguin implements ITerrainObject {
    private final String name;
    private final PenguinType type;
    private int row, column;
    private List<FoodItem> collectedFoods;
    private int movesLeft;
    private boolean specialEffectUsed;
    private boolean stunned;
    private boolean removed;

    public Penguin(String name, PenguinType type) {
        this.name = name;
        this.type = type;
        this.row = 0;
        this.column = 0;
        this.collectedFoods = new ArrayList<>();
        this.movesLeft = 4;
        this.specialEffectUsed = false;
        this.stunned = false;
        this.removed = false;
    }

    public String getName() {
        return name;
    }
    public PenguinType getType() {
        return type;
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
    public int[] getPosition() {
        return new int[] { row, column };
    }

    public boolean isStunned() {
        return stunned;
    }
    public void setStunned(boolean value) {
        stunned = value;
    }

    public boolean isRemoved() {
        return removed;
    }
    public void setRemoved(boolean value) {
        removed = value;
    }

    public boolean hasUsedSpecialAction() {
        return specialEffectUsed;
    }
    public void setSpecialActionUsed(boolean value) {
        specialEffectUsed = value;
    }

    public int getMovesLeft() {
        return movesLeft;
    }
    public void decrementMoves() {
        if (movesLeft > 0) movesLeft--;
    }

    public void collectFood(FoodItem food) {
        collectedFoods.add(food);
    }
    public List<FoodItem> getCollectedFoods() {
        return collectedFoods;
    }
    public int getTotalFoodWeight() {
        int total = 0;
        for (FoodItem food : collectedFoods) {
            total += food.getWeight();
        }
        return total;
    }
    public FoodItem removeLightestFood() {
        if (collectedFoods.isEmpty()) return null;
        FoodItem lightest = collectedFoods.get(0);
        for (FoodItem food : collectedFoods) {
            if (food.getWeight() < lightest.getWeight()) {
                lightest = food;
            }
        }
        collectedFoods.remove(lightest);
        return lightest;
    }

    @Override
    public abstract String getNotation();
    public abstract void move(Direction dir, IcyTerrain terrain);
    public abstract void useSpecialAbility(Direction dir, IcyTerrain terrain);
}