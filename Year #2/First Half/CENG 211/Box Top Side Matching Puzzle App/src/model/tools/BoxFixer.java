package model.tools;

import core.BoxGrid;
import model.box.Box;
import core.exceptions.BoxAlreadyFixedException;


public class BoxFixer extends SpecialTool{

    public BoxFixer(){
        super("BoxFixer");
    }

    public void use(BoxGrid grid,Object... args){

        if(args.length!=2){
            throw new ArrayStoreException("args must be length of 2 ");
        }
        if(!(args[0] instanceof Integer) || !(args[1] instanceof Integer) ){
            throw new IllegalArgumentException("args[0] and args[1] must be type of integer");
        }

        int row = (int)args[0];
        int column = (int)args[1];

        Box box = grid.getBox(row, column);

        if(box.getClass() == FixedBox){

            throw new BoxAlreadyFixedException("Box at (" + row + "," + col + ") is already a FixedBox.");
        }

        FixedBox fixedBox = new FixedBox(box.getTopLetter);

        grid.replaceBox(row, column. fixedBox);
    }
}
