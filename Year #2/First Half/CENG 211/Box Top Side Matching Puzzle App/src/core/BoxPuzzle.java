package core;

import model.box.*;
import model.tools.SpecialTool;
import core.exceptions.*;
import model.enums.Direction;
import java.util.Scanner;

/**
 * BoxPuzzle is the main game controller that manages the puzzle game flow.
 * Contains the BoxGrid, target letter, and turn management.
 * Uses an inner class MenuHandler to handle all user interactions.
 */
public class BoxPuzzle {
    private BoxGrid grid;
    private char targetLetter;
    private static final int TOTAL_TURNS = 5;

    /**
     * Constructs a new BoxPuzzle game.
     * Initializes the grid, selects a random target letter, and starts the game.
     */
    public BoxPuzzle() {
        // Select random target letter (A-H)
        this.targetLetter = (char) ('A' + new java.util.Random().nextInt(8));
        this.grid = new BoxGrid(targetLetter);
        
        // Display welcome message
        System.out.println("Welcome to Box Top Side Matching Puzzle App. An 8x8 box grid is being generated.");
        System.out.println("\nYour goal is to maximize the letter \"" + targetLetter + "\" on the top sides of the boxes.");
        System.out.println("\nThe initial state of the box grid:");
        grid.displayGrid();
        
        // Start the game
        new MenuHandler().play();
    }

    /**
     * Generic method to use a tool on the grid.
     * Demonstrates use of Generics as required by homework.
     * 
     * @param <T> the type of SpecialTool (must extend SpecialTool)
     * @param tool the tool to use
     * @throws BoxAlreadyFixedException if using BoxFixer on already fixed box
     * @throws UnmovableFixedBoxException if using BoxFlipper on FixedBox
     */
    public <T extends SpecialTool> void useTool(T tool) throws BoxAlreadyFixedException, UnmovableFixedBoxException {
        if (tool != null) {
            tool.useTool(this.grid);
        }
    }

    /**
     * Inner class that handles all menu interactions and game flow.
     * This demonstrates the use of Inner Classes as required by homework.
     */
    private class MenuHandler {
        private Scanner sc = new Scanner(System.in);

        /**
         * Main game loop - plays through all 5 turns.
         */
        public void play() {
            for (int turn = 1; turn <= TOTAL_TURNS; turn++) {
                System.out.println("\n\n=====> TURN " + turn + ":");
                
                try {
                    // Reset rolled status at start of turn
                    grid.resetRolledStatus();
                    
                    // Optional: view surfaces of a box
                    promptViewSurfaces();
                    
                    // Stage 1: Roll edge box
                    System.out.println(" ---> TURN " + turn + " – FIRST STAGE:");
                    promptEdgeRoll();
                    System.out.println("The chosen box and any box on its path have been rolled. The new state of the box grid:");
                    grid.displayGrid();
                    
                    // Stage 2: Open box and use tool
                    System.out.println(" ---> TURN " + turn + " – SECOND STAGE:");
                    promptOpenBox();
                    
                } catch (UnmovableFixedBoxException e) {
                    System.out.println("\nThe chosen box is automatically rolled.");
                    System.out.println("HOWEVER, IT IS FIXED BOX AND CANNOT BE MOVED. Continuing to the next turn...");
                } catch (EmptyBoxException e) {
                    System.out.println("\nBOX IS EMPTY! Continuing to the next turn...");
                } catch (BoxAlreadyFixedException e) {
                    System.out.println("\n" + e.getMessage() + " Turn wasted!");
                } catch (Exception e) {
                    System.out.println("INCORRECT INPUT: " + e.getMessage());
                    turn--; // Retry this turn
                }
            }
            showFinalScore();
        }

        /**
         * Prompts user if they want to view all surfaces of a box before their turn.
         */
        private void promptViewSurfaces() {
            try {
                System.out.print(" ---> Do you want to view all surfaces of a box? [1] Yes or [2] No?  ");
                String choice = sc.nextLine().trim();
                if (choice.equals("1")) {
                    System.out.print("\nPlease enter the location of the box you want to view:  ");
                    String loc = sc.nextLine().toUpperCase().trim();
                    int[] coords = parseLocation(loc);
                    Box box = grid.getBox(coords[0], coords[1]);
                    System.out.println(box.displayAllSurfaces());
                } else {
                    System.out.println("\nContinuing to the first stage...");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("INCORRECT INPUT: " + e.getMessage());
                System.out.println("\nContinuing to the first stage...");
            } catch (Exception e) {
                System.out.println("INCORRECT INPUT. Continuing to the first stage...");
            }
        }

        /**
         * Prompts user to select and roll an edge box.
         * @throws UnmovableFixedBoxException if selected box is FixedBox
         */
        private void promptEdgeRoll() throws UnmovableFixedBoxException {
            try {
                System.out.print("Please enter the location of the edge box you want to roll: ");
                String loc = sc.nextLine().toUpperCase().trim();

                int[] coords = parseLocation(loc);
                int r = coords[0];
                int c = coords[1];

                // Check if box is on edge
                boolean isEdge = (r == 0 || r == 7 || c == 0 || c == 7);
                if (!isEdge) {
                    System.out.print("INCORRECT INPUT: The chosen box is not on any of the edges. Please reenter the location: ");
                    promptEdgeRoll();
                    return;
                }

                // Determine direction based on position
                Direction dir = null;
                boolean isCorner = (r == 0 || r == 7) && (c == 0 || c == 7);
                
                if (isCorner) {
                    // Corner box: user chooses direction
                    System.out.print("\nThe chosen box can be rolled to either ");
                    if (r == 0 && c == 0) {
                        System.out.print("[1] right or [2] downwards: ");
                        int choice = Integer.parseInt(sc.nextLine().trim());
                        dir = (choice == 1) ? Direction.RIGHT : Direction.DOWN;
                    } else if (r == 0 && c == 7) {
                        System.out.print("[1] left or [2] downwards: ");
                        int choice = Integer.parseInt(sc.nextLine().trim());
                        dir = (choice == 1) ? Direction.LEFT : Direction.DOWN;
                    } else if (r == 7 && c == 0) {
                        System.out.print("[1] right or [2] upwards: ");
                        int choice = Integer.parseInt(sc.nextLine().trim());
                        dir = (choice == 1) ? Direction.RIGHT : Direction.UP;
                    } else { // r == 7 && c == 7
                        System.out.print("[1] left or [2] upwards: ");
                        int choice = Integer.parseInt(sc.nextLine().trim());
                        dir = (choice == 1) ? Direction.LEFT : Direction.UP;
                    }
                } else {
                    // Edge box: automatic direction
                    if (r == 0) dir = Direction.DOWN;
                    else if (r == 7) dir = Direction.UP;
                    else if (c == 0) dir = Direction.RIGHT;
                    else dir = Direction.LEFT;
                    System.out.println("The chosen box is automatically rolled to " + dir.toString().toLowerCase() + ".");
                }

                // Roll the boxes
                grid.rollEdgeBox(r, c, dir);
            } catch (IllegalArgumentException e) {
                System.out.println("INCORRECT INPUT: " + e.getMessage());
                promptEdgeRoll();
            } catch (Exception e) {
                System.out.println("INCORRECT INPUT");
                promptEdgeRoll();
            }
        }

        /**
         * Prompts user to open a box that was rolled and use any tool found inside.
         * @throws EmptyBoxException if the box is empty
         * @throws BoxAlreadyFixedException if using BoxFixer on FixedBox
         * @throws UnmovableFixedBoxException if using BoxFlipper on FixedBox
         */
        private void promptOpenBox() throws EmptyBoxException, BoxAlreadyFixedException, UnmovableFixedBoxException {
            try {
                System.out.print("Please enter the location of the box you want to open: ");
                String loc = sc.nextLine().toUpperCase().trim();

                int[] coords = parseLocation(loc);
                int r = coords[0];
                int c = coords[1];

                Box selectedBox = grid.getBox(r, c);

                // Check if box was rolled this turn
                if (!selectedBox.wasRolledThisTurn()) {
                    System.out.print("INCORRECT INPUT: The chosen box was not rolled during the first stage. Please reenter the location: \n");
                    promptOpenBox();
                    return;
                }

                // Open the box
                SpecialTool tool = selectedBox.open();
                
                // If empty, throw exception
                if (tool == null) {
                    throw new EmptyBoxException("The box on location R" + (r+1) + "-C" + (c+1) + " is empty.");
                }

                // Display what was found
                System.out.println("\nThe box on location R" + (r+1) + "-C" + (c+1) + " is opened. It contains a SpecialTool --> " + tool.getName());
                
                // Use the tool based on its type (this may throw exceptions)
                useToolInteractively(tool);
                
                // Display result
                System.out.println("The new state of the box grid:");
                grid.displayGrid();
            } catch (IllegalArgumentException e) {
                System.out.println("INCORRECT INPUT: " + e.getMessage());
                promptOpenBox();
            }
        }

        /**
         * Prompts user for tool-specific inputs and applies the tool.
         * @param tool the tool to use
         * @throws BoxAlreadyFixedException if fixing already fixed box
         * @throws UnmovableFixedBoxException if flipping FixedBox
         */
        private void useToolInteractively(SpecialTool tool) throws BoxAlreadyFixedException, UnmovableFixedBoxException {
            try {
                String toolName = tool.getName();
                
                System.out.print("\nPlease enter the location of the box to use this SpecialTool: ");
                String loc = sc.nextLine().toUpperCase().trim();
                int[] coords = parseLocation(loc);
                
                // Apply tool with appropriate parameters
                tool.useTool(grid, coords[0], coords[1]);
                
                // Print confirmation message
                if (toolName.equals("PlusShapeStamp")) {
                    System.out.println("\nTop sides of the chosen box (R" + (coords[0]+1) + "-C" + (coords[1]+1) + 
                                     ") and its surrounding boxes have been stamped to letter \"" + targetLetter + "\".");
                } else if (toolName.equals("MassRowStamp")) {
                    System.out.println("\nAll boxes in row " + (coords[0]+1) + " have been stamped to letter \"" + targetLetter + "\".");
                } else if (toolName.equals("MassColumnStamp")) {
                    System.out.println("\nAll boxes in column " + (coords[1]+1) + " have been stamped to letter \"" + targetLetter + "\".");
                } else if (toolName.equals("BoxFlipper")) {
                    System.out.println("\nThe chosen box on location R" + (coords[0]+1) + "-C" + (coords[1]+1) + " has been flipped upside down.");
                } else if (toolName.equals("BoxFixer")) {
                    System.out.println("\nThe chosen box on location R" + (coords[0]+1) + "-C" + (coords[1]+1) + " has been replaced with a FixedBox.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("INCORRECT INPUT: " + e.getMessage());
                useToolInteractively(tool);
            }
        }

        /**
         * Parses location string like "R1-C4" or "1-4" into [row, col] indices (0-based).
         * @param loc the location string
         * @return array [row, col] in 0-based indices
         * @throws IllegalArgumentException if format is invalid or out of bounds
         */
        private int[] parseLocation(String loc) {
            loc = loc.toUpperCase().replace(" ", "");
            int row, col;
            
            try {
                if (loc.contains("R") && loc.contains("C") && loc.contains("-")) {
                    // Format: R1-C4
                    row = Integer.parseInt(loc.substring(loc.indexOf("R") + 1, loc.indexOf("-"))) - 1;
                    col = Integer.parseInt(loc.substring(loc.indexOf("C") + 1)) - 1;
                } else if (loc.contains("-")) {
                    // Format: 1-4
                    String[] parts = loc.split("-");
                    row = Integer.parseInt(parts[0]) - 1;
                    col = Integer.parseInt(parts[1]) - 1;
                } else {
                    throw new IllegalArgumentException("Invalid location format. Use format like R1-C4 or 1-4");
                }
                
                // Validate bounds
                if (row < 0 || row >= 8 || col < 0 || col >= 8) {
                    throw new IllegalArgumentException("Location out of bounds. Row and column must be between 1 and 8");
                }
                
                return new int[]{row, col};
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid location format. Use format like R1-C4 or 1-4");
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Invalid location format. Use format like R1-C4 or 1-4");
            }
        }

        /**
         * Displays the final score and game result.
         */
        private void showFinalScore() {
            System.out.println("\n\n******** GAME OVER ********");
            System.out.println("\nThe final state of the box grid:");
            grid.displayGrid();
            
            int count = grid.countTargetLetterOnTop(targetLetter);
            System.out.println("\nTHE TOTAL NUMBER OF TARGET LETTER \"" + targetLetter + "\" IN THE BOX GRID --> " + count);
            System.out.println("The game has been SUCCESSFULLY completed!");
        }
    }
}
