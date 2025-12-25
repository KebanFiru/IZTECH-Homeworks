package model.tools;

import core.BoxGrid;
import model.box.Box;
import model.box.FixedBox;
import core.exceptions.BoxAlreadyFixedException;


/**
 * BoxFixer is a SpecialTool that replaces a box with an identical FixedBox copy.
 * If the box is already a FixedBox, throws BoxAlreadyFixedException and wastes the turn.
 * If the box contains a tool, it is removed from the game.
 * Usage: select a box location, and the box at that location is replaced with a FixedBox.
 */
public class BoxFixer extends SpecialTool {

    /**
     * Constructs a BoxFixer tool.
     */
    public BoxFixer() {
        super("BoxFixer");
    }

    /**
     * Replaces the box at the specified location with a FixedBox copy.
     * Throws BoxAlreadyFixedException if the box is already fixed.
     * @param grid the BoxGrid
     * @param args expects (int row, int column)
     */
    @Override
    public void useTool(BoxGrid grid, Object... args) {
        int row = (Integer) args[0];
        int column = (Integer) args[1];

        Box box = grid.getBox(row, column);
        if (box instanceof FixedBox) {
            throw new BoxAlreadyFixedException("Box at (" + row + "," + column + ") is already a FixedBox.");
        }

        // Create a FixedBox with the same surfaces as the original box
        FixedBox fixedBox = new FixedBox(
            box.getTopSide(),
            box.getBottomSide(),
            box.getFrontSide(),
            box.getBackSide(),
            box.getLeftSide(),
            box.getRightSide()
        );

        // Replace the box in the grid (assume grid has a replaceBox method)
        grid.replaceBox(row, column, fixedBox);
    }
}
