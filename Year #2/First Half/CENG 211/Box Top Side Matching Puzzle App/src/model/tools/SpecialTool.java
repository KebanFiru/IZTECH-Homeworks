package model.tools;

import model.box.Box;

public abstract class SpecialTool {

    private String name;

    public SpecialTool(String name){

        this.name = name;
    }

    public String getName(){

        return name;
    }

    public abstract void use(Box box);
}
