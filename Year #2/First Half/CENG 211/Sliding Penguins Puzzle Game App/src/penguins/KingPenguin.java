public class KingPenguin extends Penguin{

    public KingPenguin(String name){

        super(name,PenguinType.KING);
    }

    @Override
    public PenguinType getType() {
        return PenguinType.KING;
    }
}
