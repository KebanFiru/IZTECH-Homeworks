package model.tools;

import core.BoxGrid;
import model.box.Box;
import model.box.FixedBox;
import core.exceptions.BoxAlreadyFixedException;


public class BoxFixer extends SpecialTool{

    public BoxFixer(){
        super("BoxFixer");
    }

    public void useTool(BoxGrid grid, Object... args){

        int row = (Integer) args[0];
        int column = (Integer) args[1];

        Box box = new grid.getBox(row, column);
        
        if(box.getClass() == FixedBox){

            throw new BoxAlreadyFixedException("Box at (" + row + "," + col + ") is already a FixedBox.");
        }

        FixedBox fixedBox = new FixedBox(box.getTopLetter);

        grid.replaceBox(row, column. fixedBox);
    }
}
