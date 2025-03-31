public class Treasure {

    private int value;

    public Treasure(int value){

        this.value = value;

    }

    @Override
    public String toString(){

        return String.format("%s", value);

    }

}
