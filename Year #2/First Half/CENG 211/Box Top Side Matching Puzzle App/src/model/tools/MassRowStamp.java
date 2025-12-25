package model.tools;

import model.box.Box;
import model.box.FixedBox;
import core.BoxGrid;

public class MassRowStamp extends SpecialTool{

    public MassRowStamp(){
        super("MassRowStamp");
    }

    public void useTool(BoxGrid grid, Object... args){

        int row = (Integer) args[0];

        char stampLetter = grid.getStampLetter();

        for(int i= 0; i<grid.getColumnCount(); i++){

            Box box = grid.getBox(row, i);

            if(!(box instanceof FixedBox)){

                box.setTopLetter(stampLetter);
            }
        }
    }
}
