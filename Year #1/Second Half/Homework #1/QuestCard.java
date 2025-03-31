public class QuestCard {

    private String name;

    public QuestCard(String name){

        this.name = name;

    }

    public String toString(){

        return String.format("%s", this.name);

    }

    public boolean equals(Object obj){

        if(obj instanceof  Turquoise || obj instanceof  Gold || obj instanceof  Obsidian){

            return true;

        }

        return false;

    }

}
