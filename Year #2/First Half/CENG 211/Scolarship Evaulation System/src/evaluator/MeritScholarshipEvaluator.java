package evaluator;

import model.Applicant;
import types.Application;
import types.RejectionReason;
import types.ScholarshipResultType;

/**
 * Evaluator for Merit-Based Scholarship applications (ID prefix: 11).
 * Implements the common evaluation framework for merit-based criteria.
 * 
 * This class follows the Single Responsibility Principle (SRP):
 * - Application classes handle data (applicant info, documents, state)
 * - Evaluator classes handle business logic (validation, scoring, decision making)
 * 
 * Evaluation Rules:
 * - GPA ≥ 3.20 → Full Scholarship
 * - 3.00 ≤ GPA < 3.20 → Half Scholarship
 * - GPA < 3.00 → Rejected
 * - Recommendation Letter (REC) → 2 years duration, otherwise 1 year
 */
public class MeritScholarshipEvaluator extends ScholarshipEvaluator {

    // Constants for GPA thresholds (avoiding magic numbers)
    private static final double FULL_SCHOLARSHIP_GPA = 3.20;
    private static final double HALF_SCHOLARSHIP_GPA = 3.00;
    private static final String DURATION_WITH_REC = "2 years";
    private static final String DURATION_WITHOUT_REC = "1 year";

    /**
     * Creates a new MeritScholarshipEvaluator instance.
     * This evaluator is stateless and can be instantiated as needed.
     */
    public MeritScholarshipEvaluator() {
        // Default constructor
    }

    @Override
    protected RejectionReason getSpecificRejectionReason(Application application) {
        Applicant applicant = application.getApplicant();
        
        // Merit scholarship requires GPA ≥ 3.0
        if (applicant.getGpa() < HALF_SCHOLARSHIP_GPA) {
            return RejectionReason.GPA_BELOW_THRESHOLD;
        }
        
        return RejectionReason.NONE;
    }

    @Override
    public ScholarshipResultType getScholarshipType(Application application) {
        Applicant applicant = application.getApplicant();
        double gpa = applicant.getGpa();
        
        if (gpa >= FULL_SCHOLARSHIP_GPA) {
            return ScholarshipResultType.FULL;
        } 
        else if (gpa >= HALF_SCHOLARSHIP_GPA) {
            return ScholarshipResultType.HALF;
        }
        
        return ScholarshipResultType.NONE;
    }

    @Override
    protected String getApplicationDuration(Application application) {
        Applicant applicant = application.getApplicant();
        
        // If Recommendation Letter exists, 2 years; otherwise 1 year
        if (applicant.hasDocument("REC")) {
            return DURATION_WITH_REC;
        }
        
        return DURATION_WITHOUT_REC;
    }
}
