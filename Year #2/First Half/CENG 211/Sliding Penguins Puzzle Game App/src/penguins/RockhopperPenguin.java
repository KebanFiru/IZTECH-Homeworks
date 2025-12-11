package penguins;

import terrain.Direction;
import terrain.IcyTerrain;

/**
 * Rockhopper Penguin implementation.
 * Special ability: Can jump over obstacles while sliding.
 */
public class RockhopperPenguin extends Penguin {
    /**
     * Constructs a new Rockhopper Penguin.
     * 
     * @param name The name of the penguin
     */
    public RockhopperPenguin(String name) {
        super(name, PenguinType.ROCKHOPPER);
    }

    @Override
    public String getNotation() {
        return "RH";
    }

    @Override
    public void move(Direction dir, IcyTerrain terrain) {
        terrain.slidePenguin(this, dir, -1, false);
    }

    /**
     * Uses special ability: Slides with the ability to jump over obstacles.
     * 
     * @param dir The direction to slide
     * @param terrain The terrain to slide on
     */
    @Override
    public void useSpecialAbility(Direction dir, IcyTerrain terrain) {
        terrain.slidePenguin(this, dir, -1, true);
        setSpecialActionUsed(true);
    }
}