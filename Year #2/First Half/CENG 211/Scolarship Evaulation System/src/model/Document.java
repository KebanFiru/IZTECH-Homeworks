package model;

public class Document {
    private final String applicantID;
    private final String documentType;   // ENR, REC, SAV, RSV, GRP
    private final int durationInMonths;

    public Document(String applicantID, String documentType, int durationInMonths) {
        this.applicantID = applicantID;
        this.documentType = documentType;
        this.durationInMonths = durationInMonths;
    }

    public Document(Document other) {
        this.applicantID = other.applicantID;
        this.documentType = other.documentType;
        this.durationInMonths = other.durationInMonths;
    }

    public String getApplicantID() {
        return applicantID;
    }

    public String getDocumentType() {
        return documentType;
    }

    public int getDurationInMonths() {
        return durationInMonths;
    }


    @Override
    public String toString() {
        return "Document: Applicant ID= " + applicantID+ ", Type=" + documentType + ", Duration=" + durationInMonths + " months";
    }
}