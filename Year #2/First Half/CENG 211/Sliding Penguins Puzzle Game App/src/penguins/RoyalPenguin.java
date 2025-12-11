package penguins;

import terrain.Direction;
import terrain.IcyTerrain;

/**
 * Royal Penguin implementation.
 * Special ability: Moves one safe square before sliding normally.
 */
public class RoyalPenguin extends Penguin {
    /**
     * Constructs a new Royal Penguin.
     * 
     * @param name The name of the penguin
     */
    public RoyalPenguin(String name) {
        super(name, PenguinType.ROYAL);
    }

    @Override
    public void move(Direction dir, IcyTerrain terrain) {
        terrain.slidePenguin(this, dir, -1, false);
    }

    /**
     * Uses special ability: Moves one safe square adjacent (horizontally/vertically).
     * Does NOT slide after moving - just a single step.
     * Can fall into water or encounter hazards during this move.
     * 
     * @param dir The direction to move one square
     * @param terrain The terrain to move on
     */
    @Override
    public void useSpecialAbility(Direction dir, IcyTerrain terrain) {
        int[] pos = getPosition();
        int[] next = terrain.getNextPosition(pos[0], pos[1], dir);
        
        // Remove from current position
        terrain.removeObject(pos[0], pos[1]);
        
        // Check if next position is valid
        if (!terrain.isValidPosition(next[0], next[1])) {
            // Falls into water
            setRemoved(true);
            System.out.println(getName() + " falls into the water!");
            System.out.println("*** " + getName() + " IS REMOVED FROM THE GAME!");
            setSpecialActionUsed(true);
            return;
        }
        
        // Check what's at the destination
        model.ITerrainObject obj = terrain.getObjectAt(next[0], next[1]);
        
        if (obj != null && obj.getClass() == food.FoodItem.class) {
            // Collect food
            food.FoodItem foodItem = (food.FoodItem) obj;
            collectFood(foodItem);
            System.out.println(getName() + " moves one square to the " + dir.toString() + ".");
            System.out.println(getName() + " takes the " + foodItem.getType() + " on the ground. (Weight=" + foodItem.getWeight() + " units)");
            terrain.getFoodItems().remove(foodItem);
            terrain.removeObject(next[0], next[1]);
            setPosition(next[0], next[1]);
            terrain.placeObject(this, next[0], next[1]);
        } else if (obj instanceof hazards.Hazard) {
            // Landed on hazard - penguin should stay at previous position before triggering hazard effect
            System.out.println(getName() + " moves one square to the " + dir.toString() + ".");
            setPosition(pos[0], pos[1]);
            ((hazards.Hazard)obj).onPenguinLand(this, terrain, dir);
        } else if (obj instanceof Penguin) {
            // Can't move onto another penguin - stay at current position
            System.out.println(getName() + " tries to move but there's another penguin in the way.");
            setPosition(pos[0], pos[1]);
            terrain.placeObject(this, pos[0], pos[1]);
        } else {
            // Empty square - safe move
            System.out.println(getName() + " moves one square to the " + dir.toString() + ".");
            setPosition(next[0], next[1]);
            terrain.placeObject(this, next[0], next[1]);
        }
        setSpecialActionUsed(true);
    }
}