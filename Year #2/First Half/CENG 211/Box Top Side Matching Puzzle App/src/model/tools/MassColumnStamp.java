package model.tools;

import core.BoxGrid;
import model.box.FixedBox;
import model.box.Box;

import model.enums.Letter;
/**
 * MassColumnStamp is a SpecialTool that stamps all boxes in a column with a new letter.
 * Skips FixedBoxes.
 */
public class MassColumnStamp extends SpecialTool{

    /**
     * Constructs a MassColumnStamp tool.
     */
    public MassColumnStamp(){
        super("MassColumnStamp");
    }

    /**
     * Stamps all boxes in the specified column with a new letter, skipping FixedBoxes.
     * @param grid the BoxGrid
     * @param args expects (int column)
     */
    @Override
    public void useTool(BoxGrid grid, Object... args){
        int column = (Integer)args[0];
        char stampLetter = grid.getStampLetter();
        for(int i= 0; i<grid.getRowCount(); i++){
            Box box = grid.getBox(i, column);
            if(!(box instanceof FixedBox)){
                box.setTopSide(Letter.fromChar(stampLetter));
            }
        }
    }
}
