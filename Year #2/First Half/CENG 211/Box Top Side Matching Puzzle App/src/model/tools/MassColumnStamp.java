package model.tools;

import core.BoxGrid;
import model.box.FixedBox;
import model.box.Box;

public class MassColumnStamp extends SpecialTool{

    public MassColumnStamp(){

        super("MassColumnStamp");
    }

    public void use(BoxGrid grid, int column){

        char stampLetter = grid.getStampLetter();

        for(int i= 0; i<grid.getRowCount(); i++){

            Box box = grid.getBox(i, column);

            if(!(box instanceof FixedBox)){

                box.setTopLetter(stampLetter);
            }
        }
    }
}
