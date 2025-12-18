package model.tools;

import core.BoxGrid;
import core.exceptions.BoxAlreadyFixedException;
import model.box.Box;
import model.box.FixedBox;

public class PlusShapeStamp extends SpecialTool{

    public PlusShapeStamp(){
        super("PlusShapeStamp");
    }

    public void use(BoxGrid grid, int row, int column){
        Box centerBox = grid.getBox(row,column);

        if(!(centerBox instanceof FixedBox)){

            Box upBox = grid.getBox(row-1,column);
            Box bottomBox = grid.getBox(row+1,column);
            Box leftBox = grid.getBox(row,column-1);
            Box rightBox = grid.getBox(row,column+1);

            upBox.setTopLetter(grid.getStampLetter());
            centerBox.setTopLetter(grid.getStampLetter());
            bottomBox.setTopLetter(grid.getStampLetter());
            leftBox.setTopLetter(grid.getStampLetter());
            rightBox.setTopLetter(grid.getStampLetter());
            
        }
    }
}
