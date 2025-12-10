package model;

import terrain.Direction;

public interface ISlidable {

    void slide(Direction direction);

    boolean isSliding();

    void setSliding(boolean sliding);

}
