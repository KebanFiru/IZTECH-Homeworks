package penguins;

import terrain.Direction;
import terrain.IcyTerrain;

public class KingPenguin extends Penguin {
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

    @Override
    public void useSpecialAbility(Direction dir, IcyTerrain terrain) {
        // King Penguin slides and stops on the 5th square.
        terrain.slidePenguin(this, dir, 5, false);
        setSpecialActionUsed(true);
    }
}