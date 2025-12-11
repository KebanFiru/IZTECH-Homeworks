package model;

import terrain.Direction;
import terrain.IcyTerrain;

public interface ISlidable {

    void slide(Direction direction, IcyTerrain terrain);

    boolean isSliding();

    void setSliding(boolean sliding);
}