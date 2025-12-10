package penguins;

import terrain.Direction;
import terrain.IcyTerrain;

public class RockhopperPenguin extends Penguin {
    public RockhopperPenguin(String name) {
        super(name, PenguinType.ROCKHOPPER);
    }

    @Override
    public void move(Direction dir, IcyTerrain terrain) {
        terrain.slidePenguin(this, dir, -1, false);
    }

    @Override
    public void useSpecialAbility(Direction dir, IcyTerrain terrain) {
        terrain.slidePenguin(this, dir, -1, true); // canJump = true
        setSpecialActionUsed(true);
    }
}