package model.tools;

import core.BoxGrid;
import core.exceptions.BoxAlreadyFixedException;
import model.box.Box;
import model.box.FixedBox;

import model.enums.Letter;

/**
 * PlusShapeStamp is a SpecialTool that stamps the center and adjacent boxes (up, down, left, right)
 * with a new letter in a plus shape. Throws BoxAlreadyFixedException if used on a FixedBox.
 */
public class PlusShapeStamp extends SpecialTool{

    /**
     * Constructs a PlusShapeStamp tool.
     */
    public PlusShapeStamp(){
        super("PlusShapeStamp");
    }

    /**
     * Stamps the center and adjacent boxes in a plus shape with a new letter.
     * @param grid the BoxGrid
     * @param args expects (int row, int column)
     */
    @Override
    public void useTool(BoxGrid grid, Object... args) throws BoxAlreadyFixedException{
        int row = (Integer) args[0];
        int column = (Integer) args[1];
        Box centerBox = grid.getBox(row,column);
        if(!(centerBox instanceof FixedBox)){
            Box upBox = grid.getBox(row-1,column);
            Box bottomBox = grid.getBox(row+1,column);
            Box leftBox = grid.getBox(row,column-1);
            Box rightBox = grid.getBox(row,column+1);

            char stampLetter = grid.getStampLetter();
            upBox.setTopSide(Letter.valueOf(String.valueOf(stampLetter)));
            centerBox.setTopSide(Letter.valueOf(String.valueOf(stampLetter)));
            bottomBox.setTopSide(Letter.valueOf(String.valueOf(stampLetter)));
            leftBox.setTopSide(Letter.valueOf(String.valueOf(stampLetter)));
            rightBox.setTopSide(Letter.valueOf(String.valueOf(stampLetter)));
        }
        else{
            throw new BoxAlreadyFixedException("Cannot use PlusShapeStamp on a fixed box.");
        }
    }
}
