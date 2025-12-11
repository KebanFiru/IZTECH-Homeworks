package view;

import penguins.Penguin;
import model.ITerrainObject;
import terrain.IcyTerrain;
import java.util.List;
import java.util.ArrayList;

/**
 * View class responsible for all display and output operations.
 * Handles formatting and presenting game state to the user.
 * Follows MVC pattern by separating presentation logic from game logic.
 */
public class GameView {
    
    /**
     * Displays the welcome message and game initialization info.
     */
    public void displayWelcome() {
        System.out.println("Welcome to Sliding Penguins Puzzle Game App. An 10x10 icy terrain grid is being generated.");
        System.out.println("Penguins, Hazards, and Food items are also being generated. The initial icy terrain grid:");
    }
    
    /**
     * Displays the current state of the terrain grid.
     * 
     * @param terrain The terrain to display
     */
    public void displayGrid(IcyTerrain terrain) {
        System.out.println("\n    0   1   2   3   4   5   6   7   8   9");
        System.out.println("  +---+---+---+---+---+---+---+---+---+---+");
        
        for (int row = 0; row < IcyTerrain.GRID_SIZE; row++) {
            System.out.print(row + " |");
            for (int col = 0; col < IcyTerrain.GRID_SIZE; col++) {
                ITerrainObject obj = terrain.getObjectAt(row, col);
                if (obj != null) {
                    System.out.print(" " + obj.getNotation() + "|");
                } else {
                    System.out.print("   |");
                }
            }
            System.out.println();
            System.out.println("  +---+---+---+---+---+---+---+---+---+---+");
        }
    }
    
    /**
     * Displays information about all penguins in the game.
     * 
     * @param penguins List of penguins to display
     */
    public void displayPenguinInfo(List<Penguin> penguins) {
        System.out.println("\nPenguins:");
        for (Penguin p : penguins) {
            System.out.println("- " + p.getName() + " (" + p.getType() + ") at position (" + 
                    p.getRow() + ", " + p.getColumn() + ")");
        }
    }
    
    /**
     * Displays the turn header for a penguin.
     * 
     * @param turnNumber The current turn number
     * @param penguin The penguin whose turn it is
     * @param isPlayer Whether this is the player's penguin
     */
    public void displayTurnHeader(int turnNumber, Penguin penguin, boolean isPlayer) {
        System.out.println("\n*** Turn " + turnNumber + " - " + penguin.getName() +
                (isPlayer ? " (Your Penguin):" : ":"));
    }
    
    /**
     * Displays a message that a penguin is stunned.
     * 
     * @param penguin The stunned penguin
     */
    public void displayStunnedMessage(Penguin penguin) {
        System.out.println(penguin.getName() + " is stunned and skips this turn.");
    }
    
    /**
     * Displays the special action choice message.
     * 
     * @param penguin The penguin
     * @param uses Whether the penguin uses the special action
     * @param isAutomatic Whether the special action was automatically triggered (for RockhopperPenguin)
     */
    public void displaySpecialActionChoice(Penguin penguin, boolean uses, boolean isAutomatic) {
        if (uses) {
            if (isAutomatic) {
                System.out.println(penguin.getName() + " will automatically USE its special action.");
            } else {
                System.out.println(penguin.getName() + " chooses to USE its special action.");
            }
        } else {
            System.out.println(penguin.getName() + " does NOT use its special action.");
        }
    }
    
    /**
     * Displays the movement direction choice.
     * 
     * @param penguin The penguin
     * @param direction The direction name
     */
    public void displayMovementChoice(Penguin penguin, String direction) {
        System.out.println(penguin.getName() + " chooses to move " + direction + ".");
    }
    
    /**
     * Displays a message that the grid state has been updated.
     */
    public void displayGridUpdate() {
        System.out.println("New state of the grid:");
    }
    
    /**
     * Displays the game over screen with winner information.
     * 
     * @param penguins List of all penguins
     * @param playerPenguin The player's penguin
     */
    public void displayGameOver(List<Penguin> penguins, Penguin playerPenguin) {
        System.out.println("\n***** GAME OVER *****");
        System.out.println("***** SCOREBOARD FOR THE PENGUINS *****");
        
        // Create a sorted list of penguins by weight (including removed ones)
        List<Penguin> sortedPenguins = new ArrayList<>(penguins);
        sortedPenguins.sort((p1, p2) -> Integer.compare(p2.getTotalFoodWeight(), p1.getTotalFoodWeight()));
        
        String[] places = {"1st", "2nd", "3rd"};
        
        for (int i = 0; i < sortedPenguins.size(); i++) {
            Penguin p = sortedPenguins.get(i);
            String place = i < places.length ? places[i] : (i + 1) + "th";
            
            String playerMarker = (p == playerPenguin) ? " (Your Penguin)" : "";
            System.out.println("* " + place + " place: " + p.getName() + playerMarker + " ");
            
            // Display food items
            if (p.getCollectedFoods().isEmpty()) {
                System.out.println(" |---> Food items: None");
            } else {
                System.out.print(" |---> Food items: ");
                for (int j = 0; j < p.getCollectedFoods().size(); j++) {
                    food.FoodItem f = p.getCollectedFoods().get(j);
                    System.out.print(f.getNotation() + " (" + f.getWeight() + " units)");
                    if (j < p.getCollectedFoods().size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }
            
            System.out.println(" |---> Total weight: " + p.getTotalFoodWeight() + " units");
        }
    }
    
    /**
     * Prompts the user for special action input.
     * 
     * @param penguinName The name of the penguin
     */
    public void promptSpecialAction(String penguinName) {
        System.out.print("Will " + penguinName + " use its special action? Answer with Y or N --> ");
    }
    
    /**
     * Prompts the user for direction input.
     * 
     * @param message The prompt message
     */
    public void promptDirection(String message) {
        System.out.print(message);
    }
    
    /**
     * Displays an invalid input message.
     * 
     * @param message The error message
     */
    public void displayInvalidInput(String message) {
        System.out.println(message);
    }
    
    /**
     * Displays an error message.
     * 
     * @param message The error message
     */
    public void displayError(String message) {
        System.err.println(message);
    }
    
    /**
     * Displays a food collection message.
     * 
     * @param penguinName The penguin's name
     * @param foodType The type of food
     * @param weight The weight of the food
     */
    public void displayFoodCollection(String penguinName, String foodType, int weight) {
        System.out.println(penguinName + " takes the " + foodType + 
                " on the ground. (Weight=" + weight + " units)");
    }
    
    /**
     * Displays a penguin removal message.
     * 
     * @param penguinName The penguin's name
     */
    public void displayPenguinRemoved(String penguinName) {
        System.out.println("*** " + penguinName + " IS REMOVED FROM THE GAME!");
    }
    
    /**
     * Displays a collision message.
     * 
     * @param penguin1Name First penguin's name
     * @param penguin2Name Second penguin's name
     */
    public void displayPenguinCollision(String penguin1Name, String penguin2Name) {
        System.out.println(penguin1Name + " collides with " + penguin2Name + ".");
        System.out.println("Movement is transferred to " + penguin2Name + ".");
    }
    
    /**
     * Displays a generic message.
     * 
     * @param message The message to display
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }
}
