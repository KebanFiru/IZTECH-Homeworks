package model.tools;

import core.BoxGrid;

public class BoxFlipper extends SpecialTool{

    public BoxFlipper(){
        super("BoxFlipper");
    }

    public void use(BoxGrid grid, int row, int column){

        Box box = grid.getBox(row, column);

        char[] boxSideLetters = box.getSides();

        // I assume top as indexed 1 side and 3 as bottom indexed side

        char bottomSide = box.getBottomLetter();
        char topSide = box.getTopLetter();

        //Implement setSide in Box
        box.setSide(1, bottomSide);
        box.setSide(3, topSide);

    }
}
