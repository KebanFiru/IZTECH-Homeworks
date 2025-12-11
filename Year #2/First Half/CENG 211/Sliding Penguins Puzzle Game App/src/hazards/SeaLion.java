package hazards;

import penguins.Penguin;
import terrain.Direction;
import terrain.IcyTerrain;
import model.ISlidable;

/**
 * Sea Lion hazard - a predator that can slide when hit.
 * When a penguin hits it, the penguin bounces back and the SeaLion starts sliding.
 */
public class SeaLion extends Hazard implements ISlidable {
    private boolean sliding = false;
    
    /**
     * Constructs a new SeaLion hazard.
     */
    public SeaLion() {
        super();
    }

    @Override
    public String getNotation() {
        return "SL";
    }

    @Override
    public boolean canSlide() {
        return true;
    }

    @Override
    public String getHazardType() {
        return HazardType.SEALION.name();
    }
    
    @Override
    public void onPenguinLand(Penguin penguin, IcyTerrain terrain, Direction dir) {
        if (penguin == null || terrain == null || dir == null) {
            return;
        }
        System.out.println(penguin.getName() + " collides with a " + getHazardType() + "!");
        // Penguin bounces in opposite direction
        Direction opposite = getOppositeDirection(dir);
        System.out.println(penguin.getName() + " bounces back in the opposite direction.");
        // SeaLion starts sliding in the original direction
        System.out.println("The SeaLion starts sliding in the original direction.");
        // Remove SeaLion from current position
        terrain.removeObject(getRow(), getColumn());
        // Slide penguin in opposite direction
        terrain.slidePenguin(penguin, opposite, -1, false);
        // Slide SeaLion in original direction
        slide(dir, terrain);
    }
    
    private Direction getOppositeDirection(Direction dir) {
        switch (dir) {
            case UP: return Direction.DOWN;
            case DOWN: return Direction.UP;
            case LEFT: return Direction.RIGHT;
            case RIGHT: return Direction.LEFT;
            default: return dir;
        }
    }
    
    @Override
    public void slide(Direction direction, IcyTerrain terrain) {
        if (terrain == null || direction == null) {
            return;
        }
        setSliding(true);
        int currentRow = getRow();
        int currentColumn = getColumn();
        
        while (true) {
            int[] next = terrain.getNextPosition(currentRow, currentColumn, direction);
            
            if (!terrain.isValidPosition(next[0], next[1])) {
                // Falls off edge
                setSliding(false);
                return;
            }
            
            Object obj = terrain.getObjectAt(next[0], next[1]);
            
            if (obj != null && obj.getClass() == food.FoodItem.class) {
                // Remove food and continue
                terrain.getFoodItems().remove(obj);
                terrain.removeObject(next[0], next[1]);
                currentRow = next[0];
                currentColumn = next[1];
                continue;
            } 
            else if (obj != null && obj.getClass() == HoleInIce.class) {
                // Falls into hole and plugs it
                ((HoleInIce)obj).setPlugged(true);
                System.out.println("The SeaLion falls into a hole and plugs it.");
                setSliding(false);
                return;
            } 
            else if (obj instanceof Hazard || obj instanceof Penguin) {
                // Stops before obstacle (Hazard or Penguin)
                setPosition(currentRow, currentColumn);
                terrain.placeObject(this, currentRow, currentColumn);
                setSliding(false);
                return;
            } 
            else {
                // Empty square - continue to next position
                currentRow = next[0];
                currentColumn = next[1];
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