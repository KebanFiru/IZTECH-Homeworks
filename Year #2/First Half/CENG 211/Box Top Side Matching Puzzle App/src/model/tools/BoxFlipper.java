package model.tools;

import core.BoxGrid;
import model.box.Box;

/**
 * BoxFlipper is a SpecialTool that flips a box upside down.
 * The top and bottom surfaces are swapped.
 * Throws UnmovableFixedBoxException if used on a FixedBox.
 */
public class BoxFlipper extends SpecialTool{

    /**
     * Constructs a BoxFlipper tool.
     */
    public BoxFlipper(){
        super("BoxFlipper");
    }

    /**
     * Flips the box at the specified location upside down.
     * @param grid the BoxGrid
     * @param args expects (int row, int column)
     */
    @Override
    public void useTool(BoxGrid grid, Object... args){
        int row = (Integer) args[0];
        int column = (Integer) args[1];
        Box box = grid.getBox(row, column);
        box.flipUpsideDown();
    }
}
