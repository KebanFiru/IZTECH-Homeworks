package evaluator;

import model.Applicant;
import model.Publication;
import types.Application;
import types.RejectionReason;
import types.ScholarshipResultType;

/**
 * Abstract base class for scholarship evaluation logic.
 * Defines the common evaluation framework used by all scholarship types.
 * 
 * Each concrete evaluator (Merit, Need, Research) extends this class
 * and implements specific evaluation rules.
 */
public abstract class ScholarshipEvaluator {

    /**
     * Evaluates an application and returns a formatted result string.
     * This is the main method called by Application objects.
     * 
     * @param application The application to evaluate
     * @return Formatted evaluation result string
     */
    public String evaluate(Application application) {
        Applicant applicant = application.getApplicant();
        
        // Check if application is accepted
        RejectionReason rejectionReason = getRejectionReason(application);
        boolean isAccepted = (rejectionReason == RejectionReason.NONE);
        
        // Build formatted output
        StringBuilder result = new StringBuilder();
        result.append(String.format("Applicant ID: %s, Name: %s, Scholarship: %s, Status: %s",
                applicant.getApplicantID(),
                applicant.getName(),
                application.getScholarshipCategory(),
                isAccepted ? "Accepted" : "Rejected"));
        
        if (isAccepted) {
            ScholarshipResultType type = getScholarshipType(application);
            String duration = getApplicationDuration(application);
            result.append(String.format(", Type: %s, Duration: %s", type.getDisplay(), duration));
        } else {
            result.append(String.format(", Reason: %s", rejectionReason.getMessage()));
        }
        
        return result.toString();
    }

    /**
     * Determines the rejection reason for an application (priority order).
     * Returns NONE if application should be accepted.
     * 
     * @param application The application to check
     * @return The rejection reason or NONE if accepted
     */
    public RejectionReason getRejectionReason(Application application) {
        Applicant applicant = application.getApplicant();
        
        // Priority 1: Missing Enrollment Certificate
        if (!applicant.hasDocument("ENR")) {
            return RejectionReason.MISSING_ENROLLMENT;
        }
        
        // Priority 2: Missing Transcript
        if (!applicant.getTranscriptValidation()) {
            return RejectionReason.MISSING_TRANSCRIPT;
        }
        
        // Priority 3: GPA below 2.5
        if (applicant.getGpa() < 2.5) {
            return RejectionReason.GPA_BELOW_MINIMUM;
        }
        
        // Specific rejection reasons handled by subclasses
        return getSpecificRejectionReason(application);
    }

    /**
     * Returns scholarship-specific rejection reasons.
     * Must be implemented by each concrete evaluator.
     * 
     * @param application The application to check
     * @return The specific rejection reason or NONE
     */
    protected abstract RejectionReason getSpecificRejectionReason(Application application);

    /**
     * Determines the scholarship type (FULL, HALF, or NONE).
     * Must be implemented by each concrete evaluator.
     * 
     * @param application The application to evaluate
     * @return The scholarship result type
     */
    public abstract ScholarshipResultType getScholarshipType(Application application);

    /**
     * Calculates the scholarship duration.
     * Must be implemented by each concrete evaluator.
     * 
     * @param application The application to evaluate
     * @return Duration string (e.g., "2 years", "6 months")
     */
    protected abstract String getApplicationDuration(Application application);

    /**
     * Helper method: Calculates average impact factor of publications.
     * Used by Research scholarship evaluator.
     * 
     * @param applicant The applicant whose publications to analyze
     * @return Average impact factor or 0.0 if no publications
     */
    protected double getAverageImpact(Applicant applicant) {
        if (applicant.getPublications().isEmpty()) {
            return 0.0;
        }
        
        double totalImpact = 0.0;
        for (Publication publication : applicant.getPublications()) {
            totalImpact += publication.getImpactFactor();
        }
        
        return totalImpact / applicant.getPublications().size();
    }
}
