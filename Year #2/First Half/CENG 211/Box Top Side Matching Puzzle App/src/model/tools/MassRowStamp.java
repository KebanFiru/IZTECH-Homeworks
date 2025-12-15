package model.tools;

public class MassRowStamp extends SpecialTool{

    public MassRowStamp(){
        super("MassRowStamp");
    }

    public void use(BoxGrid grid, int row, int column){

        char stampLetter = grid.getStampLetter();

        for(int i= 0; i<grid.getColumnCount; i++){

            Box box = grid.getBox(row, i);

            if(!(box instanceof FixedBox)){

                box.setTopLetter(stampLetter);
            }
        }
    }
}
