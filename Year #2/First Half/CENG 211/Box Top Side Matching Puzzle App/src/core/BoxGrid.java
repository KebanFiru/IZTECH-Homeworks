package core;

import core.exceptions.UnmovableFixedBoxException;
import model.box.Box;
import model.box.FixedBox;
import model.box.RegularBox;
import model.box.UnchangingBox;
import model.enums.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ANSWER TO COLLECTIONS QUESTION:
 * 
 * This class uses ArrayList<ArrayList<Box>> to represent the 8x8 grid of boxes.
 * 
 * Why ArrayList?
 * - Dynamic sizing: Although we have a fixed 8x8 grid, ArrayList provides flexibility
 *   for potential future enhancements (different grid sizes).
 * - Index-based access: We need O(1) constant time access to boxes by row/column indices.
 *   ArrayList.get(index) provides this efficiency, which is crucial for frequent 
 *   box lookups during rolling, stamping, and display operations.
 * - Memory efficiency: ArrayList is more memory-efficient than LinkedList for our use case
 *   since we don't need frequent insertions/deletions in the middle of the structure.
 * - Iteration performance: Enhanced for-loop iteration over ArrayList is very fast,
 *   which is important for countTargetLetterOnTop() and display operations.
 * 
 * Why not other Collections?
 * - LinkedList: Would provide O(n) access time, which is inefficient for grid[row][col] lookups.
 * - HashMap: Unnecessary overhead for a simple 2D grid with sequential indices.
 * - Array[][]: While slightly more efficient, ArrayList provides better abstraction and
 *   is more flexible for future modifications while maintaining similar performance.
 * 
 * Structure: List<List<Box>> (outer list = rows, inner list = columns)
 * - Intuitive representation of 2D grid
 * - Easy to iterate over rows and columns
 * - Supports polymorphism: Can store RegularBox, UnchangingBox, and FixedBox objects
 */
public class BoxGrid {
    private List<List<Box>> grid;
    private char targetLetter;
    private final static int ROW = 8;
    private final static int COLUMN = 8;


    /**
     * Constructs a new BoxGrid and generates the initial 8x8 grid of boxes.
     * @param targetLetter the target letter for this game (used by stamping tools)
     */
    public BoxGrid(char targetLetter) {
        this.targetLetter = targetLetter;
        grid = new ArrayList<>();
        generateGrid();
    }

    /**
     * Generates the 8x8 grid of boxes with the specified probabilities:
     * - 85% RegularBox, 10% UnchangingBox, 5% FixedBox
     */
    public void generateGrid() {
        Random random = new Random();
        for (int r = 0; r < ROW; r++) {
            List<Box> currentRow = new ArrayList<>();
            for (int c = 0; c < COLUMN; c++) {
                double chance = random.nextDouble() * 100;
                Box box;
                if (chance < 5) {
                    box = new FixedBox();
                } else if (chance < 15) {  // 5% to 15% = 10% for UnchangingBox
                    box = new UnchangingBox();
                } else {  // 15% to 100% = 85% for RegularBox
                    box = new RegularBox();
                }
                currentRow.add(box);
            }
            grid.add(currentRow);
        }
    }

    /**
     * Returns the box at the specified row and column.
     *
     * @param row the row index (0-based)
     * @param column the column index (0-based)
     * @return the Box at the specified location
     */
    public Box getBox(int row, int column){
        return grid.get(row).get(column);
    }

    /**
     * Replaces the box at the specified location with a new box.
     *
     * @param row the row index (0-based)
     * @param column the column index (0-based)
     * @param newBox the new Box to place in the grid
     */
    public void replaceBox(int row, int column, Box newBox) {
        grid.get(row).set(column, newBox);
    }

    /**
     * Returns the number of rows in the grid.
     * @return the row count (always 8)
     */
    public int getRowCount() {
        return ROW;
    }

    /**
     * Returns the number of columns in the grid.
     * @return the column count (always 8)
     */
    public int getColumnCount() {
        return COLUMN;
    }

    /**
     * Returns the target letter to be used for stamping tools.
     * This is the letter that players are trying to maximize on box tops.
     * @return the target letter for this game
     */
    public char getStampLetter() {
        return targetLetter;
    }

    /**
     * Resets the rolled status of all boxes in the grid.
     * Should be called at the start of each turn.
     */
    public void resetRolledStatus() {
        for (List<Box> row : grid) {
            for (Box box : row) {
                box.setRolledThisTurn(false);
            }
        }
    }
    
    /**
     * Rolls the boxes along the specified edge starting from the given position in the given direction.
     * Implements the domino-effect: all boxes in the path roll until a FixedBox is encountered.
     * Marks all rolled boxes with setRolledThisTurn(true) for validation in stage 2.
     *
     * @param row the starting row index (0-based)
     * @param column the starting column index (0-based)
     * @param direction the direction to roll (UP, DOWN, LEFT, RIGHT)
     * @throws UnmovableFixedBoxException if the starting box is a FixedBox
     */
    public void rollEdgeBox(int row, int column, Direction direction) throws UnmovableFixedBoxException {
        Box startBox = grid.get(row).get(column);

        if (startBox instanceof FixedBox) {
            throw new UnmovableFixedBoxException("The chosen box is FIXED and cannot be moved!");
        }

        int currRow = row;
        int currColumn = column;

        // Roll boxes in the specified direction until boundary or FixedBox
        while (currRow >= 0 && currRow < ROW && currColumn >= 0 && currColumn < COLUMN) {
            Box currentBox = getBox(currRow, currColumn);

            // Stop if we hit a FixedBox (domino-effect stops here)
            if (currentBox instanceof FixedBox) {
                break;
            }

            // Roll the box and mark it as rolled this turn
            currentBox.roll(direction);
            currentBox.setRolledThisTurn(true);

            // Move to next box in the rolling direction
            switch (direction) {
                case RIGHT:
                    currColumn++;
                    break;
                case LEFT:
                    currColumn--;
                    break;
                case UP:
                    currRow--;
                    break;
                case DOWN:
                    currRow++;
                    break;
            }
        }
    }

    /**
     * Displays the current state of the grid to the console, showing row and column labels
     * and the notation of each box.
     */
    public void displayGrid() {
        // Print column headers
        System.out.print("        ");
        for (int c = 1; c <= COLUMN; c++) {
            System.out.printf("C%-6d", c);
        }
        System.out.println();

        // Print top border
        String horizontalLine = "    " + "-".repeat(COLUMN * 8 + 1);
        System.out.println(horizontalLine);

        // Print each row
        for (int r = 0; r < ROW; r++) {
            System.out.printf("R%-2d ", (r + 1));
            for (int c = 0; c < COLUMN; c++) {
                String content = grid.get(r).get(c).getGridDisplay();
                System.out.print("|" + content);
            }
            System.out.println("|");
            System.out.println(horizontalLine);
        }
    }



    /**
     * Counts how many boxes in the grid have the specified letter on their top face.
     *
     * @param target the target letter to count
     * @return the number of boxes with the target letter on top
     */
    public int countTargetLetterOnTop(char target) {
        int count = 0;
        for (List<Box> row : grid) {
            for (Box b : row) {
                if (b.getTopSide().toChar() == target) count++;
            }
        }
        return count;
    }
}



