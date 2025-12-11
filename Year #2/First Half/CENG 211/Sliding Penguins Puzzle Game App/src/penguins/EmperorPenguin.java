package penguins;

import terrain.Direction;
import terrain.IcyTerrain;

public class EmperorPenguin extends Penguin {
    public EmperorPenguin(String name) {
        super(name, PenguinType.EMPEROR);
    }

    @Override
    public String getNotation() {
        return "EM";
    }

    @Override
    public void move(Direction dir, IcyTerrain terrain) {
        terrain.slidePenguin(this, dir, -1, false);
    }

    @Override
    public void useSpecialAbility(Direction dir, IcyTerrain terrain) {
        terrain.slidePenguin(this, dir, 3, false); // stops on 3rd square
        setSpecialActionUsed(true);
    }
}