public class QuestCard {

    private String name;

    public QuestCard(String name){

        this.name = name;

    }

    public String toString(){

        return String.format("%s", this.name);

    }

    public boolean equals(Object obj){

        if(obj.getClass() == Turquoise.class || obj.getClass() == Gold.class || obj.getClass() == Obsidian.class){

            return true;

        }

        return false;

    }

}
