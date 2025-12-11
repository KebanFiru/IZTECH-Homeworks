package hazards;

import penguins.Penguin;
import terrain.Direction;
import terrain.IcyTerrain;

public abstract class Hazard implements IHazard {
    protected int row, column;

    @Override
    public int getRow() { return row; }
    @Override
    public int getColumn() { return column; }
    @Override
    public void setPosition(int row, int column) {
        this.row = row; this.column = column;
    }

    public static Hazard getRandomHazard() {
        int n = (int)(Math.random() * 4);
        switch(n) {
            case 0: return new LightIceBlock();
            case 1: return new HeavyIceBlock();
            case 2: return new SeaLion();
            default: return new HoleInIce();
        }
    }
    public void onPenguinLand(Penguin penguin, IcyTerrain terrain, Direction dir) {
        System.out.println(penguin.getName() + " lands on a " + getHazardType() + ".");
    }
}