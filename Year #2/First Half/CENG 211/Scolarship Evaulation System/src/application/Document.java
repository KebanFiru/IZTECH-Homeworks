package application;

public class Document {
    private String applicantID;
    private String documentType;   // ENR, REC, SAV, RSV, GRP
    private int durationInMonths;

    public Document(String applicantID, String documentType, int durationInMonths) {
        this.applicantID = applicantID;
        this.documentType = documentType;
        this.durationInMonths = durationInMonths;
    }

    public String getApplicantID() { return applicantID; }
    public String getDocumentType() { return documentType; }
    public int getDurationInMonths() { return durationInMonths; }

    @Override
    public String toString() {
        return documentType + " (" + durationInMonths + " months)";
    }
}