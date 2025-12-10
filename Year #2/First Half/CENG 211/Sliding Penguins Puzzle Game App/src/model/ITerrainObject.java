package model;

public interface ITerrainObject {

    String getNotation();

    int getRow();

    int getColumn();

    void setPosition(int row, int column);
}
