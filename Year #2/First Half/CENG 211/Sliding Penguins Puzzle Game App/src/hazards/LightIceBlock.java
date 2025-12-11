package hazards;

import terrain.Direction;
import terrain.IcyTerrain;
import model.ISlidable;
import penguins.Penguin;

/**
 * Light Ice Block hazard - a slidable obstacle.
 * Can be pushed one square by penguins.
 */
public class LightIceBlock extends Hazard implements ISlidable {
    private boolean sliding = false;

    /**
     * Constructs a new LightIceBlock hazard.
     */
    public LightIceBlock() {
        super();
    }

    @Override
    public String getNotation() {
        return "LB";
    }

    @Override
    public boolean canSlide() {
        return true;
    }

    @Override
    public String getHazardType() {
        return HazardType.LIGHT_ICE_BLOCK.name();
    }
    
    @Override
    public void onPenguinLand(Penguin penguin, IcyTerrain terrain, Direction dir) {
        if (penguin == null || terrain == null || dir == null) {
            return;
        }
        System.out.println(penguin.getName() + " collides with a " + getHazardType() + ".");
        // Penguin stops at current position before collision
        terrain.placeObject(penguin, penguin.getRow(), penguin.getColumn());
        // Penguin is stunned and skips next turn
        penguin.setStunned(true);
        System.out.println(penguin.getName() + " is stunned and will skip the next turn.");
        // Ice block starts sliding in the same direction
        slide(dir, terrain);
    }

    /**
     * Slides the ice block one square in the specified direction.
     * \n     * @param direction The direction to slide
     * @param terrain The terrain to slide on
     */
    @Override
    public void slide(Direction direction, IcyTerrain terrain) {
        if (direction == null || terrain == null) {
            return;
        }
        int row = getRow(), col = getColumn();
        
        while (true) {
            int[] next = terrain.getNextPosition(row, col, direction);
            
            if (!terrain.isValidPosition(next[0], next[1])) {
                // Falls off edge
                System.out.println("The LightIceBlock slides off the edge and falls into the water.");
                return;
            }
            
            Object obj = terrain.getObjectAt(next[0], next[1]);
            
            if (obj != null && obj.getClass() == food.FoodItem.class) {
                // Remove food and continue sliding
                terrain.getFoodItems().remove(obj);
                terrain.removeObject(next[0], next[1]);
                row = next[0];
                col = next[1];
                continue;
            } 
            else if (obj != null && obj.getClass() == HoleInIce.class) {
                // Falls into hole and plugs it
                ((HoleInIce)obj).setPlugged(true);
                System.out.println("The LightIceBlock falls into a hole and plugs it.");
                return;
            } 
            else if (obj instanceof Hazard || obj instanceof Penguin) {
                // Stops before obstacle
                setPosition(row, col);
                terrain.placeObject(this, row, col);
                setSliding(false);
                return;
            }
            else {
                // Empty square - continue to next position
                row = next[0];
                col = next[1];
            }
        }
    }

    @Override
    public boolean isSliding() {
        return sliding;
    }

    @Override
    public void setSliding(boolean sliding) {
        this.sliding = sliding;
    }
}