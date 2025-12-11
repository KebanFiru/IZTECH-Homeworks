package view;

import penguins.Penguin;
import food.FoodItem;
import model.ITerrainObject;
import terrain.IcyTerrain;
import java.util.List;

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
     */
    public void displaySpecialActionChoice(Penguin penguin, boolean uses) {
        if (uses) {
            System.out.println(penguin.getName() + " chooses to USE its special action.");
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
     */
    public void displayGameOver(List<Penguin> penguins) {
        System.out.println("\n====================================");
        System.out.println("         GAME OVER");
        System.out.println("====================================");
        
        Penguin winner = null;
        int maxWeight = -1;
        
        for (Penguin p : penguins) {
            // Skip removed penguins
            if (p.isRemoved()) {
                System.out.println(p.getName() + " was removed from the game (0 food items)");
                continue;
            }
            
            int totalWeight = p.getTotalFoodWeight();
            
            System.out.println(p.getName() + " collected " + p.getCollectedFoods().size() + 
                    " food items (Total weight: " + totalWeight + " units)");
            
            if (totalWeight > maxWeight) {
                maxWeight = totalWeight;
                winner = p;
            }
        }
        
        if (winner != null && maxWeight > 0) {
            System.out.println("\n🏆 Winner: " + winner.getName() + " with " + maxWeight + " units of food!");
        } else if (maxWeight == 0) {
            System.out.println("\n🤝 It's a tie! No one collected any food.");
        } else {
            System.out.println("\n❌ All penguins were removed! No winner.");
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
