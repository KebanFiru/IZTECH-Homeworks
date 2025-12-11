package hazards;

/**
 * Sea Lion hazard - a predator that poses danger to penguins.
 * Cannot be moved or slid.
 */
public class SeaLion extends Hazard {
    @Override
    public String getNotation() {
        return "SL";
    }

    @Override
    public boolean canSlide() {
        return false;
    }

    @Override
    public String getHazardType() {
        return HazardType.SEALION.name();
    }
}