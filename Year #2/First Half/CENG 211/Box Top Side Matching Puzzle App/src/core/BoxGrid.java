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
 * BoxGrid manages the 8x8 grid of boxes for the puzzle game.
 * Handles box generation, access, rolling, and display operations.
 * Provides methods for tool usage and letter counting.
 */
public class BoxGrid {
    private List<List<Box>> grid;
    private final static int ROW = 8;
    private final static int COLUMN = 8;


    /**
     * Constructs a new BoxGrid and generates the initial 8x8 grid of boxes.
     */
    public BoxGrid(){
        grid = new ArrayList<>();
        generateGrid();
    }

    /**
     * Generates the 8x8 grid of boxes with the specified probabilities:
     * - 5% FixedBox, 5% UnchangingBox, 90% RegularBox
     */
    public void generateGrid(){
        Random random = new Random();
        for (int r=0; r < ROW; r++){
            List<Box> currentRow = new ArrayList<>();
            for(int c=0; c < COLUMN; c++){
                double chance = random.nextDouble() * 100;
                Box box;
                if(chance < 5) box = new FixedBox();
                else if (chance < 10) box = new UnchangingBox();
                else box = new RegularBox();
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
     * Returns the letter to be used for stamping tools (random A-H).
     * @return a random letter (A-H)
     */
    public char getStampLetter() {
        return (char)('A' + new Random().nextInt(8));
    }

    /**
     * Rolls the boxes along the specified edge starting from the given position in the given direction.
     * Stops rolling when a FixedBox is encountered or the edge of the grid is reached.
     *
     * @param row the starting row index
     * @param column the starting column index
     * @param direction the direction to roll (UP, DOWN, LEFT, RIGHT)
     * @throws UnmovableFixedBoxException if the starting box is a FixedBox
     */
    public void rollEdgeBox (int row, int column, Direction direction) throws UnmovableFixedBoxException {
        Box startBox = grid.get(row).get(column);

        if (startBox instanceof FixedBox){
            throw new UnmovableFixedBoxException("Unmovable Fixed Box selected!");
        }

        int currRow = row;
        int currColumn = column;

        while (currRow >= 0 && currRow < ROW && currColumn >= 0 && currColumn < COLUMN){
            Box currentBox = getBox(currRow, currColumn);

            if (currentBox instanceof FixedBox) break;

            currentBox.roll(direction);

            if (direction == Direction.RIGHT) currColumn++;
            else if (direction == Direction.LEFT) currColumn--;
            else if (direction == Direction.UP) currRow--;
            else if (direction == Direction.DOWN) currRow++;
        }
    }

    /**
     * Displays the current state of the grid to the console, showing row and column labels
     * and the notation of each box.
     */
    public void displayGrid() {
        System.out.print("   ");
        for (int c = 1; c <= COLUMN; c++) {
            System.out.printf("     C%-4d", c);
        }
        System.out.println();

        String horizontalLine = "    " + "-".repeat((COLUMN * 10) + 1);

        System.out.println(horizontalLine);

        for (int r = 0; r < ROW; r++) {
            System.out.printf("R%-2d ", (r + 1));
            for (int c = 0; c < COLUMN; c++) {
                String content = grid.get(r).get(c).getGridDisplay();
                System.out.print("| " + content + " ");
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



