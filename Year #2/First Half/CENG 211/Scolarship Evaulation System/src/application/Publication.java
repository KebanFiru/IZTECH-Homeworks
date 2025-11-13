package application;

public class Publication {
    private String applicantID;
    private String title;
    private double impactFactor;

    public Publication(String applicantID, String title, double impactFactor) {
        this.applicantID = applicantID;
        this.title = title;
        this.impactFactor = impactFactor;
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
}