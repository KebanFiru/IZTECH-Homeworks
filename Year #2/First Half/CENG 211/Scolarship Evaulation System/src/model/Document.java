package model;

/**
 * Represents a supporting document submitted with a scholarship application.
 * Documents are used to verify eligibility and determine scholarship duration.
 * 
 * Document Types:
 * - ENR: Enrollment Certificate (mandatory for all)
 * - REC: Recommendation Letter (for merit-based)
 * - SAV: Savings Document (for need-based)
 * - RSV: Research Supervisor Approval (for research grants)
 * - GRP: Grant Proposal (for research grants)
 * 
 * This class is immutable - all fields are final.
 */
public class Document {
    private final String applicantID;
    private final String documentType;   // ENR, REC, SAV, RSV, GRP
    private final int durationInMonths;

    /**
     * Constructs a Document with the specified details.
     * 
     * @param applicantID the ID of the applicant who submitted this document
     * @param documentType the type of document (ENR, REC, SAV, RSV, GRP)
     * @param durationInMonths the duration specified in the document (in months)
     */
    public Document(String applicantID, String documentType, int durationInMonths) {
        this.applicantID = applicantID;
        this.documentType = documentType;
        this.durationInMonths = durationInMonths;
    }

    /**
     * Copy constructor - creates a copy of another Document.
     * 
     * @param other the document to copy from
     */
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