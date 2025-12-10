package penguins;

public class RoyalPenguin extends Penguin{

    public RoyalPenguin(String name){

        super(name,PenguinType.ROYAL);
    }

    @Override
    public PenguinType getType() {
        return PenguinType.ROYAL;
    }
    
    @Override
    public void useSpecialAbility(Direction dir, IcyTerrain terrain) {
        int[] pos = getPosition();
        int[] next = terrain.getNextPosition(pos[0], pos[1], dir);
        if (terrain.isValidPosition(next[0], next[1])) {
            terrain.removeObject(pos[0], pos[1]);
            setPosition(next[0], next[1]);
            terrain.placeObject(this, next[0], next[1]);
        }
        terrain.slidePenguin(this, dir, -1, false); // Then regular slide
        setSpecialEffectUsed(true);
    }
}
