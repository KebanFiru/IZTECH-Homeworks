package model.tools;

import core.BoxGrid;
import model.box.Box;
import model.box.FixedBox;
import core.exceptions.BoxAlreadyFixedException;

/**
 * BoxFixer is a SpecialTool that replaces a box with an identical FixedBox copy.
 * 
 * Behavior:
 * - RegularBox/UnchangingBox: Replaced with FixedBox (same surfaces, tool removed)
 * - FixedBox: Throws BoxAlreadyFixedException (cannot fix an already fixed box)
 * 
 * After fixing:
 * - The new FixedBox has the same letters on all surfaces
 * - Any tool inside the original box is removed from the game
 * - The box becomes immovable for the rest of the game
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
     * @param grid the BoxGrid
     * @param args expects (int row, int column)
     * @throws BoxAlreadyFixedException if the box is already a FixedBox
     */
    @Override
    public void useTool(BoxGrid grid, Object... args) throws BoxAlreadyFixedException {
        int row = (Integer) args[0];
        int column = (Integer) args[1];

        Box box = grid.getBox(row, column);
        if (box instanceof FixedBox) {
            throw new BoxAlreadyFixedException("Box at R" + (row + 1) + "-C" + (column + 1) + " is already a FixedBox.");
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

        // Replace the box in the grid
        grid.replaceBox(row, column, fixedBox);
    }
}
