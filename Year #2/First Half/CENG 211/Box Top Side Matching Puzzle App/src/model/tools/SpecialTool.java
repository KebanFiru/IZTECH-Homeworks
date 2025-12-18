package model.tools;

import core.BoxGrid;

public abstract class SpecialTool {

    private String name;

    public SpecialTool(String name){

        this.name = name;
    }

    public String getName(){

        return name;
    }

    public abstract void useTool(BoxGrid grid, Object... args);
}
