package model.tools;

import model.box.Box;
import core.exceptions.BoxAlreadyFixedException;


public class BoxFixer extends SpecialTool{

    public BoxFixer(){
        super("BoxFixer");
    }

    public void use(Box box){
        
        if(box.getClass() == FixedBox){

            throw new BoxAlreadyFixedException("Box at (" + row + "," + col + ") is already a FixedBox.");
        }

        FixedBox fixedBox = new FixedBox(box.getTopLetter);

        grid.replaceBox(row, column. fixedBox);
    }
}
