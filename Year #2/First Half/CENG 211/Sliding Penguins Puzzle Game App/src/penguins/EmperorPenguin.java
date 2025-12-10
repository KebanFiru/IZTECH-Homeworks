package penguins;

public class EmperorPenguin extends Penguin{

    public EmperorPenguin(String name){

        super(name,PenguinType.EMPEROR);
    }

    @Override
    public PenguinType getType() {

        return PenguinType.EMPEROR;
    }

    @Override
    public void useSpecialAbility(Direction dir, IcyTerrain terrain) {
        
        terrain.slidePenguin(this, dir, 3, false);
        setSpecialEffectUsed(true);
    }

}
