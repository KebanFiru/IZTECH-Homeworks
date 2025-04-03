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

    public void claimTreasures(TreasureBox treasureBox){
        try {
            while (!treasureBox.isEmpty()) {
                QuestCard card = treasureBox.remove();
                if (card instanceof TreasureCard) {
                    TreasureCard treasureCard = (TreasureCard) card;

                    Treasure treasureType = treasureCard.getTreasure();
                    if (treasureType == null) {
                        continue;
                    }

                    int treasureAmount = treasureCard.getValue();

                    for (int i = 0; i < treasureAmount; i++) {
                        boolean foundTreasure = false;
                        for (int j = 0; j < chest.getCurrentSize(); j++) {
                            Object obj = chest.toArray()[j];
                            if (obj instanceof Treasure) {
                                Treasure chestTreasure = (Treasure) obj;
                                if (chestTreasure.getClass().equals(treasureType.getClass())) {
                                    chest.remove(chestTreasure);
                                    player.getTent().add(chestTreasure);
                                    foundTreasure = true;
                                    break;
                                }
                            }
                        }
                        if (!foundTreasure) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error in claimTreasures: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void play(){
        System.out.println("Starting Incan Gold game with " + numberOfRounds + " rounds...");

        initializeGameComponents();

        for (int round = 1; round <= numberOfRounds; round++) {
            System.out.println("\nRound " + round + ":");

            if (box.getCurrentSize() < 3) {
                System.out.println("Not enough cards in the box to continue. Game ends.");
                break;
            }

            for (int roll = 1; roll <= 3; roll++) {
                if (box.isEmpty()) {
                    System.out.println("No more cards in the box. Ending round early.");
                    break;
                }

                int diceResult = player.rollDice();
                System.out.println("  Roll " + roll + ": " + diceResult);

                diceResult = diceResult % box.getCurrentSize();

                QuestCard drawnCard = box.removeByIndex(diceResult);
                if (drawnCard == null) {
                    System.out.println("  Invalid index. Rolling again.");
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

            int turquoiseCount = 0;
            int obsidianCount = 0;
            int goldCount = 0;

            Object[] tentTreasures = player.getTent().toArray();
            for (int i = 0; i < tentTreasures.length; i++) {
                Object obj = tentTreasures[i];
                if (obj instanceof Turquoise) {
                    turquoiseCount++;
                } else if (obj instanceof Obsidian) {
                    obsidianCount++;
                } else if (obj instanceof Gold) {
                    goldCount++;
                }
            }

            int turquoiseValue = turquoiseCount * 1;  // Turquoise value is 1
            int obsidianValue = obsidianCount * 5;    // Obsidian value is 5
            int goldValue = goldCount * 10;           // Gold value is 10

            System.out.println("\nTreasures collected:");
            System.out.println("Turquoise: " + turquoiseCount + " (total value: " + turquoiseValue + ")");
            System.out.println("Obsidian: " + obsidianCount + " (total value: " + obsidianValue + ")");
            System.out.println("Gold: " + goldCount + " (total value: " + goldValue + ")");
            System.out.println("\nFinal score: " + finalScore);
        }
    }
}

