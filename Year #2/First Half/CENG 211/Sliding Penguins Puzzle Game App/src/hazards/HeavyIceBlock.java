package hazards;

import terrain.Direction;
import terrain.IcyTerrain;
import penguins.Penguin;
import food.FoodItem;

/**
 * Heavy Ice Block hazard - a heavy immovable obstacle.
 * Cannot be moved by penguins due to its weight.
 */
public class HeavyIceBlock extends Hazard {

    /**
     * Constructs a new HeavyIceBlock hazard.
     */
    public HeavyIceBlock() {
        super();
    }

    @Override
    public String getNotation() {
        return "HB";
    }

    @Override
    public boolean canSlide() {
        return true;
    }

    @Override
    public String getHazardType() {
        return HazardType.HEAVY_ICE_BLOCK.name();
    }
    
    @Override
    public void onPenguinLand(Penguin penguin, IcyTerrain terrain, Direction dir) {
        if (penguin == null || terrain == null) {
            return;
        }
        System.out.println(penguin.getName() + " collides with a " + getHazardType() + ".");
        // Penguin stops at current position before collision
        terrain.placeObject(penguin, penguin.getRow(), penguin.getColumn());
        // Remove lightest food as penalty
        FoodItem lightest = penguin.removeLightestFood();
        if (lightest != null) {
            System.out.println(penguin.getName() + " loses the lightest food item (" + 
                    lightest.getNotation() + ", " + lightest.getWeight() + " units).");
        }
        // Heavy ice block cannot be moved - it stays in place
    }
}