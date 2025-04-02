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

                    System.out.println("Processing treasure card: " + treasureCard);

                    Treasure treasureType = treasureCard.getTreasure();
                    if (treasureType == null) {
                        System.out.println("Warning: Treasure type is null for card " + treasureCard);
                        continue;
                    }

                    int treasureAmount = treasureCard.getValue();
                    System.out.println("Treasure type: " + treasureType.getClass().getSimpleName() + ", amount: " + treasureAmount);

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
                            System.out.println("Not enough treasures of type " + treasureType.getClass().getSimpleName() + " in the chest.");
                            break;
                        }
                    }
                } else {
                    System.out.println("Warning: Non-treasure card found in treasure box: " + card);
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
            System.out.println("Final score: " + finalScore);
        }
    }
}

