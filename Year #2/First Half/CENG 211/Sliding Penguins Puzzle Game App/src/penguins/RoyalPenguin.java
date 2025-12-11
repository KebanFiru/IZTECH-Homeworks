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
    public String getNotation() {
        return "RG";
    }

    @Override
    public void move(Direction dir, IcyTerrain terrain) {
        terrain.slidePenguin(this, dir, -1, false);
    }

    /**
     * Uses special ability: Moves one safe square, then slides normally.
     * 
     * @param dir The direction to move and slide
     * @param terrain The terrain to move on
     */
    @Override
    public void useSpecialAbility(Direction dir, IcyTerrain terrain) {
        int[] pos = getPosition();
        int[] next = terrain.getNextPosition(pos[0], pos[1], dir);
        if (terrain.isValidPosition(next[0], next[1])) {
            terrain.removeObject(pos[0], pos[1]);
            setPosition(next[0], next[1]);
            terrain.placeObject(this, next[0], next[1]);
        }
        terrain.slidePenguin(this, dir, -1, false);
        setSpecialActionUsed(true);
    }
}