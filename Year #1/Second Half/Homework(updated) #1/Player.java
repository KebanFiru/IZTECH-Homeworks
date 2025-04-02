import java.util.Random;

public class Player {

    private Tent tent;
    private TreasureBox treasureBox;
    private HazardBox hazardBox;
    private String name;
    private int score;
    private Random random;

    public Player(String name) {
        this.name = name;
        this.tent = new Tent();
        this.treasureBox = new TreasureBox();
        this.hazardBox = new HazardBox();
        this.score = 0;
        this.random = new Random();
    }

    public int rollDice(){
        return random.nextInt(30);
    }

    public int calculateScore(){
        try {
            score = 0;
            for (int i = 0; i < tent.getCurrentSize(); i++) {
                Object obj = tent.toArray()[i];
                if (obj instanceof Treasure) {
                    Treasure treasure = (Treasure) obj;
                    score += treasure.getValue();
                } else {
                    System.out.println("Warning: Non-treasure object found in tent: " + obj);
                }
            }
            return score;
        } catch (Exception e) {
            System.out.println("Error in calculateScore: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public String toString(){
        return "Player [name=" + name + ", score=" + score + "]";
    }

    public Tent getTent() {
        return tent;
    }

    public TreasureBox getTreasureBox() {
        return treasureBox;
    }

    public HazardBox getHazardBox() {
        return hazardBox;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}

