package application;

import java.util.ArrayList;

public abstract class Application {
    protected String applicantID;
    protected String name;
    protected double gpa;
    protected double income;
    protected ArrayList<Document> documents;
    protected ArrayList<Publication> publications;
    protected String transcriptStatus;

    public Application(String applicantID, String name, double gpa, double income, String transcriptStatus) {
        this.applicantID = applicantID;
        this.name = name;
        this.gpa = gpa;
        this.income = income;
        this.documents = new ArrayList<>();
        this.publications = new ArrayList<>();
        this.transcriptStatus = transcriptStatus.toUpperCase();
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

    public boolean getTranscriptValidation(){

        if(transcriptStatus.equals("Y")){
            return false;
        }
        return true;
    }

    public boolean hasPublicationOrGRP(){
        if(publications.size() != 0 || documents.contains("GRP")){
            return true;
        }
        return false;
    }

    public ArrayList<Publication> getPublications(){

        return publications;
    }

    public void addDocument(Document doc) {
        this.documents.add(doc);
    }

    public void addPublication(Publication pub) {
        this.publications.add(pub);
    }

    public boolean hasDocument(String documentType) {
        for (Document doc : documents) {
            if (doc.getDocumentType().equals(documentType)) {
                return true;
            }
        }
        return false;
    }

    public abstract String getScholarshipCategory();

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Applicant ID: ").append(applicantID)
                .append(", Name: ").append(name)
                .append(", Scholarship: ").append(getScholarshipCategory());

        return result.toString();
    }
}