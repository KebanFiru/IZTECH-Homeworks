public class TreasureCard extends QuestCard{

    private Treasure treasure;
    private int value;


    public TreasureCard(String name, Treasure treasure, int value){

        super(name);
        this.treasure = treasure;
        this.value = value;

    }

    @Override
    public String toString(){

        return "Treasure card name:" + getName() + ","+"value:"+this.value;

    }

}
