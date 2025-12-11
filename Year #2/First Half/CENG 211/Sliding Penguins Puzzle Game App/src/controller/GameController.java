package controller;

import terrain.IcyTerrain;
import terrain.Direction;
import penguins.Penguin;
import penguins.PenguinType;
import hazards.Hazard;
import hazards.HoleInIce;
import food.FoodItem;
import model.ITerrainObject;
import view.GameView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Controls game flow, setup, user input, and turn management.
 * Handles the main game loop and coordinates between terrain, penguins, user input, and view.
 * Follows MVC pattern: Controller handles logic, View handles display.
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
    private GameView view;  // View layer for display

    /**
     * Constructs a new GameController.
     * Initializes the terrain, scanner for user input, view, and sets the current turn to 1.
     */
    public GameController() {
        terrain = new IcyTerrain();
        scanner = new Scanner(System.in);
        view = new GameView();
        currentTurn = 1;
    }

    /**
     * Starts and runs the main game loop.
     * Initializes the game, displays the initial state, and processes turns for all penguins.
     * Ensures Scanner is properly closed using try-with-resources pattern.
     */
    public void startGame() {
        try {
            view.displayWelcome();

            initializeGame();
            view.displayGrid(terrain);
            view.displayPenguinInfo(terrain.getPenguins());

            for (currentTurn = 1; currentTurn <= MAX_TURNS; currentTurn++) {
                for (Penguin penguin : terrain.getPenguins()) {
                    if (!penguin.isRemoved()) {
                        if (penguin.isStunned()) {
                            view.displayTurnHeader(currentTurn, penguin, penguin == playerPenguin);
                            view.displayStunnedMessage(penguin);
                            penguin.setStunned(false);
                            view.displayGrid(terrain);
                            continue;
                        }
                        processPenguinTurn(penguin);
                    }
                }
            }

            view.displayGameOver(terrain.getPenguins());
        } catch (Exception e) {
            view.displayError("An error occurred during the game: " + e.getMessage());
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
        view.displayTurnHeader(currentTurn, penguin, penguin == playerPenguin);

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

        if (!penguin.hasUsedSpecialAction()) {
            view.displaySpecialActionChoice(penguin, useSpecial);
        }
        view.displayMovementChoice(penguin, getDirectionName(moveDir));
        
        if (useSpecial && !penguin.hasUsedSpecialAction()) {
            penguin.useSpecialAbility(moveDir, terrain);
        } else {
            penguin.move(moveDir, terrain);
        }
        
        view.displayGridUpdate();
        view.displayGrid(terrain);
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
                view.promptSpecialAction(playerPenguin.getName());
                String input = scanner.nextLine().trim().toUpperCase();
                if (input.equals("Y")) return true;
                if (input.equals("N")) return false;
                view.displayInvalidInput("Invalid input. Please enter Y or N.");
            } catch (Exception e) {
                view.displayError("Error reading input: " + e.getMessage());
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
                view.promptDirection("Which direction will " + playerPenguin.getName() + " move? Answer with U (Up), D (Down), L (Left), R (Right) --> ");
                String input = scanner.nextLine().trim().toUpperCase();
                if (!input.isEmpty()) {
                    Direction dir = Direction.fromChar(input.charAt(0));
                    if (dir != null) return dir;
                }
                view.displayInvalidInput("Invalid direction. Please enter U, D, L, or R.");
            } catch (Exception e) {
                view.displayError("Error reading input: " + e.getMessage());
            }
        }
    }

    /**
     * Chooses a direction for an AI-controlled penguin.
     * Priority order:
     * 1. Direction leading to food items
     * 2. Direction towards safe empty spaces
     * 3. Direction towards hazards (except unplugged HoleInIce)
     * 4. Direction towards water (only as last resort)
     * 
     * @param penguin The AI penguin choosing a direction
     * @return The chosen direction for movement
     */
    private Direction chooseAIDirection(Penguin penguin) {
        Direction[] directions = Direction.values();
        List<Direction> foodDirs = new ArrayList<>();
        List<Direction> safeDirs = new ArrayList<>();
        List<Direction> hazardDirs = new ArrayList<>();
        List<Direction> waterDirs = new ArrayList<>();

        for (Direction dir : directions) {
            int[] next = terrain.getNextPosition(penguin.getRow(), penguin.getColumn(), dir);
            
            // If out of bounds, it's water
            if (!terrain.isValidPosition(next[0], next[1])) {
                waterDirs.add(dir);
                continue;
            }
            
            ITerrainObject obj = terrain.getObjectAt(next[0], next[1]);
            
            if (obj instanceof FoodItem) {
                foodDirs.add(dir);
            } else if (obj instanceof HoleInIce) {
                HoleInIce hole = (HoleInIce) obj;
                if (hole.isPlugged()) {
                    safeDirs.add(dir); // Plugged holes are safe
                } else {
                    waterDirs.add(dir); // Unplugged holes are like water
                }
            } else if (obj instanceof Hazard) {
                hazardDirs.add(dir);
            } else {
                safeDirs.add(dir);
            }
        }
        
        // Choose based on priority
        if (!foodDirs.isEmpty()) {
            return foodDirs.get((int) (Math.random() * foodDirs.size()));
        }
        if (!safeDirs.isEmpty()) {
            return safeDirs.get((int) (Math.random() * safeDirs.size()));
        }
        if (!hazardDirs.isEmpty()) {
            return hazardDirs.get((int) (Math.random() * hazardDirs.size()));
        }
        // Last resort: water (or any random direction if all lists are empty)
        if (!waterDirs.isEmpty()) {
            return waterDirs.get((int) (Math.random() * waterDirs.size()));
        }
        // Fallback: return random direction (shouldn't happen but prevents crash)
        return directions[(int) (Math.random() * directions.length)];
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