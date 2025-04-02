import java.util.Random;

public class Player {

    private Tent tent;
    private TreasureBox treasurebox;
    private HazardBox hazardbox;
    private String name;
    private int score;
    private Random random;

    public Player(String name) {
        this.name = name;
        this.tent = new Tent();
        this.treasurebox = new TreasureBox();
        this.hazardbox = new HazardBox();
        this.score = 0;
        this.random = new Random();
    }

    public int rollDice(){
        return random.nextInt(30);
    }

    public int calculateScore(){
        score = 0;
        for (int i = 0; i < tent.getCurrentSize(); i++) {
            Treasure treasure = tent.toArray()[i];
            score += treasure.getValue();
        }
        return score;
    }

    public String toString(){
        return "Player [name=" + name + ", score=" + score + "]";
    }


    public Tent getTent() {
        return tent;
    }

    public TreasureBox getTreasureBox() {
        return treasurebox;
    }

    public HazardBox getHazardBox() {
        return hazardbox;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}

