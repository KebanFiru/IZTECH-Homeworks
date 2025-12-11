package hazards;

import model.ITerrainObject;

public interface IHazard extends ITerrainObject {
    boolean canSlide();
    String getHazardType();
}