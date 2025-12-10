package penguins;

public class RoyalPenguin extends Penguin{

    public RoyalPenguin(String name){

        super(name,PenguinType.ROYAL);
    }

    @Override
    public PenguinType getType() {
        return PenguinType.ROYAL;
    }
}
