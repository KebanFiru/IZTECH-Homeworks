package types;

/**
 * Enum representing the result type of a scholarship application.
 * Used to indicate whether an applicant receives a full or half scholarship.
 */
public enum ScholarshipResultType {
    FULL("Full"),
    HALF("Half"),
    NONE("None");

    private final String display;

    ScholarshipResultType(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }

    @Override
    public String toString() {
        return display;
    }
}
