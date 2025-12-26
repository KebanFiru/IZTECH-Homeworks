package model.tools;

import core.BoxGrid;

/**
 * Abstract base class for all special tools in the puzzle game.
 * Each tool has a name and a useTool method to apply its effect to the grid.
 * Tools may throw exceptions when used incorrectly (e.g., on FixedBoxes).
 */
public abstract class SpecialTool {

    private String name;

    /**
     * Constructs a SpecialTool with the given name.
     * @param name the name of the tool
     */
    public SpecialTool(String name){
        this.name = name;
    }

    /**
     * Returns the name of the tool.
     * @return the tool name
     */
    public String getName(){
        return name;
    }

    /**
     * Applies the tool's effect to the grid at the specified location/arguments.
     * @param grid the BoxGrid
     * @param args additional arguments (row, column, etc.)
     */
    public abstract void useTool(BoxGrid grid, Object... args);
}
