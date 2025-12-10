public class KingPenguin extends Penguin{

    public KingPenguin(String name){

        super(name,PenguinType.EMPEROR);
    }

    @Override
    public PenguinType getType() {
        return PenguinType.KING;
    }
}
