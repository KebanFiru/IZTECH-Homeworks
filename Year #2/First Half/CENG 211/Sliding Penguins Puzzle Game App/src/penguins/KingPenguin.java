package penguins;

import terrain.Direction;
import terrain.IcyTerrain;

/**
 * King Penguin implementation.
 * Special ability: Slides and stops precisely on the 5th square.
 */
public class KingPenguin extends Penguin {
    /**
     * Constructs a new King Penguin.
     * 
     * @param name The name of the penguin
     */
    public KingPenguin(String name) {
        super(name, PenguinType.KING);
    }

    @Override
    public String getNotation() {
        return "KG";
    }

    @Override
    public void move(Direction dir, IcyTerrain terrain) {
        terrain.slidePenguin(this, dir, -1, false);
    }

    /**
     * Uses special ability: Slides and stops precisely on the 5th square.
     * 
     * @param dir The direction to slide
     * @param terrain The terrain to slide on
     */
    @Override
    public void useSpecialAbility(Direction dir, IcyTerrain terrain) {
        terrain.slidePenguin(this, dir, 5, false);
        setSpecialActionUsed(true);
    }
}