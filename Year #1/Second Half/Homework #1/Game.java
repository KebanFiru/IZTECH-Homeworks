public class Game {

    private int numberOfRounds;
    private Player player;
    private Box box;
    private Chest chest;

    public Game(int numberOfRounds, Player player, Box box, Chest chest){

        this.numberOfRounds = numberOfRounds;
        this.player = player;
        this.box = box;
        this.chest = chest;

    }

    public void initializeGameComponents(){
        box.initializeBox();

        chest.initializeChest();
    }

    public void claimTreasures(TreasureBox treasurebox){
        while (!treasurebox.isEmpty()) {
            QuestCard card = treasurebox.remove();
            if (card instanceof TreasureCard) {
                TreasureCard treasureCard = (TreasureCard) card;
                Treasure treasureType = treasureCard.getTreasure();
                int treasureAmount = treasureCard.getValue();

                for (int i = 0; i < treasureAmount; i++) {
                    for (int j = 0; j < chest.getCurrentSize(); j++) {
                        Treasure chestTreasure = chest.toArray()[j];
                        if (chestTreasure.getClass().equals(treasureType.getClass())) {
                            chest.remove(chestTreasure);
                            player.getTent().add(chestTreasure);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void play(){
        System.out.println("Starting Incan Gold game with " + numberOfRounds + " rounds...");

        initializeGameComponents();

        for (int round = 1; round <= numberOfRounds; round++) {
            System.out.println("\nRound " + round + ":");

            for (int roll = 1; roll <= 3; roll++) {
                int diceResult = player.rollDice();
                System.out.println("  Roll " + roll + ": " + diceResult);

                QuestCard drawnCard = box.removeByIndex(diceResult);
                if (drawnCard == null) {
                    roll--;
                    continue;
                }

                System.out.println("  Drew card: " + drawnCard);

                if (drawnCard instanceof HazardCard) {
                    player.getHazardBox().add(drawnCard);
                    System.out.println("  Added to HazardBox");
                } else if (drawnCard instanceof TreasureCard) {
                    player.getTreasureBox().add(drawnCard);
                    System.out.println("  Added to TreasureBox");
                }
            }
        }

        int hazardCount = player.getHazardBox().getCurrentSize();
        int treasureCount = player.getTreasureBox().getCurrentSize();

        System.out.println("\nGame Over!");
        System.out.println("HazardBox size: " + hazardCount);
        System.out.println("TreasureBox size: " + treasureCount);

        if (hazardCount > treasureCount) {
            System.out.println("Player loses! More hazards than treasures.");
        } else {
            System.out.println("Player wins! Claiming treasures...");
            claimTreasures(player.getTreasureBox());

            int finalScore = player.calculateScore();
            System.out.println("Final score: " + finalScore);
        }
    }
}

