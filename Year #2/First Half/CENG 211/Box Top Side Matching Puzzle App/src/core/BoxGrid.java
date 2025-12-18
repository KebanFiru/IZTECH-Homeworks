package core;

import core.exceptions.UnmovableFixedBoxException;
import model.box.Box;
import model.box.FixedBox;
import model.box.RegularBox;
import model.box.UnchangingBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoxGrid {
    private List<List<Box>> grid;
    private final static int ROW = 8;
    private final static int COLUMN = 8;

    public BoxGrid(){
        grid = new ArrayList<>();
        generateGrid();
    }

    public void generateGrid(){
        Random random = new Random();
        for (int r=0; r < ROW; r++);{
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

    public Box getBox(int row, int column){
        Box box = grid.get(row).get(column);
        return new Box(box);
    }

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

    public void displayGrid() {
        System.out.print("    ");
        for(int c=1; c<=COLUMN; c++) System.out.print("C" + c + "    ");
        System.out.println("\n");

        for (int r = 0; r < ROW; r++) {
            System.out.print("R" + (r + 1) + "  ");
            for (int c = 0; c < COLUMN; c++) {
                System.out.print("| " + grid.get(r).get(c).getNotation() + " ");
            }
            System.out.println("|");
        }
    }

    public int countTargetLetterOnTop(char target) {
        int count = 0;
        for (List<Box> row : grid) {
            for (Box b : row) {
                if (b.getTopLetter() == target) count++;
            }
        }
        return count;
    }
}



