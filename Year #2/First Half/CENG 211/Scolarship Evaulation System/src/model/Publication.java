package model;

public class Publication {
    private final String applicantID;
    private final String title;
    private final double impactFactor;

    public Publication(String applicantID, String title, double impactFactor) {
        this.applicantID = applicantID;
        this.title = title;
        this.impactFactor = impactFactor;
    }

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