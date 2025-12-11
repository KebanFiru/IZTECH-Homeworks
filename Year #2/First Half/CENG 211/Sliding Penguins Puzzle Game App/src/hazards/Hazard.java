package hazards;

import penguins.Penguin;
import terrain.Direction;
import terrain.IcyTerrain;

/**
 * Abstract base class for all hazard types on the terrain.
 * Hazards can affect penguins that land on them in various ways.
 */
public abstract class Hazard implements IHazard {
    protected int row, column;

    @Override
    public int getRow() { return row; }
    
    @Override
    public int getColumn() { return column; }
    
    @Override
    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Factory method to create a random hazard.
     * 
     * @return A randomly generated hazard (LightIceBlock, HeavyIceBlock, SeaLion, or HoleInIce)
     */
    public static Hazard getRandomHazard() {
        int n = (int)(Math.random() * 4);
        switch(n) {
            case 0: return new LightIceBlock();
            case 1: return new HeavyIceBlock();
            case 2: return new SeaLion();
            default: return new HoleInIce();
        }
    }
    
    /**
     * Called when a penguin lands on this hazard.
     * Each hazard type must implement its specific behavior.
     * 
     * @param penguin The penguin that landed on the hazard
     * @param terrain The terrain where the interaction occurs
     * @param dir The direction the penguin was moving
     */
    public abstract void onPenguinLand(Penguin penguin, IcyTerrain terrain, Direction dir);
}