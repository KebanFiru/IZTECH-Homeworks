package penguins;

public class RockhopperPenguin extends Penguin{

    public RockhopperPenguin(String name){

        super(name,PenguinType.ROCKCHOPPER);
    }

    @Override
    public PenguinType getType() {
        return PenguinType.ROCKCHOPPER;
    }
}
