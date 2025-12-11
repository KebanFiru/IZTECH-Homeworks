package hazards;

import terrain.Direction;
import terrain.IcyTerrain;
import model.ISlidable;

public class LightIceBlock extends Hazard implements ISlidable {
    private boolean sliding = false;

    @Override
    public String getNotation() {
        return "LB";
    }

    @Override
    public boolean canSlide() {
        return true;
    }

    @Override
    public String getHazardType() {
        return HazardType.LIGHT_ICE_BLOCK.name();
    }

    // Implements ISlidable
    @Override
    public void slide(Direction direction, IcyTerrain terrain) {
        int row = getRow(), col = getColumn();
        int[] next = terrain.getNextPosition(row, col, direction);
        if (terrain.isValidPosition(next[0], next[1]) && terrain.getObjectAt(next[0], next[1]) == null) {
            terrain.removeObject(row, col);
            setPosition(next[0], next[1]);
            terrain.placeObject(this, next[0], next[1]);
            setSliding(true);
        } else {
            setSliding(false);
        }
    }

    @Override
    public boolean isSliding() {
        return sliding;
    }

    @Override
    public void setSliding(boolean sliding) {
        this.sliding = sliding;
    }
}