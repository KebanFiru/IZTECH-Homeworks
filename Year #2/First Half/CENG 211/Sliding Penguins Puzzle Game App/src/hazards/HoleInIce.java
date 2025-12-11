package hazards;

public class HoleInIce extends Hazard {
    private boolean plugged = false;
    @Override
    public String getNotation() { return plugged ? "PX" : "HI"; }
    @Override
    public boolean canSlide() { return false; }
    @Override
    public String getHazardType() { return HazardType.HOLE_IN_ICE.name(); }
    public boolean isPlugged() { return plugged; }
    public void setPlugged(boolean plugged) { this.plugged = plugged; }
}