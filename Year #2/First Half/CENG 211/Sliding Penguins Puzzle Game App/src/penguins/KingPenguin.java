public class KingPenguin extends Penguin{

    public KingPenguin(String name){

        super(name,PenguinType.KING);
    }

    @Override
    public PenguinType getType() {
        return PenguinType.KING;
    }
    @Override
    public void useSpecialAbility(Direction dir, IcyTerrain terrain) {
        terrain.slidePenguin(this, dir, 5, false); 
        setSpecialEffectUsed(true);
    }
}
