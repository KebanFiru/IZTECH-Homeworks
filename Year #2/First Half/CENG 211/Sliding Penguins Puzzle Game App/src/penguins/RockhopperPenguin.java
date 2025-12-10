package penguins;

public class RockhopperPenguin extends Penguin{

    public EmperorPenguin(String name){

        super(name,PenguinType.ROCKCHOPPER);
    }

    @Override
    public PenguinType getType() {
        return PenguinType.ROCKCHOPPER;
    }
}
