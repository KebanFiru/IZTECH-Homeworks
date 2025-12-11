package controller;

import terrain.IcyTerrain;
import terrain.Direction;
import penguins.Penguin;
import penguins.PenguinType;
import hazards.Hazard;
import food.FoodItem;
import model.ITerrainObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Controls game flow, setup, user input, display, and turn management.
 * Handles the main game loop and coordinates between terrain, penguins, and user input.
 */
public class GameController {
    /** Total number of turns in the game */
    private static final int MAX_TURNS = 4;
    
    /** Number of penguins in the game */
    private static final int PENGUIN_COUNT = 3;
    
    /** Number of hazards to place on the terrain */
    private static final int HAZARD_COUNT = 15;
    
    /** Number of food items to place on the terrain */
    private static final int FOOD_COUNT = 20;
    
    /** Probability threshold for AI using special action */
    private static final double AI_SPECIAL_ACTION_PROBABILITY = 0.3;
    
    private IcyTerrain terrain;
    private Penguin playerPenguin;
    private Scanner scanner;
    private int currentTurn;

    /**
     * Constructs a new GameController.
     * Initializes the terrain, scanner for user input, and sets the current turn to 1.
     */
    public GameController() {
        terrain = new IcyTerrain();
        scanner = new Scanner(System.in);
        currentTurn = 1;
    }

    /**
     * Starts and runs the main game loop.
     * Initializes the game, displays the initial state, and processes turns for all penguins.
     * Ensures Scanner is properly closed using try-with-resources pattern.
     */
    public void startGame() {
        try {
            System.out.println("Welcome to Sliding Penguins Puzzle Game App. An 10x10 icy terrain grid is being generated.");
            System.out.println("Penguins, Hazards, and Food items are also being generated. The initial icy terrain grid:");

            initializeGame();
            displayGrid();
            displayPenguinInfo();

            for (currentTurn = 1; currentTurn <= MAX_TURNS; currentTurn++) {
                for (Penguin penguin : terrain.getPenguins()) {
                    if (!penguin.isRemoved()) {
                        if (penguin.isStunned()) {
                            System.out.println("\n*** Turn " + currentTurn + " - " + penguin.getName() + ":");
                            System.out.println(penguin.getName() + " is stunned and skips this turn.");
                            penguin.setStunned(false);
                            displayGrid();
                            continue;
                        }
                        processPenguinTurn(penguin);
                    }
                }
            }

            displayGameOver();
        } catch (Exception e) {
            System.err.println("An error occurred during the game: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * Initializes the game by generating penguins, placing hazards, and placing food items.
     */
    private void initializeGame() {
        generatePenguins();
        for (int i = 0; i < HAZARD_COUNT; i++) {
            placeHazard();
        }
        for (int i = 0; i < FOOD_COUNT; i++) {
            placeFood();
        }
    }

    /**
     * Generates penguins with random types and places them on edge positions.
     * One penguin is randomly selected as the player's penguin.
     */
    private void generatePenguins() {
        String[] names = {"P1", "P2", "P3"};
        List<int[]> edgePositions = terrain.getAvailableEdgePositions();

        for (int i = 0; i < PENGUIN_COUNT; i++) {
            PenguinType type = PenguinType.getRandom();
            Penguin penguin = terrain.createPenguin(names[i], type);
            if (edgePositions.isEmpty()) {
                System.err.println("Warning: No available edge positions for penguin " + names[i]);
                continue;
            }
            int[] pos = edgePositions.remove((int) (Math.random() * edgePositions.size()));
            penguin.setPosition(pos[0], pos[1]);
            terrain.placeObject(penguin, pos[0], pos[1]);
            terrain.addPenguin(penguin);
        }
        
        if (!terrain.getPenguins().isEmpty()) {
            playerPenguin = terrain.getPenguins().get((int) (Math.random() * terrain.getPenguins().size()));
        } else {
            throw new IllegalStateException("Failed to create any penguins");
        }
    }

    /**
     * Places a random hazard on an empty position on the terrain.
     * Logs a warning if no empty position is available.
     */
    private void placeHazard() {
        Hazard hazard = Hazard.getRandomHazard();
        int[] pos = terrain.findEmptyPosition(true);
        if (pos != null) {
            hazard.setPosition(pos[0], pos[1]);
            terrain.placeObject(hazard, pos[0], pos[1]);
            terrain.addHazard(hazard);
        } else {
            System.err.println("Warning: No available position to place hazard");
        }
    }

    /**
     * Places a food item on an empty position on the terrain.
     * Logs a warning if no empty position is available.
     */
    private void placeFood() {
        FoodItem food = new FoodItem();
        int[] pos = terrain.findEmptyPosition(false);
        if (pos != null) {
            food.setPosition(pos[0], pos[1]);
            terrain.placeObject(food, pos[0], pos[1]);
            terrain.addFoodItem(food);
        } else {
            System.err.println("Warning: No available position to place food");
        }
    }

    /**
     * Processes a single penguin's turn.
     * Handles both player and AI penguin actions, including special ability usage and movement.
     * 
     * @param penguin The penguin whose turn is being processed
     */
    private void processPenguinTurn(Penguin penguin) {
        System.out.println("\n*** Turn " + currentTurn + " - " + penguin.getName() +
                (penguin == playerPenguin ?  " (Your Penguin):" : ":"));

        boolean useSpecial = false;
        Direction moveDir;

        if (penguin == playerPenguin) {
            if (!penguin.hasUsedSpecialAction()) {
                useSpecial = askPlayerSpecialAction();
            }
            moveDir = askPlayerDirection("Which direction will " + penguin.getName() + " move?  ");
        } else {
            if (!penguin.hasUsedSpecialAction()) {
                useSpecial = Math.random() < AI_SPECIAL_ACTION_PROBABILITY;
            }
            moveDir = chooseAIDirection(penguin);
        }

        System.out.println(penguin.getName() + (useSpecial ? " uses SPECIAL action" : " slides normally") +
                " " + getDirectionName(moveDir) + ".");
        if (useSpecial) {
            penguin.useSpecialAbility(moveDir, terrain);
        } else {
            penguin.move(moveDir, terrain);
        }
        displayGrid();
    }

    /**
     * Asks the player whether to use the penguin's special action.
     * Validates input to ensure it's either 'Y' or 'N'.
     * 
     * @return true if player chooses to use special action, false otherwise
     */
    private boolean askPlayerSpecialAction() {
        while (true) {
            try {
                System.out.print("Will " + playerPenguin.getName() + " use its special action? Answer with Y or N --> ");
                String input = scanner.nextLine().trim().toUpperCase();
                if (input.equals("Y")) return true;
                if (input.equals("N")) return false;
                System.out.println("Invalid input. Please enter Y or N.");
            } catch (Exception e) {
                System.err.println("Error reading input: " + e.getMessage());
            }
        }
    }

    /**
     * Asks the player to choose a direction for movement.
     * Validates input to ensure it's a valid direction (U, D, L, or R).
     * 
     * @param prompt The prompt message to display to the player
     * @return The chosen direction
     */
    private Direction askPlayerDirection(String prompt) {
        while (true) {
            try {
                System.out.print("Which direction will " + playerPenguin.getName() + " move? Answer with U (Up), D (Down), L (Left), R (Right) --> ");
                String input = scanner.nextLine().trim().toUpperCase();
                if (!input.isEmpty()) {
                    Direction dir = Direction.fromChar(input.charAt(0));
                    if (dir != null) return dir;
                }
                System.out.println("Invalid direction. Please enter U, D, L, or R.");
            } catch (Exception e) {
                System.err.println("Error reading input: " + e.getMessage());
            }
        }
    }

    /**
     * Chooses a direction for an AI-controlled penguin.
     * Prioritizes: 1) food items, 2) safe empty spaces, 3) hazards, 4) random direction.
     * 
     * @param penguin The AI penguin choosing a direction
     * @return The chosen direction for movement
     */
    private Direction chooseAIDirection(Penguin penguin) {
        Direction[] directions = Direction.values();
        List<Direction> foodDirs = new ArrayList<>();
        List<Direction> hazardDirs = new ArrayList<>();
        List<Direction> safeDirs = new ArrayList<>();

        for (Direction dir : directions) {
            int[] next = terrain.getNextPosition(penguin.getRow(), penguin.getColumn(), dir);
            if (!terrain.isValidPosition(next[0], next[1])) continue;
            ITerrainObject obj = terrain.getObjectAt(next[0], next[1]);
            if (obj instanceof FoodItem) foodDirs.add(dir);
            else if (obj instanceof Hazard) hazardDirs.add(dir);
            else safeDirs.add(dir);
        }
        if (!foodDirs.isEmpty()) return foodDirs.get((int) (Math.random() * foodDirs.size()));
        if (!safeDirs.isEmpty()) return safeDirs.get((int) (Math.random() * safeDirs.size()));
        if (!hazardDirs.isEmpty()) return hazardDirs.get((int) (Math.random() * hazardDirs.size()));
        return directions[(int) (Math.random() * directions.length)];
    }

    /**
     * Displays the current state of the terrain grid.
     * Shows all objects (penguins, hazards, food items) with their notations.
     */
    private void displayGrid() {
        System.out.println("-------------------------------------------------------------");
        for (int y = 0; y < terrain.getGridSize(); y++) {
            System.out.print("|");
            for (int x = 0; x < terrain.getGridSize(); x++) {
                ITerrainObject obj = terrain.getObjectAt(x, y);
                String notation = obj == null ? "  " : obj.getNotation();
                System.out.print(" " + String.format("%-2s", notation) + " |");
            }
            System.out.println();
            System.out.println("-------------------------------------------------------------");
        }
    }

    /**
     * Displays information about all penguins in the game.
     * Highlights which penguin is controlled by the player.
     */
    private void displayPenguinInfo() {
        System.out.println("These are the penguins on the icy terrain:");
        for (int i = 0; i < terrain.getPenguins().size(); i++) {
            Penguin penguin = terrain.getPenguins().get(i);
            String penguinTypeName = penguin.getClass().getSimpleName();
            // Format: "Penguin 1 (P1): King Penguin"
            String formattedType = penguinTypeName.replace("Penguin", " Penguin");
            System.out.println("- Penguin " + (i + 1) + " (" + penguin.getName() + "): " + 
                    formattedType + 
                    (penguin == playerPenguin ? " ---> YOUR PENGUIN" : ""));
        }
    }

    /**
     * Displays the game over screen with final scoreboard.
     * Ranks penguins by total food weight collected and shows their collected items.
     */
    private void displayGameOver() {
        System.out.println("\n***** GAME OVER *****");
        System.out.println("***** SCOREBOARD FOR THE PENGUINS *****");

        List<Penguin> sortedPenguins = new ArrayList<>(terrain.getPenguins());
        sortedPenguins.sort((p1, p2) -> Integer.compare(p2.getTotalFoodWeight(), p1.getTotalFoodWeight()));

        String[] places = {"1st", "2nd", "3rd"};
        for (int i = 0; i < sortedPenguins.size(); i++) {
            Penguin p = sortedPenguins.get(i);
            System.out.println("* " + places[i] + " place: " + p.getName() +
                    (p == playerPenguin ? " (Your Penguin)" : ""));
            System.out.print("|---> Food items: ");
            List<FoodItem> foods = p.getCollectedFoods();
            for (int j = 0; j < foods.size(); j++) {
                FoodItem f = foods.get(j);
                System.out.print(f.getNotation() + " (" + f.getWeight() + " units)");
                if (j < foods.size() - 1) System.out.print(", ");
            }
            System.out.println();
            System.out.println("|---> Total weight: " + p.getTotalFoodWeight() + " units");
        }
    }

    /**
     * Converts a Direction enum to its display name.
     * 
     * @param dir The direction to convert
     * @return The string representation of the direction
     */
    private String getDirectionName(Direction dir) {
        switch (dir) {
            case UP: return "UP";
            case DOWN: return "DOWN";
            case LEFT: return "LEFT";
            case RIGHT: return "RIGHT";
            default: return "";
        }
    }
}