package application;

import java.util.ArrayList;

public class Applicant {
    private final int id;
    private final String name;
    private final double gpa;
    private final int income;
    private String transcriptStatus;
    private int familyIncome;
    private int dependents;
    private boolean hasFamilyInfo;
    private ArrayList<Document> documents;
    private ArrayList<Publication> publications;

    public Applicant(int id, String name , double gpa, int income){

        if (id <= 0) {
            throw new IllegalArgumentException("Applicant ID must be positive: " + id);
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0: " + gpa);
        }
        if (income < 0) {
            throw new IllegalArgumentException("Income cannot be negative: " + income);
        }

        this.id = id;
        this.name = name.trim();
        this.gpa = gpa;
        this.income = income;
        documents = new ArrayList<>();
        publications = new ArrayList<>();
        transcriptStatus = "";
        hasFamilyInfo = false;
    }

    public Applicant(Applicant other){
        if (other == null) {
            throw new IllegalArgumentException("Cannot copy from null Applicant");
        }
        this.id = other.id;
        this.name = other.name;
        this.gpa = other.gpa;
        this.income = other.income;
        this.transcriptStatus = other.transcriptStatus;
        this.familyIncome = other.familyIncome;
        this.dependents = other.dependents;
        this.hasFamilyInfo = other.hasFamilyInfo;
        this.documents = documentsCopy(other.documents);
        this.publications = publicationsCopy(other.publications);

    }

    public ArrayList<Document> documentsCopy(ArrayList<Document> documentArrayList){
        ArrayList<Document> copy = new ArrayList<Document>();
        for(Document doc : documentArrayList){
            copy.add(new Document(doc));
        }
        return copy;
    }

    public ArrayList<Publication> publicationsCopy(ArrayList<Publication> publicationArrayList){
        ArrayList<Publication> copy = new ArrayList<Publication>();
        for (Publication pub : publicationArrayList) {
            copy.add(new Publication(pub));
        }
        return copy;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    public int getIncome() {
        return income;
    }

    public String getTranscriptStatus() {
        return transcriptStatus;
    }

    public boolean hasFamilyInfo() {
        return hasFamilyInfo;
    }

    public int getFamilyIncome() {
        return familyIncome;
    }

    public int getDependents() {
        return dependents;
    }

    public ArrayList<Document> getDocuments() {
        return documentsCopy(documents);
    }

    public ArrayList<Publication> getPublications() {
        return publicationsCopy(publications);
    }

    public void setTranscript(String status) {
        if (status == null || (!status.equals("Y") && !status.equals("N"))) {
            throw new IllegalArgumentException("Transcript status must be 'Y' or 'N': " + status);
        }
        this.transcriptStatus = status;
    }

    public void setFamilyInfo(int familyIncome, int dependents) {
        if (familyIncome < 0) {
            throw new IllegalArgumentException("Family income cannot be negative: " + familyIncome);
        }
        if (dependents < 0) {
            throw new IllegalArgumentException("Dependents cannot be negative: " + dependents);
        }
        this.familyIncome = familyIncome;
        this.dependents = dependents;
        this.hasFamilyInfo = true;
    }

    public void addDocument(Document doc) {
        if (doc != null) {
            this.documents.add(doc);
        }
    }

    public void addPublication(Publication pub) {
        if (pub != null) {
            this.publications.add(pub);
        }
    }

    public boolean hasDocument(String documentType) {
        if (documentType == null) {
            return false;
        }
        for (Document doc : documents) {
            if (doc.getDocumentType().equals(documentType)) {
                return true;
            }
        }
        return false;
    }

    public Document getDocument(String documentType) {
        if (documentType == null) {
            return null; // Defensive programming
        }
        for (Document doc : documents) {
            if (doc.getDocumentType().equals(documentType)) {
                return doc;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String output = "Applicant: ID=" + id +
                ", Name=" + name +
                ", GPA=" + gpa +
                ", Income=" + income +
                ", Transcript='" + transcriptStatus ;

        if (hasFamilyInfo) {
            output += ", FamilyIncome=" + familyIncome +
                    ", Dependents=" + dependents;
        }

        output += ", Documents=" + documents.size() +
                ", Publications=" + publications.size();

        return output;
    }

}
