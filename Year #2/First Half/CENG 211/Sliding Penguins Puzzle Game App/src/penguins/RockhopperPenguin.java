package penguins;

public class RockhopperPenguin extends Penguin{

    public RockhopperPenguin(String name){

        super(name,PenguinType.ROCKCHOPPER);
    }

    @Override
    public PenguinType getType() {
        return PenguinType.ROCKCHOPPER;
    }

    @Override
    public void useSpecialAbility(Direction dir, IcyTerrain terrain) {
        terrain.slidePenguin(this, dir, -1, true);
        setSpecialEffectUsed(true);
    }
}
