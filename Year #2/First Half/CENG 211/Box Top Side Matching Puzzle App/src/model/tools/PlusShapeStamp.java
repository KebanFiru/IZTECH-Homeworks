package model.tools;

import core.BoxGrid;
import model.box.Box;
import interfaces.IStampable;
import model.enums.Letter;

/**
 * PlusShapeStamp is a SpecialTool that stamps the center and adjacent boxes (up, down, left, right)
 * with the target letter in a plus shape.
 * 
 * Behavior:
 * - Stamps center box and its 4 adjacent neighbors (if they exist and are stampable)
 * - UnchangingBox: Can be stamped but letter doesn't change
 * - FixedBox: Cannot be stamped (skipped if in the plus shape)
 * - Boxes outside grid boundaries are safely ignored
 */
public class PlusShapeStamp extends SpecialTool {

    /**
     * Constructs a PlusShapeStamp tool.
     */
    public PlusShapeStamp() {
        super("PlusShapeStamp");
    }

    /**
     * Stamps the center and adjacent boxes in a plus shape with the target letter.
     * @param grid the BoxGrid
     * @param args expects (int row, int column) for the center position
     */
    @Override
    public void useTool(BoxGrid grid, Object... args) {
        int row = (Integer) args[0];
        int column = (Integer) args[1];
        
        Letter targetLetter = Letter.fromChar(grid.getStampLetter());
        
        // Stamp center box
        stampBox(grid, row, column, targetLetter);
        
        // Stamp adjacent boxes (with boundary checks)
        if (row > 0) stampBox(grid, row - 1, column, targetLetter);        // Up
        if (row < grid.getRowCount() - 1) stampBox(grid, row + 1, column, targetLetter); // Down
        if (column > 0) stampBox(grid, row, column - 1, targetLetter);     // Left
        if (column < grid.getColumnCount() - 1) stampBox(grid, row, column + 1, targetLetter); // Right
    }
    
    /**
     * Helper method to stamp a single box if it implements IStampable.
     * @param grid the BoxGrid
     * @param row the row index
     * @param column the column index
     * @param letter the letter to stamp
     */
    private void stampBox(BoxGrid grid, int row, int column, Letter letter) {
        Box box = grid.getBox(row, column);
        if (box instanceof IStampable) {
            ((IStampable) box).stampTopSide(letter);
        }
    }
}
