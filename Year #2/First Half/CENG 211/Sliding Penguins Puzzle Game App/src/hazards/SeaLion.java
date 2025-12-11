package hazards;

import penguins.Penguin;
import terrain.Direction;
import terrain.IcyTerrain;

/**
 * Sea Lion hazard - a predator that poses danger to penguins.
 * Cannot be moved or slid.
 */
public class SeaLion extends Hazard {
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
        return false;
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
        slideSeaLion(terrain, dir);
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
    
    private void slideSeaLion(IcyTerrain terrain, Direction dir) {
        if (terrain == null || dir == null) {
            return;
        }
        int currentRow = getRow();
        int currentColumn = getColumn();
        
        while (true) {
            int[] next = terrain.getNextPosition(currentRow, currentColumn, dir);
            
            if (!terrain.isValidPosition(next[0], next[1])) {
                // Falls off edge
                return;
            }
            
            Object obj = terrain.getObjectAt(next[0], next[1]);
            
            if (obj instanceof food.FoodItem) {
                // Remove food and continue
                terrain.getFoodItems().remove(obj);
                terrain.removeObject(next[0], next[1]);
                currentRow = next[0];
                currentColumn = next[1];
                continue;
            } 
            else if (obj instanceof HoleInIce) {
                // Falls into hole and plugs it
                ((HoleInIce)obj).setPlugged(true);
                System.out.println("The SeaLion falls into a hole and plugs it.");
                return;
            } 
            else if (obj != null) {
                // Stops before obstacle
                setPosition(currentRow, currentColumn);
                terrain.placeObject(this, currentRow, currentColumn);
                return;
            }
            
            currentRow = next[0];
            currentColumn = next[1];
        }
    }
}