package model.tools;

import model.box.Box;
import interfaces.IStampable;
import core.BoxGrid;
import model.enums.Letter;

/**
 * MassRowStamp is a SpecialTool that stamps all boxes in an entire row with the target letter.
 * 
 * Behavior:
 * - Stamps all stampable boxes in the row
 * - UnchangingBox: Can be stamped but letter doesn't change
 * - FixedBox: Cannot be stamped (skipped, doesn't implement IStampable)
 */
public class MassRowStamp extends SpecialTool {

    /**
     * Constructs a MassRowStamp tool.
     */
    public MassRowStamp() {
        super("MassRowStamp");
    }

    /**
     * Stamps all stampable boxes in the specified row with the target letter.
     * @param grid the BoxGrid
     * @param args expects (int row)
     */
    @Override
    public void useTool(BoxGrid grid, Object... args) {
        int row = (Integer) args[0];
        Letter targetLetter = Letter.fromChar(grid.getStampLetter());
        
        for (int col = 0; col < grid.getColumnCount(); col++) {
            Box box = grid.getBox(row, col);
            if (box instanceof IStampable) {
                ((IStampable) box).stampTopSide(targetLetter);
            }
        }
    }
}
