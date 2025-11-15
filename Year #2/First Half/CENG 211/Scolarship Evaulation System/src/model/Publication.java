package model;

/**
 * Represents a research publication submitted with a research grant application.
 * Publications are evaluated based on their impact factor to determine
 * scholarship eligibility and type.
 * 
 * Impact Factor Thresholds:
 * - ≥ 1.50: Qualifies for Full Scholarship
 * - 1.00 - 1.49: Qualifies for Half Scholarship
 * - < 1.00: Does not qualify
 * 
 * This class is immutable - all fields are final.
 */
public class Publication {
    private final String applicantID;
    private final String title;
    private final double impactFactor;

    /**
     * Constructs a Publication with the specified details.
     * 
     * @param applicantID the ID of the applicant who authored this publication
     * @param title the title of the publication
     * @param impactFactor the impact factor of the publication (quality metric)
     */
    public Publication(String applicantID, String title, double impactFactor) {
        this.applicantID = applicantID;
        this.title = title;
        this.impactFactor = impactFactor;
    }

    /**
     * Copy constructor - creates a copy of another Publication.
     * 
     * @param other the publication to copy from
     */
    public Publication(Publication other){
        this.applicantID = other.applicantID;
        this.title = other.title;
        this.impactFactor = other.impactFactor;
    }

    public String getApplicantID() {
        return applicantID;
    }

    public String getTitle() {
        return title;
    }

    public double getImpactFactor() {
        return impactFactor;
    }

    @Override
    public String toString() {
        return "Publication: Applicant ID= " + applicantID + " Title=" + title + ", Impact=" + impactFactor;
    }


}