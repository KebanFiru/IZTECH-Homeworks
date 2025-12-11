package src.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import src.food.FoodItem;
import src.hazards.HazardType;
import src.hazards.HoleInIce;
import src.hazards.IHazard;
import src.hazards.LightIceBlock;
import src.hazards.SeaLion;
import src.model.IMovable;
import src.model.ITerrainObject;
import src.penguins.EmperorPenguin;
import src.penguins.KingPenguin;
import src.penguins.Penguin;
import src.penguins.RockhopperPenguin;
import src.penguins.RoyalPenguin;
import src.terrain.Direction;
import src.terrain.IcyTerrain;
import src.util.InputHelper;

/**
 * GameController Class
 * 
 * This class manages the game logic for the Sliding Penguins Puzzle Game.
 * It handles:
 * - Turn management (4 turns per penguin, order: P1-P2-P3)
 * - Player input and AI decision making
 * - Sliding mechanics and collision handling
 * - Special ability usage for each penguin type
 * - Scoring and game end conditions
 */
public class GameController {
    
    // Number of turns each penguin gets
    private static final int TOTAL_TURNS = 4;
    
    // Probability for AI to use special ability (30%)
    private static final double AI_ABILITY_CHANCE = 0.30;
    
    // The icy terrain grid
    private final IcyTerrain terrain;
    
    // Input helper for user interaction
    private final InputHelper inputHelper;
    
    // Random generator for AI decisions
    private final Random random;
    
    // Index of the player's penguin (0, 1, or 2)
    private final int playerPenguinIndex;
    
    // Current turn number (1-4)
    private int currentTurn;
    
    /**
     * Constructor for GameController.
     * 
     * @param terrain the IcyTerrain instance (must not be null)
     * @param inputHelper the InputHelper for user input (must not be null)
     * @throws IllegalArgumentException if terrain or inputHelper is null
     */
    public GameController(IcyTerrain terrain, InputHelper inputHelper) {
        if (terrain == null) {
            throw new IllegalArgumentException("Terrain cannot be null");
        }
        if (inputHelper == null) {
            throw new IllegalArgumentException("InputHelper cannot be null");
        }
        this.terrain = terrain;
        this.inputHelper = inputHelper;
        this.random = new Random();
        this.currentTurn = 1;
        
        // Randomly assign player to one of the three penguins
        this.playerPenguinIndex = random.nextInt(3);
    }
    
    /**
     * Gets the player's penguin.
     * 
     * @return the player's Penguin
     */
    public Penguin getPlayerPenguin() {
        return terrain.getPenguins().get(playerPenguinIndex);
    }
    
    /**
     * Checks if a penguin is controlled by the player.
     * 
     * @param penguin the penguin to check
     * @return true if this is the player's penguin
     */
    public boolean isPlayerPenguin(Penguin penguin) {
        return terrain.getPenguins().indexOf(penguin) == playerPenguinIndex;
    }
    
    /**
     * Displays information about all penguins at the start of the game.
     */
    public void displayPenguinInfo() {
        System.out.println("\nThese are the penguins on the icy terrain:");
        List<Penguin> penguins = terrain.getPenguins();
        
        for (int i = 0; i < penguins.size(); i++) {
            Penguin penguin = penguins.get(i);
            String playerMarker = (i == playerPenguinIndex) ? " ---> YOUR PENGUIN" : "";
            System.out.println("- Penguin " + (i + 1) + " (" + penguin.getName() + "): " 
                    + penguin.getType().getDisplayName() + playerMarker);
        }
    }
    
    /**
     * Runs the main game loop.
     * Each turn, penguins move in order P1-P2-P3.
     * The game lasts for 4 turns.
     */
    public void runGame() {
        // Run 4 turns
        for (currentTurn = 1; currentTurn <= TOTAL_TURNS; currentTurn++) {
            List<Penguin> penguins = terrain.getPenguins();
            
            // Each penguin takes a turn in order P1, P2, P3
            for (int i = 0; i < penguins.size(); i++) {
                Penguin penguin = penguins.get(i);
                
                // Skip removed penguins
                if (penguin.isRemoved()) {
                    continue;
                }
                
                // Handle stunned penguins
                if (penguin.isStunned()) {
                    displayTurnHeader(penguin);
                    System.out.println(penguin.getName() + "'s turn is skipped due to being stunned.");
                    penguin.setStunned(false);
                    System.out.println("\nNew state of the grid:");
                    displayGrid();
                    continue;
                }
                
                // Execute the penguin's turn
                executeTurn(penguin);
            }
        }
        
        // Game over - display results
        displayGameResults();
    }
    
    /**
     * Displays the turn header.
     * 
     * @param penguin the penguin whose turn it is
     */
    private void displayTurnHeader(Penguin penguin) {
        String playerMarker = isPlayerPenguin(penguin) ? " (Your Penguin)" : "";
        System.out.println("\n*** Turn " + currentTurn + " – " + penguin.getName() + playerMarker + ":");
    }
    
    /**
     * Executes a single penguin's turn.
     * 
     * @param penguin the penguin taking the turn
     */
    private void executeTurn(Penguin penguin) {
        displayTurnHeader(penguin);
        
        if (isPlayerPenguin(penguin)) {
            executePlayerTurn(penguin);
        } else {
            executeAITurn(penguin);
        }
        
        // Display new grid state after turn
        System.out.println("\nNew state of the grid:");
        displayGrid();
    }
    
    /**
     * Displays the current grid state.
     */
    private void displayGrid() {
        terrain.displayGrid();
    }
    
    // ==================== Player Turn Logic ====================
    
    /**
     * Executes the player's turn.
     * Asks for ability usage and direction choice.
     * 
     * @param penguin the player's penguin
     */
    private void executePlayerTurn(Penguin penguin) {
        boolean useAbility = false;
        
        // Ask about ability usage if not already used
        if (!penguin.isAbilityUsed()) {
            useAbility = askPlayerAbilityUsage(penguin);
        }
        
        // Handle RoyalPenguin pre-slide step
        if (useAbility && penguin instanceof RoyalPenguin) {
            handleRoyalPenguinStep((RoyalPenguin) penguin, true);
            penguin.setAbilityUsed(true);
        }
        
        // Ask for direction
        Direction direction = askPlayerDirection(penguin);
        
        // Handle RockhopperPenguin jump preparation
        boolean prepareJump = false;
        if (useAbility && penguin instanceof RockhopperPenguin) {
            prepareJump = true;
            penguin.setAbilityUsed(true);
            System.out.println(penguin.getName() + " prepares to jump over a hazard.");
        }
        
        // Execute the slide
        if (useAbility && (penguin instanceof KingPenguin || penguin instanceof EmperorPenguin)) {
            penguin.setAbilityUsed(true);
            executeSlideWithStop(penguin, direction);
        } else if (prepareJump) {
            executeSlideWithJump(penguin, direction);
        } else {
            executeSlide(penguin, direction);
        }
    }
    
    /**
     * Asks the player if they want to use their special ability.
     * 
     * @param penguin the player's penguin
     * @return true if player wants to use ability
     */
    private boolean askPlayerAbilityUsage(Penguin penguin) {
        System.out.print("Will " + penguin.getName() + " use its special action? Answer with Y or N --> ");
        
        while (true) {
            String input = inputHelper.readLineUpperCase();
            if (input.equals("Y") || input.equals("YES")) {
                return true;
            } else if (input.equals("N") || input.equals("NO")) {
                return false;
            }
            System.out.print("Invalid input. Please enter Y or N --> ");
        }
    }
    
    /**
     * Asks the player for a direction to move.
     * 
     * @param penguin the player's penguin
     * @return the chosen Direction
     */
    private Direction askPlayerDirection(Penguin penguin) {
        System.out.print("Which direction will " + penguin.getName() 
                + " move? Answer with U (Up), D (Down), L (Left), R (Right) --> ");
        
        while (true) {
            String input = inputHelper.readLineUpperCase();
            Direction direction = Direction.fromInput(input);
            
            if (direction != null) {
                return direction;
            }
            System.out.print("Invalid input. Please enter U, D, L, or R --> ");
        }
    }
    
    /**
     * Handles the RoyalPenguin's step ability.
     * Allows moving one square in any direction without sliding.
     * 
     * @param penguin the RoyalPenguin
     * @param isPlayer true if this is the player's penguin
     */
    private void handleRoyalPenguinStep(RoyalPenguin penguin, boolean isPlayer) {
        Direction stepDirection;
        
        if (isPlayer) {
            System.out.print("Which direction will " + penguin.getName() 
                    + " step? Answer with U (Up), D (Down), L (Left), R (Right) --> ");
            
            while (true) {
                String input = inputHelper.readLineUpperCase();
                stepDirection = Direction.fromInput(input);
                
                if (stepDirection != null) {
                    break;
                }
                System.out.print("Invalid input. Please enter U, D, L, or R --> ");
            }
        } else {
            // AI chooses a safe step direction
            stepDirection = chooseAISafeStepDirection(penguin);
        }
        
        // Calculate new position
        int newRow = penguin.getRow() + stepDirection.getRowDelta();
        int newCol = penguin.getColumn() + stepDirection.getColumnDelta();
        
        System.out.println(penguin.getName() + " moves one square to the " + stepDirection.getDisplayName() + ".");
        
        // Check if stepping off the grid
        if (!terrain.isValidPosition(newRow, newCol)) {
            System.out.println(penguin.getName() + " falls into the water!");
            System.out.println("*** " + penguin.getName() + " IS REMOVED FROM THE GAME!");
            terrain.removePenguin(penguin);
            return;
        }
        
        // Check what's at the new position
        ITerrainObject objectAtNew = terrain.getObjectAt(newRow, newCol);
        
        if (objectAtNew != null) {
            // Handle stepping onto something
            if (objectAtNew instanceof FoodItem) {
                FoodItem food = (FoodItem) objectAtNew;
                // Remove food first before moving penguin
                penguin.collectFood(food);
                food.setRemoved(true);
                terrain.removeObjectAt(newRow, newCol);
                System.out.println(penguin.getName() + " takes the " + food.getDisplayName() 
                        + " on the ground. (Weight=" + food.getWeight() + " units)");
            } else if (objectAtNew instanceof IHazard) {
                // Stepped onto a hazard - handle accordingly
                handleHazardCollision(penguin, (IHazard) objectAtNew, stepDirection);
                return;
            } else if (objectAtNew instanceof Penguin) {
                // Cannot step onto another penguin - stay in place
                System.out.println(penguin.getName() + " cannot step there - another penguin is in the way.");
                return;
            }
        }
        
        // Move the penguin
        terrain.moveObject(penguin, newRow, newCol);
    }
    
    // ==================== AI Turn Logic ====================
    
    /**
     * Executes an AI penguin's turn.
     * AI makes decisions based on prioritization rules.
     * 
     * @param penguin the AI penguin
     */
    private void executeAITurn(Penguin penguin) {
        boolean useAbility = false;
        
        // Decide if AI will use ability (30% chance, except for Rockhopper)
        if (!penguin.isAbilityUsed() && !(penguin instanceof RockhopperPenguin)) {
            useAbility = random.nextDouble() < AI_ABILITY_CHANCE;
        }
        
        // Handle RoyalPenguin pre-slide step
        if (useAbility && penguin instanceof RoyalPenguin) {
            System.out.println(penguin.getName() + " chooses to USE its special action.");
            handleRoyalPenguinStep((RoyalPenguin) penguin, false);
            penguin.setAbilityUsed(true);
            
            // Check if penguin was removed during step
            if (penguin.isRemoved()) {
                return;
            }
        } else if (!useAbility && !(penguin instanceof RockhopperPenguin)) {
            System.out.println(penguin.getName() + " does NOT use its special action.");
        }
        
        // Choose direction based on AI priority
        Direction direction = chooseAIDirection(penguin);
        System.out.println(penguin.getName() + " chooses to move to the " + direction.getDisplayName() + ".");
        
        // Check if Rockhopper should use ability (first time moving toward hazard)
        boolean prepareJump = false;
        if (penguin instanceof RockhopperPenguin && !penguin.isAbilityUsed()) {
            if (hasHazardInPath(penguin, direction)) {
                System.out.println(penguin.getName() + " will automatically USE its special action.");
                prepareJump = true;
                penguin.setAbilityUsed(true);
            }
        }
        
        // Execute the slide
        if (useAbility && (penguin instanceof KingPenguin || penguin instanceof EmperorPenguin)) {
            penguin.setAbilityUsed(true);
            System.out.println(penguin.getName() + " chooses to USE its special action.");
            executeSlideWithStop(penguin, direction);
        } else if (prepareJump) {
            executeSlideWithJump(penguin, direction);
        } else {
            executeSlide(penguin, direction);
        }
    }
    
    /**
     * Chooses a direction for AI penguin based on priority rules:
     * 1. Direction leading to food
     * 2. Direction leading to hazard (except HoleInIce)
     * 3. Direction leading to water (last resort)
     * 
     * @param penguin the AI penguin
     * @return the chosen direction
     */
    private Direction chooseAIDirection(Penguin penguin) {
        List<Direction> foodDirections = new ArrayList<>();
        List<Direction> hazardDirections = new ArrayList<>();
        List<Direction> waterDirections = new ArrayList<>();
        
        for (Direction dir : Direction.values()) {
            PathResult result = analyzePath(penguin, dir);
            
            if (result.hasFood) {
                foodDirections.add(dir);
            } else if (result.hasHazard && !result.endsInHole) {
                hazardDirections.add(dir);
            } else {
                waterDirections.add(dir);
            }
        }
        
        // Priority 1: Direction with food
        if (!foodDirections.isEmpty()) {
            return foodDirections.get(random.nextInt(foodDirections.size()));
        }
        
        // Priority 2: Direction with hazard (not HoleInIce as end point)
        if (!hazardDirections.isEmpty()) {
            return hazardDirections.get(random.nextInt(hazardDirections.size()));
        }
        
        // Priority 3: Any direction (water/edge)
        if (!waterDirections.isEmpty()) {
            return waterDirections.get(random.nextInt(waterDirections.size()));
        }
        
        // Fallback: random direction
        Direction[] dirs = Direction.values();
        return dirs[random.nextInt(dirs.length)];
    }
    
    /**
     * Chooses a safe step direction for AI RoyalPenguin.
     * Avoids hazards and water if possible.
     * 
     * @param penguin the RoyalPenguin
     * @return a safe direction, or any direction if none are safe
     */
    private Direction chooseAISafeStepDirection(Penguin penguin) {
        List<Direction> safeDirections = new ArrayList<>();
        List<Direction> hazardDirections = new ArrayList<>();
        List<Direction> allDirections = new ArrayList<>();
        
        for (Direction dir : Direction.values()) {
            int newRow = penguin.getRow() + dir.getRowDelta();
            int newCol = penguin.getColumn() + dir.getColumnDelta();
            
            allDirections.add(dir);
            
            if (!terrain.isValidPosition(newRow, newCol)) {
                // Would fall into water
                continue;
            }
            
            ITerrainObject obj = terrain.getObjectAt(newRow, newCol);
            
            if (obj == null || obj instanceof FoodItem) {
                safeDirections.add(dir);
            } else if (obj instanceof IHazard) {
                hazardDirections.add(dir);
            }
        }
        
        // Prefer safe directions
        if (!safeDirections.isEmpty()) {
            return safeDirections.get(random.nextInt(safeDirections.size()));
        }
        
        // Then hazard directions
        if (!hazardDirections.isEmpty()) {
            return hazardDirections.get(random.nextInt(hazardDirections.size()));
        }
        
        // Last resort: any direction (including water)
        return allDirections.get(random.nextInt(allDirections.size()));
    }
    
    /**
     * Checks if there's a hazard in the path for Rockhopper ability activation.
     * 
     * @param penguin the penguin
     * @param direction the direction to check
     * @return true if there's a hazard in the path
     */
    private boolean hasHazardInPath(Penguin penguin, Direction direction) {
        int row = penguin.getRow();
        int col = penguin.getColumn();
        
        while (true) {
            row += direction.getRowDelta();
            col += direction.getColumnDelta();
            
            if (!terrain.isValidPosition(row, col)) {
                return false;
            }
            
            ITerrainObject obj = terrain.getObjectAt(row, col);
            
            if (obj instanceof IHazard) {
                return true;
            } else if (obj instanceof FoodItem) {
                return false; // Would stop at food first
            } else if (obj instanceof Penguin) {
                return false; // Would stop at penguin first
            }
        }
    }
    
    /**
     * Helper class to store path analysis results.
     */
    private class PathResult {
        boolean hasFood = false;
        boolean hasHazard = false;
        boolean endsInHole = false;
    }
    
    /**
     * Analyzes a path in the given direction.
     * 
     * @param penguin the penguin
     * @param direction the direction to analyze
     * @return PathResult with analysis data
     */
    private PathResult analyzePath(Penguin penguin, Direction direction) {
        PathResult result = new PathResult();
        
        int row = penguin.getRow();
        int col = penguin.getColumn();
        
        while (true) {
            row += direction.getRowDelta();
            col += direction.getColumnDelta();
            
            // Check if off grid - would fall into water
            if (!terrain.isValidPosition(row, col)) {
                return result;
            }
            
            ITerrainObject obj = terrain.getObjectAt(row, col);
            
            if (obj == null) {
                continue; // Empty square, keep going
            }
            
            if (obj instanceof FoodItem) {
                result.hasFood = true;
                return result;
            }
            
            if (obj instanceof Penguin) {
                // Would stop before this penguin
                return result;
            }
            
            if (obj instanceof IHazard) {
                IHazard hazard = (IHazard) obj;
                result.hasHazard = true;
                
                if (hazard instanceof HoleInIce) {
                    HoleInIce hole = (HoleInIce) hazard;
                    if (!hole.isPlugged()) {
                        result.endsInHole = true;
                    }
                }
                return result;
            }
        }
    }
    
    // ==================== Sliding Mechanics ====================
    
    /**
     * Executes a normal slide without special abilities.
     * 
     * @param penguin the penguin sliding
     * @param direction the direction to slide
     */
    private void executeSlide(Penguin penguin, Direction direction) {
        int row = penguin.getRow();
        int col = penguin.getColumn();
        
        while (true) {
            int nextRow = row + direction.getRowDelta();
            int nextCol = col + direction.getColumnDelta();
            
            // Check if next position is off the grid (water)
            if (!terrain.isValidPosition(nextRow, nextCol)) {
                // Move to edge then fall into water
                terrain.moveObject(penguin, row, col);
                System.out.println(penguin.getName() + " falls into the water!");
                System.out.println("*** " + penguin.getName() + " IS REMOVED FROM THE GAME!");
                terrain.removePenguin(penguin);
                return;
            }
            
            ITerrainObject nextObj = terrain.getObjectAt(nextRow, nextCol);
            
            // Empty square - continue sliding
            if (nextObj == null) {
                row = nextRow;
                col = nextCol;
                continue;
            }
            
            // Food item - collect and stop
            if (nextObj instanceof FoodItem) {
                FoodItem food = (FoodItem) nextObj;
                // Remove food first, then move penguin to that position
                penguin.collectFood(food);
                food.setRemoved(true);
                terrain.removeObjectAt(nextRow, nextCol);
                terrain.moveObject(penguin, nextRow, nextCol);
                System.out.println(penguin.getName() + " takes the " + food.getDisplayName() 
                        + " on the ground. (Weight=" + food.getWeight() + " units)");
                return;
            }
            
            // Another penguin - stop before and transfer movement
            if (nextObj instanceof Penguin) {
                Penguin otherPenguin = (Penguin) nextObj;
                terrain.moveObject(penguin, row, col);
                System.out.println(penguin.getName() + " collides with " + otherPenguin.getName() + ".");
                System.out.println(otherPenguin.getName() + " starts sliding in the same direction.");
                
                // Transfer movement to other penguin
                executeSlide(otherPenguin, direction);
                return;
            }
            
            // Hazard - handle collision
            if (nextObj instanceof IHazard) {
                // Move to position before hazard first
                terrain.moveObject(penguin, row, col);
                handleHazardCollision(penguin, (IHazard) nextObj, direction);
                return;
            }
            
            // Default: move to current position
            row = nextRow;
            col = nextCol;
        }
    }
    
    /**
     * Executes a slide with King/Emperor penguin's stop ability.
     * 
     * @param penguin the penguin sliding
     * @param direction the direction to slide
     */
    private void executeSlideWithStop(Penguin penguin, Direction direction) {
        int stopSquare = 0;
        
        if (penguin instanceof KingPenguin) {
            stopSquare = 5;
        } else if (penguin instanceof EmperorPenguin) {
            stopSquare = 3;
        }
        
        int row = penguin.getRow();
        int col = penguin.getColumn();
        int squaresMoved = 0;
        
        while (true) {
            int nextRow = row + direction.getRowDelta();
            int nextCol = col + direction.getColumnDelta();
            
            // Check if next position is off the grid (water)
            if (!terrain.isValidPosition(nextRow, nextCol)) {
                terrain.moveObject(penguin, row, col);
                System.out.println(penguin.getName() + " falls into the water!");
                System.out.println("*** " + penguin.getName() + " IS REMOVED FROM THE GAME!");
                terrain.removePenguin(penguin);
                return;
            }
            
            ITerrainObject nextObj = terrain.getObjectAt(nextRow, nextCol);
            
            // Empty square - continue sliding
            if (nextObj == null) {
                row = nextRow;
                col = nextCol;
                squaresMoved++;
                
                // Check if should stop using ability
                if (squaresMoved == stopSquare) {
                    terrain.moveObject(penguin, row, col);
                    System.out.println(penguin.getName() + " stops at an empty square using its special action.");
                    return;
                }
                continue;
            }
            
            // Food item - collect and stop
            if (nextObj instanceof FoodItem) {
                FoodItem food = (FoodItem) nextObj;
                // Remove food first, then move penguin to that position
                penguin.collectFood(food);
                food.setRemoved(true);
                terrain.removeObjectAt(nextRow, nextCol);
                terrain.moveObject(penguin, nextRow, nextCol);
                System.out.println(penguin.getName() + " takes the " + food.getDisplayName() 
                        + " on the ground. (Weight=" + food.getWeight() + " units)");
                return;
            }
            
            // Another penguin - stop before and transfer movement
            if (nextObj instanceof Penguin) {
                Penguin otherPenguin = (Penguin) nextObj;
                terrain.moveObject(penguin, row, col);
                System.out.println(penguin.getName() + " collides with " + otherPenguin.getName() + ".");
                System.out.println(otherPenguin.getName() + " starts sliding in the same direction.");
                executeSlide(otherPenguin, direction);
                return;
            }
            
            // Hazard - handle collision
            if (nextObj instanceof IHazard) {
                terrain.moveObject(penguin, row, col);
                handleHazardCollision(penguin, (IHazard) nextObj, direction);
                return;
            }
            
            row = nextRow;
            col = nextCol;
            squaresMoved++;
            
            if (squaresMoved == stopSquare) {
                terrain.moveObject(penguin, row, col);
                System.out.println(penguin.getName() + " stops at an empty square using its special action.");
                return;
            }
        }
    }
    
    /**
     * Executes a slide with Rockhopper penguin's jump ability.
     * 
     * @param penguin the penguin sliding
     * @param direction the direction to slide
     */
    private void executeSlideWithJump(Penguin penguin, Direction direction) {
        RockhopperPenguin rockhopper = (RockhopperPenguin) penguin;
        
        int row = penguin.getRow();
        int col = penguin.getColumn();
        
        while (true) {
            int nextRow = row + direction.getRowDelta();
            int nextCol = col + direction.getColumnDelta();
            
            // Check if next position is off the grid (water)
            if (!terrain.isValidPosition(nextRow, nextCol)) {
                terrain.moveObject(penguin, row, col);
                System.out.println(penguin.getName() + " falls into the water!");
                System.out.println("*** " + penguin.getName() + " IS REMOVED FROM THE GAME!");
                terrain.removePenguin(penguin);
                return;
            }
            
            ITerrainObject nextObj = terrain.getObjectAt(nextRow, nextCol);
            
            // Empty square - continue sliding
            if (nextObj == null) {
                row = nextRow;
                col = nextCol;
                continue;
            }
            
            // Food item - collect and stop
            if (nextObj instanceof FoodItem) {
                FoodItem food = (FoodItem) nextObj;
                // Remove food first, then move penguin to that position
                penguin.collectFood(food);
                food.setRemoved(true);
                terrain.removeObjectAt(nextRow, nextCol);
                terrain.moveObject(penguin, nextRow, nextCol);
                System.out.println(penguin.getName() + " takes the " + food.getDisplayName() 
                        + " on the ground. (Weight=" + food.getWeight() + " units)");
                return;
            }
            
            // Another penguin - stop before and transfer movement
            if (nextObj instanceof Penguin) {
                Penguin otherPenguin = (Penguin) nextObj;
                terrain.moveObject(penguin, row, col);
                System.out.println(penguin.getName() + " collides with " + otherPenguin.getName() + ".");
                System.out.println(otherPenguin.getName() + " starts sliding in the same direction.");
                executeSlide(otherPenguin, direction);
                return;
            }
            
            // Hazard - try to jump over it
            if (nextObj instanceof IHazard) {
                IHazard hazard = (IHazard) nextObj;
                
                if (rockhopper.canJump()) {
                    // Check if landing square is empty
                    int landRow = nextRow + direction.getRowDelta();
                    int landCol = nextCol + direction.getColumnDelta();
                    
                    if (!terrain.isValidPosition(landRow, landCol)) {
                        // Would jump into water
                        System.out.println(penguin.getName() + " jumps over " + hazard.getHazardName() 
                                + " but falls into the water!");
                        System.out.println("*** " + penguin.getName() + " IS REMOVED FROM THE GAME!");
                        terrain.removePenguin(penguin);
                        return;
                    }
                    
                    ITerrainObject landObj = terrain.getObjectAt(landRow, landCol);
                    
                    if (landObj == null) {
                        // Successfully jump over
                        System.out.println(penguin.getName() + " jumps over " + hazard.getHazardName() + " in its path.");
                        rockhopper.useJump();
                        row = landRow;
                        col = landCol;
                        continue;
                    } else if (landObj instanceof FoodItem) {
                        // Jump and collect food
                        System.out.println(penguin.getName() + " jumps over " + hazard.getHazardName() + " in its path.");
                        rockhopper.useJump();
                        FoodItem food = (FoodItem) landObj;
                        // Remove food first, then move penguin to that position
                        penguin.collectFood(food);
                        food.setRemoved(true);
                        terrain.removeObjectAt(landRow, landCol);
                        terrain.moveObject(penguin, landRow, landCol);
                        System.out.println(penguin.getName() + " takes the " + food.getDisplayName() 
                                + " on the ground. (Weight=" + food.getWeight() + " units)");
                        return;
                    } else {
                        // Landing square not empty - fail to jump
                        System.out.println(penguin.getName() + " fails to jump - landing square is not empty.");
                        terrain.moveObject(penguin, row, col);
                        handleHazardCollision(penguin, hazard, direction);
                        return;
                    }
                } else {
                    // Already used jump - normal hazard collision
                    terrain.moveObject(penguin, row, col);
                    handleHazardCollision(penguin, hazard, direction);
                    return;
                }
            }
            
            row = nextRow;
            col = nextCol;
        }
    }
    
    // ==================== Hazard Collision Handling ====================
    
    /**
     * Handles collision between a penguin and a hazard.
     * 
     * @param penguin the penguin that collided
     * @param hazard the hazard collided with
     * @param direction the direction the penguin was moving
     */
    private void handleHazardCollision(Penguin penguin, IHazard hazard, Direction direction) {
        HazardType hazardType = hazard.getHazardType();
        
        switch (hazardType) {
            case LIGHT_ICE_BLOCK:
                handleLightIceBlockCollision(penguin, (LightIceBlock) hazard, direction);
                break;
            case HEAVY_ICE_BLOCK:
                handleHeavyIceBlockCollision(penguin);
                break;
            case SEA_LION:
                handleSeaLionCollision(penguin, (SeaLion) hazard, direction);
                break;
            case HOLE_IN_ICE:
                handleHoleInIceCollision(penguin, (HoleInIce) hazard);
                break;
        }
    }
    
    /**
     * Handles collision with a LightIceBlock.
     * Penguin stops and is stunned, ice block starts sliding.
     * 
     * @param penguin the penguin
     * @param iceBlock the LightIceBlock
     * @param direction the direction of collision
     */
    private void handleLightIceBlockCollision(Penguin penguin, LightIceBlock iceBlock, Direction direction) {
        System.out.println(penguin.getName() + " hits a LightIceBlock and is stunned!");
        penguin.setStunned(true);
        
        // Ice block starts sliding in the same direction
        executeHazardSlide(iceBlock, direction);
    }
    
    /**
     * Handles collision with a HeavyIceBlock.
     * Penguin stops and loses lightest food.
     * 
     * @param penguin the penguin
     */
    private void handleHeavyIceBlockCollision(Penguin penguin) {
        System.out.println(penguin.getName() + " hits a HeavyIceBlock!");
        FoodItem droppedFood = penguin.dropLightestFood();
        
        if (droppedFood != null) {
            System.out.println(penguin.getName() + " loses " + droppedFood.getDisplayName() 
                    + " (" + droppedFood.getWeight() + " units)!");
        }
    }
    
    /**
     * Handles collision with a SeaLion.
     * Penguin bounces back, SeaLion slides in penguin's original direction.
     * 
     * @param penguin the penguin
     * @param seaLion the SeaLion
     * @param direction the original direction
     */
    private void handleSeaLionCollision(Penguin penguin, SeaLion seaLion, Direction direction) {
        System.out.println(penguin.getName() + " bounces off a SeaLion!");
        
        // SeaLion slides in the penguin's original direction
        Direction oppositeDir = direction.getOpposite();
        
        // Penguin bounces back (opposite direction)
        System.out.println(penguin.getName() + " starts sliding " + oppositeDir.getDisplayName() + ".");
        System.out.println("SeaLion starts sliding " + direction.getDisplayName() + ".");
        
        // Execute both slides
        executeHazardSlide(seaLion, direction);
        executeSlide(penguin, oppositeDir);
    }
    
    /**
     * Handles collision with a HoleInIce.
     * Penguin falls in and is removed.
     * 
     * @param penguin the penguin
     * @param hole the HoleInIce
     */
    private void handleHoleInIceCollision(Penguin penguin, HoleInIce hole) {
        if (hole.isPlugged()) {
            // Hole is plugged, safe to pass
            System.out.println(penguin.getName() + " passes over a plugged hole.");
            return;
        }
        
        System.out.println(penguin.getName() + " falls into a HoleInIce!");
        System.out.println("*** " + penguin.getName() + " IS REMOVED FROM THE GAME!");
        terrain.removePenguin(penguin);
    }
    
    /**
     * Executes sliding for a hazard (LightIceBlock or SeaLion).
     * 
     * @param hazard the hazard that's sliding
     * @param direction the direction to slide
     */
    private void executeHazardSlide(IHazard hazard, Direction direction) {
        if (!(hazard instanceof IMovable)) {
            return;
        }
        
        ITerrainObject hazardObj = (ITerrainObject) hazard;
        
        int row = hazardObj.getRow();
        int col = hazardObj.getColumn();
        
        while (true) {
            int nextRow = row + direction.getRowDelta();
            int nextCol = col + direction.getColumnDelta();
            
            // Check if next position is off the grid (water)
            if (!terrain.isValidPosition(nextRow, nextCol)) {
                terrain.removeHazard(hazard);
                System.out.println(hazard.getHazardName() + " falls into the water!");
                return;
            }
            
            ITerrainObject nextObj = terrain.getObjectAt(nextRow, nextCol);
            
            // Empty square - continue sliding
            if (nextObj == null) {
                row = nextRow;
                col = nextCol;
                continue;
            }
            
            // Food item - remove food and continue
            if (nextObj instanceof FoodItem) {
                FoodItem food = (FoodItem) nextObj;
                food.setRemoved(true);
                terrain.removeObjectAt(nextRow, nextCol);
                System.out.println(hazard.getHazardName() + " destroys " + food.getDisplayName() + "!");
                row = nextRow;
                col = nextCol;
                continue;
            }
            
            // Another penguin - stop
            if (nextObj instanceof Penguin) {
                terrain.moveObject(hazardObj, row, col);
                System.out.println(hazard.getHazardName() + " stops near a penguin.");
                return;
            }
            
            // Another hazard
            if (nextObj instanceof IHazard) {
                IHazard otherHazard = (IHazard) nextObj;
                
                // HoleInIce - fall in and plug it
                if (otherHazard instanceof HoleInIce) {
                    HoleInIce hole = (HoleInIce) otherHazard;
                    if (!hole.isPlugged()) {
                        terrain.removeHazard(hazard);
                        hole.plug();
                        System.out.println(hazard.getHazardName() + " falls into and plugs the HoleInIce!");
                        return;
                    } else {
                        // Plugged hole - continue
                        row = nextRow;
                        col = nextCol;
                        continue;
                    }
                }
                
                // SeaLion hit by LightIceBlock
                if (hazard instanceof LightIceBlock && otherHazard instanceof SeaLion) {
                    SeaLion seaLion = (SeaLion) otherHazard;
                    terrain.moveObject(hazardObj, row, col);
                    System.out.println("LightIceBlock stops and transfers movement to SeaLion.");
                    executeHazardSlide(seaLion, direction);
                    return;
                }
                
                // Stop at other hazards
                terrain.moveObject(hazardObj, row, col);
                return;
            }
            
            row = nextRow;
            col = nextCol;
        }
    }
    
    // ==================== Game Results ====================
    
    /**
     * Displays the final game results and scoreboard.
     */
    private void displayGameResults() {
        System.out.println("\n***** GAME OVER *****");
        System.out.println("***** SCOREBOARD FOR THE PENGUINS *****");
        
        // Sort penguins by total food weight (descending)
        List<Penguin> sortedPenguins = new ArrayList<>(terrain.getPenguins());
        Collections.sort(sortedPenguins, new Comparator<Penguin>() {
            @Override
            public int compare(Penguin p1, Penguin p2) {
                return Integer.compare(p2.getTotalFoodWeight(), p1.getTotalFoodWeight());
            }
        });
        
        String[] places = {"1st", "2nd", "3rd"};
        
        for (int i = 0; i < sortedPenguins.size(); i++) {
            Penguin penguin = sortedPenguins.get(i);
            String playerMarker = isPlayerPenguin(penguin) ? " (Your Penguin)" : "";
            
            System.out.println("* " + places[i] + " place: " + penguin.getName() + playerMarker);
            
            // Display collected food items
            List<FoodItem> foods = penguin.getCollectedFood();
            if (foods.isEmpty()) {
                System.out.println("|---> Food items: None");
            } else {
                StringBuilder foodList = new StringBuilder("|---> Food items: ");
                for (int j = 0; j < foods.size(); j++) {
                    FoodItem food = foods.get(j);
                    foodList.append(food.toString());
                    if (j < foods.size() - 1) {
                        foodList.append(", ");
                    }
                }
                System.out.println(foodList.toString());
            }
            
            System.out.println("|---> Total weight: " + penguin.getTotalFoodWeight() + " units");
        }
    }
}
