package application;

import java.util.ArrayList;

public abstract class Application {
    protected String applicantID;
    protected String name;
    protected double gpa;
    protected double income;
    protected String transcriptStatus;
    protected ArrayList<Document> documents;
    protected ArrayList<Publication> publications;

    protected String status;
    protected String scholarshipType;
    protected int durationInYears;
    protected String rejectionReason;

    public Application(String applicantID, String name, double gpa, double income) {
        this.applicantID = applicantID;
        this.name = name;
        this.gpa = gpa;
        this.income = income;
        this.documents = new ArrayList<>();
        this.publications = new ArrayList<>();
        this.transcriptStatus = "N";
    }

    public String getApplicantID() {
        return applicantID;
    }

    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    public String getStatus() {
        return status;
    }

    public String getScholarshipType() {
        return scholarshipType;
    }

    public int getDurationInYears() {
        return durationInYears;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setTranscriptStatus(String status) {
        this.transcriptStatus = status;
    }

    public void addDocument(Document doc) {
        this.documents.add(doc);
    }

    public void addPublication(Publication pub) {
        this.publications.add(pub);
    }

    protected boolean hasDocument(String documentType) {
        for (Document doc : documents) {
            if (doc.getDocumentType().equals(documentType)) {
                return true;
            }
        }
        return false;
    }

    protected int getDocumentDuration(String documentType) {
        for (Document doc : documents) {
            if (doc.getDocumentType().equals(documentType)) {
                return doc.getDurationInMonths();
            }
        }
        return 0;
    }

    protected boolean passesGeneralChecks() {
        // Check 1: Must have Enrollment Certificate
        if (!hasDocument("ENR")) {
            status = "Rejected";
            rejectionReason = "Missing Enrollment Certificate";
            return false;
        }

        // Check 2: Transcript must be valid
        if (!transcriptStatus.equals("Y")) {
            status = "Rejected";
            rejectionReason = "Missing Transcript";
            return false;
        }

        // Check 3: GPA must be >= 2.50
        if (gpa < 2.50) {
            status = "Rejected";
            rejectionReason = "GPA below 2.5";
            return false;
        }

        return true;
    }

    public abstract void evaluate();

    public abstract String getScholarshipCategory();

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Applicant ID: ").append(applicantID)
                .append(", Name: ").append(name)
                .append(", Scholarship: ").append(getScholarshipCategory())
                .append(", Status: ").append(status);

        if (status.equals("Accepted")) {
            result.append(", Type: ").append(scholarshipType)
                    .append(", Duration: ").append(durationInYears)
                    .append(durationInYears == 1 ? " year" : " years");
        } else {
            result.append(", Reason: ").append(rejectionReason);
        }

        return result.toString();
    }
}