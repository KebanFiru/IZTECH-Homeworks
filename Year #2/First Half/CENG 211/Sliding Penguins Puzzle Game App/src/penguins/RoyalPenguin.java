package penguins;

import terrain.Direction;
import terrain.IcyTerrain;

public class RoyalPenguin extends Penguin {
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

    @Override
    public void useSpecialAbility(Direction dir, IcyTerrain terrain) {
        // Moves one safe square, then slides as usual
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