package hazards;

public class LightIceBlock extends Hazard {
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
}