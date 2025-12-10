package penguins;

public class EmperorPenguin extends Penguin{

    public EmperorPenguin(String name){

        super(name,PenguinType.EMPEROR);
    }

    @Override
    public PenguinType getType() {
        return PenguinType.EMPEROR;
    }
}
