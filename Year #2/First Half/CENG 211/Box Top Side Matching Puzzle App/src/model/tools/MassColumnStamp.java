package model.tools;

import core.BoxGrid;
import model.box.Box;
import interfaces.IStampable;
import model.enums.Letter;

/**
 * MassColumnStamp is a SpecialTool that stamps all boxes in an entire column with the target letter.
 * 
 * Behavior:
 * - Stamps all stampable boxes in the column
 * - UnchangingBox: Can be stamped but letter doesn't change
 * - FixedBox: Cannot be stamped (skipped, doesn't implement IStampable)
 */
public class MassColumnStamp extends SpecialTool {

    /**
     * Constructs a MassColumnStamp tool.
     */
    public MassColumnStamp() {
        super("MassColumnStamp");
    }

    /**
     * Stamps all stampable boxes in the specified column with the target letter.
     * @param grid the BoxGrid
     * @param args expects (int column)
     */
    @Override
    public void useTool(BoxGrid grid, Object... args) {
        int column = (Integer) args[0];
        Letter targetLetter = Letter.fromChar(grid.getStampLetter());
        
        for (int row = 0; row < grid.getRowCount(); row++) {
            Box box = grid.getBox(row, column);
            if (box instanceof IStampable) {
                ((IStampable) box).stampTopSide(targetLetter);
            }
        }
    }
}
