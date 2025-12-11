package hazards;

public class HeavyIceBlock extends Hazard {
    @Override
    public String getNotation() { return "HB"; }
    @Override
    public boolean canSlide() { return true; }
    @Override
    public String getHazardType() { return HazardType.HEAVY_ICE_BLOCK.name(); }
}