package model.tools;

import core.BoxGrid;
import model.box.Box;
import core.exceptions.BoxAlreadyFixedException;


public class BoxFixer extends SpecialTool{

    public BoxFixer(){
        super("BoxFixer");
    }

    public void useTool(BoxGrid grid, int row, int column){

        Box box = new grid.getBox(row, column);
        
        if(box.getClass() == FixedBox){

            throw new BoxAlreadyFixedException("Box at (" + row + "," + col + ") is already a FixedBox.");
        }

        FixedBox fixedBox = new FixedBox(box.getTopLetter);

        grid.replaceBox(row, column. fixedBox);
    }
}
