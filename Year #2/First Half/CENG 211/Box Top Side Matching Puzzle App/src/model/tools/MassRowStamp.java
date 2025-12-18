package model.tools;

import model.box.Box;

public class MassRowStamp extends SpecialTool{

    public MassRowStamp(){
        super("MassRowStamp");
    }

    public void useTool(BoxGrid grid, int row){

        char stampLetter = grid.getStampLetter();

        for(int i= 0; i<grid.getColumnCount(); i++){

            Box box = grid.getBox(row, i);

            if(!(box instanceof FixedBox)){

                box.setTopLetter(stampLetter);
            }
        }
    }
}
