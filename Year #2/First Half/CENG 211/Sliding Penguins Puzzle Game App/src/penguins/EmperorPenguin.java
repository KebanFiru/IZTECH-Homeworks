package penguins;

import terrain.Direction;
import terrain.IcyTerrain;

/**
 * Emperor Penguin implementation.
 * Special ability: Stops precisely on the 3rd square when sliding.
 */
public class EmperorPenguin extends Penguin {
    /**
     * Constructs a new Emperor Penguin.
     * 
     * @param name The name of the penguin
     */
    public EmperorPenguin(String name) {
        super(name, PenguinType.EMPEROR);
    }

    @Override
    public void move(Direction dir, IcyTerrain terrain) {
        terrain.slidePenguin(this, dir, -1, false);
    }

    /**
     * Uses special ability: Slides and stops precisely on the 3rd square.
     * 
     * @param dir The direction to slide
     * @param terrain The terrain to slide on
     */
    @Override
    public void useSpecialAbility(Direction dir, IcyTerrain terrain) {
        terrain.slidePenguin(this, dir, 3, false);
        setSpecialActionUsed(true);
    }
}