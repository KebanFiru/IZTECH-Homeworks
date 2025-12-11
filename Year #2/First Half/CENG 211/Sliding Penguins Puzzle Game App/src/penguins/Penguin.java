package penguins;

import model.ITerrainObject;
import model.ISlidable;
import food.FoodItem;
import terrain.Direction;
import terrain.IcyTerrain;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all penguin types.
 * Implements ITerrainObject for grid placement and ISlidable for movement.
 * Manages penguin state including position, collected food, special abilities, and status effects.
 */
public abstract class Penguin implements ITerrainObject, ISlidable {
    private final String name;
    private final PenguinType type;
    private int row, column;
    private List<FoodItem> collectedFoods;
    private int movesLeft;
    private boolean specialEffectUsed;
    private boolean stunned;
    private boolean removed;
    private boolean sliding;

    /**
     * Constructs a new Penguin with the specified name and type.
     * 
     * @param name The name of the penguin (e.g., "P1", "P2")
     * @param type The type of penguin (determines abilities)
     */
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
        this.sliding = false;
    }

    /**
     * Gets the name of this penguin.
     * 
     * @return The penguin's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the type of this penguin.
     * 
     * @return The penguin's type
     */
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
    
    /**
     * Gets the current position as an array.
     * 
     * @return An array [row, column] representing the position
     */
    public int[] getPosition() {
        return new int[] { row, column };
    }

    /**
     * Checks if the penguin is stunned.
     * Stunned penguins skip their turn.
     * 
     * @return true if stunned, false otherwise
     */
    public boolean isStunned() {
        return stunned;
    }
    
    /**
     * Sets the stunned status of the penguin.
     * 
     * @param value The new stunned status
     */
    public void setStunned(boolean value) {
        stunned = value;
    }

    /**
     * Checks if the penguin has been removed from the game.
     * Penguins are removed when they slide off the edge.
     * 
     * @return true if removed, false otherwise
     */
    public boolean isRemoved() {
        return removed;
    }
    
    /**
     * Sets the removed status of the penguin.
     * 
     * @param value The new removed status
     */
    public void setRemoved(boolean value) {
        removed = value;
    }

    /**
     * Checks if the penguin has used its special action this game.
     * 
     * @return true if special action has been used, false otherwise
     */
    public boolean hasUsedSpecialAction() {
        return specialEffectUsed;
    }
    
    /**
     * Sets whether the special action has been used.
     * 
     * @param value The new special action status
     */
    public void setSpecialActionUsed(boolean value) {
        specialEffectUsed = value;
    }

    /**
     * Gets the number of moves remaining for this penguin.
     * 
     * @return The number of moves left
     */
    public int getMovesLeft() {
        return movesLeft;
    }
    
    /**
     * Decrements the number of moves remaining.
     */
    public void decrementMoves() {
        if (movesLeft > 0) movesLeft--;
    }

    /**
     * Collects a food item.
     * 
     * @param food The food item to collect
     */
    public void collectFood(FoodItem food) {
        if (food != null) {
            collectedFoods.add(food);
        }
    }
    
    /**
     * Gets the list of all collected food items.
     * 
     * @return List of collected food items
     */
    public List<FoodItem> getCollectedFoods() {
        return collectedFoods;
    }
    
    /**
     * Calculates the total weight of all collected food items.
     * 
     * @return The total weight
     */
    public int getTotalFoodWeight() {
        int total = 0;
        for (FoodItem food : collectedFoods) {
            total += food.getWeight();
        }
        return total;
    }
    
    /**
     * Removes and returns the lightest food item from the collection.
     * Used by some hazards to penalize the penguin.
     * 
     * @return The lightest food item, or null if no food collected
     */
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

    // ISlidable interface implementation
    @Override
    public void slide(Direction direction, IcyTerrain terrain) {
        sliding = true;
        terrain.slidePenguin(this, direction, -1, false);
    }

    @Override
    public boolean isSliding() {
        return sliding;
    }

    @Override
    public void setSliding(boolean sliding) {
        this.sliding = sliding;
    }

    // Abstract methods to be implemented by subclasses
    
    /**
     * Gets the notation for displaying this penguin on the grid.
     * Returns the penguin's name (P1, P2, P3) for display.
     * 
     * @return The penguin's name
     */
    public String getNotation() {
        return name;
    }
    
    /**
     * Performs a normal move in the specified direction.
     * 
     * @param dir The direction to move
     * @param terrain The terrain to move on
     */
    public abstract void move(Direction dir, IcyTerrain terrain);
    
    /**
     * Uses the penguin's special ability in the specified direction.
     * Each penguin type has a unique special ability.
     * 
     * @param dir The direction for the special ability
     * @param terrain The terrain to act on
     */
    public abstract void useSpecialAbility(Direction dir, IcyTerrain terrain);
}