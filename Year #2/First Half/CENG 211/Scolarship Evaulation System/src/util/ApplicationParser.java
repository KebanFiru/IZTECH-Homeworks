package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import model.Applicant;
import model.Document;
import model.Publication;
import types.Application;
import types.MeritApplication;
import types.NeedApplication;
import types.ResearchApplication;
import types.DocumentType;

/**
 * Parser class for reading CSV files and creating Application objects.
 * Uses StringTokenizer for robust CSV parsing with proper delimiter handling.
 */
public class ApplicationParser {
    
    /**
     * Parses scholarship applications from CSV file and creates Application objects.
     * Returns ArrayList of Application objects demonstrating POLYMORPHISM.
     * 
     * CSV Format:
     * - A,ID,Name,GPA,Income (Applicant basic info)
     * - T,ID,Status (Transcript validation status)
     * - I,ID,FamilyIncome,Dependents (Family information)
     * - D,ID,DocType,Duration (Supporting documents)
     * - P,ID,Title,ImpactFactor (Research publications)
     * 
     * Error Handling:
     * - Returns empty list if file not found
     * - Skips malformed lines with warning message
     * - Validates all numeric conversions
     * 
     * @param filename path to the CSV file
     * @return ArrayList of Application objects, empty if file cannot be read
     * @throws IllegalArgumentException if filename is null or empty
     */
    public static ArrayList<Application> parse(String filename) {
        // Input validation - defensive programming
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }
        
        // ArrayLists to store all data (using only ArrayList, no HashMap)
        ArrayList<ApplicantData> applicantDataList = new ArrayList<>();
        ArrayList<TranscriptData> transcriptList = new ArrayList<>();
        ArrayList<FamilyInfoData> familyInfoList = new ArrayList<>();
        ArrayList<Document> documentsList = new ArrayList<>();
        ArrayList<Publication> publicationsList = new ArrayList<>();
        
        int lineNumber = 0;
        
        // Read CSV file with comprehensive error handling
        try (BufferedReader inputStream = new BufferedReader(new FileReader(filename))) {
            String line;
            
            while ((line = inputStream.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                
                // Skip empty lines (defensive programming)
                if (line.isEmpty()) {
                    continue;
                }
                
                try {
                    // Parse line using StringTokenizer
                    StringTokenizer tokenizer = new StringTokenizer(line, ",");
                    if (!tokenizer.hasMoreTokens()) {
                        continue; // Skip lines with no tokens
                    }
                    
                    String prefix = tokenizer.nextToken().trim();
                    
                    // Process based on row type
                    switch (prefix) {
                        case "A": // Applicant Info: A,ID,Name,GPA,Income
                            if (tokenizer.countTokens() >= 4) {
                                int id = Integer.parseInt(tokenizer.nextToken().trim());
                                String name = tokenizer.nextToken().trim();
                                double gpa = Double.parseDouble(tokenizer.nextToken().trim());
                                int income = Integer.parseInt(tokenizer.nextToken().trim());
                                
                                applicantDataList.add(new ApplicantData(id, name, gpa, income));
                            } 
                            else {
                                System.err.println("Warning: Line " + lineNumber + " - Incomplete applicant data, skipping");
                            }
                            break;
                            
                        case "T": // Transcript: T,ID,Status
                            if (tokenizer.countTokens() >= 2) {
                                int id = Integer.parseInt(tokenizer.nextToken().trim());
                                String status = tokenizer.nextToken().trim();
                                transcriptList.add(new TranscriptData(id, status));
                            } 
                            else {
                                System.err.println("Warning: Line " + lineNumber + " - Incomplete transcript data, skipping");
                            }
                            break;
                            
                        case "I": // Family Info: I,ID,FamilyIncome,Dependents
                            if (tokenizer.countTokens() >= 3) {
                                int id = Integer.parseInt(tokenizer.nextToken().trim());
                                int familyIncome = Integer.parseInt(tokenizer.nextToken().trim());
                                int dependents = Integer.parseInt(tokenizer.nextToken().trim());
                                
                                familyInfoList.add(new FamilyInfoData(id, familyIncome, dependents));
                            } 
                            else {
                                System.err.println("Warning: Line " + lineNumber + " - Incomplete family info data, skipping");
                            }
                            break;
                            
                        case "D": // Document: D,ID,DocType,Duration
                            if (tokenizer.countTokens() >= 3) {
                                int id = Integer.parseInt(tokenizer.nextToken().trim());
                                String docTypeStr = tokenizer.nextToken().trim();
                                int duration = Integer.parseInt(tokenizer.nextToken().trim());
                                
                                // Convert string to DocumentType enum
                                try {
                                    DocumentType docType = DocumentType.valueOf(docTypeStr);
                                    documentsList.add(new Document(id, docType, duration));
                                } 
                                catch (IllegalArgumentException e) {
                                    System.err.println("Warning: Line " + lineNumber + " - Invalid document type: " + docTypeStr);
                                }
                            } 
                            else {
                                System.err.println("Warning: Line " + lineNumber + " - Incomplete document data, skipping");
                            }
                            break;
                            
                        case "P": // Publication: P,ID,Title,ImpactFactor
                            if (tokenizer.countTokens() >= 3) {
                                int id = Integer.parseInt(tokenizer.nextToken().trim());
                                String title = tokenizer.nextToken().trim();
                                double impact = Double.parseDouble(tokenizer.nextToken().trim());
                                
                                publicationsList.add(new Publication(id, title, impact));
                            } 
                            else {
                                System.err.println("Warning: Line " + lineNumber + " - Incomplete publication data, skipping");
                            }
                            break;
                            
                        default:
                            System.err.println("Warning: Line " + lineNumber + " - Unknown prefix: " + prefix);
                            break;
                    }
                    
                } 
                catch (NumberFormatException e) {
                    System.err.println("Warning: Line " + lineNumber + " - Number format error: " + e.getMessage());
                } 
                catch (Exception e) {
                    System.err.println("Warning: Line " + lineNumber + " - Error parsing: " + e.getMessage());
                }
            }
            
        } 
        catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return new ArrayList<>(); // Return empty list on file error
        }
        
        // Build Applicant objects from parsed data
        ArrayList<Applicant> applicants = new ArrayList<>();
        
        for (ApplicantData appData : applicantDataList) {
            try {
                Applicant applicant = new Applicant(appData.id, appData.name, appData.gpa, appData.income);
                
                // Add transcript info
                for (TranscriptData t : transcriptList) {
                    if (t.applicantId == appData.id) {
                        applicant.setTranscript(t.status);
                        break;
                    }
                }
                
                // Add family info
                for (FamilyInfoData f : familyInfoList) {
                    if (f.applicantId == appData.id) {
                        applicant.setFamilyInfo(f.familyIncome, f.dependents);
                        break;
                    }
                }
                
                // Add documents
                for (Document doc : documentsList) {
                    if (doc.getApplicantId() == appData.id) {
                        applicant.addDocument(doc);
                    }
                }
                
                // Add publications
                for (Publication pub : publicationsList) {
                    if (pub.getApplicantId() == appData.id) {
                        applicant.addPublication(pub);
                    }
                }
                
                applicants.add(applicant);
                
            } 
            catch (IllegalArgumentException e) {
                System.err.println("Warning: Invalid applicant data for ID " + appData.id + ": " + e.getMessage());
            }
        }
        
        // Create Application objects using Factory Pattern
        ArrayList<Application> applications = new ArrayList<>();
        
        for (Applicant applicant : applicants) {
            Application app = createApplication(applicant);
            if (app != null) {
                applications.add(app);
            }
        }
        
        return applications;
    }
    
    /**
     * Factory Method Pattern - creates appropriate Application subclass based on ID prefix.
     * Demonstrates POLYMORPHISM and Factory Pattern for object creation.
     * 
     * ID Prefix mapping:
     * - 11xxx -> MeritApplication (GPA-based)
     * - 22xxx -> NeedApplication (income-based)
     * - 33xxx -> ResearchApplication (publication-based)
     * 
     * @param applicant the Applicant object
     * @return appropriate Application subclass, or null if ID format unknown
     */
    private static Application createApplication(Applicant applicant) {
        int id = applicant.getId();
        String idStr = String.valueOf(id);
        
        // Determine application type based on ID prefix (first 2 digits)
        if (idStr.length() >= 2) {
            String prefix = idStr.substring(0, 2);
            
            if (prefix.equals("11")) {
                return new MeritApplication(applicant);
            } 
            else if (prefix.equals("22")) {
                return new NeedApplication(applicant);
            } 
            else if (prefix.equals("33")) {
                return new ResearchApplication(applicant);
            }
        }
        
        System.err.println("Warning: Unknown application type for ID: " + id);
        return null;
    }
    
    // Inner helper classes for temporary data storage
    
    /**
     * Helper class to store applicant basic data during parsing.
     */
    private static class ApplicantData {
        final int id;
        final String name;
        final double gpa;
        final int income;
        
        ApplicantData(int id, String name, double gpa, int income) {
            this.id = id;
            this.name = name;
            this.gpa = gpa;
            this.income = income;
        }
    }
    
    /**
     * Helper class to store transcript data during parsing.
     */
    private static class TranscriptData {
        final int applicantId;
        final String status;
        
        TranscriptData(int applicantId, String status) {
            this.applicantId = applicantId;
            this.status = status;
        }
    }
    
    /**
     * Helper class to store family information during parsing.
     */
    private static class FamilyInfoData {
        final int applicantId;
        final int familyIncome;
        final int dependents;
        
        FamilyInfoData(int applicantId, int familyIncome, int dependents) {
            this.applicantId = applicantId;
            this.familyIncome = familyIncome;
            this.dependents = dependents;
        }
    }
}
