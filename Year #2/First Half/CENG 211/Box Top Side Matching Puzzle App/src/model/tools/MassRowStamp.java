package model.tools;

import model.box.Box;
import model.box.FixedBox;
import core.BoxGrid;
import model.enums.Letter;

/**
 * MassRowStamp is a SpecialTool that stamps all boxes in a row with a new letter.
 * Skips FixedBoxes.
 */
public class MassRowStamp extends SpecialTool{

    /**
     * Constructs a MassRowStamp tool.
     */
    public MassRowStamp(){
        super("MassRowStamp");
    }

    /**
     * Stamps all boxes in the specified row with a new letter, skipping FixedBoxes.
     * @param grid the BoxGrid
     * @param args expects (int row)
     */
    @Override
    public void useTool(BoxGrid grid, Object... args){
        int row = (Integer) args[0];
        char stampLetter = grid.getStampLetter();
        for(int i= 0; i<grid.getColumnCount(); i++){
            Box box = grid.getBox(row, i);
            if(!(box instanceof FixedBox)){
                box.setTopSide(Letter.fromChar(stampLetter));
            }
        }
    }
}
