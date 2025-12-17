package model.tools;

import core.BoxGrid;
import model.box.Box;

public abstract class SpecialTool {

    private String name;

    public SpecialTool(String name){

        this.name = name;
    }

    public String getName(){

        return name;
    }

    public abstract void use(BoxGrid grid, int row, int column);
}
