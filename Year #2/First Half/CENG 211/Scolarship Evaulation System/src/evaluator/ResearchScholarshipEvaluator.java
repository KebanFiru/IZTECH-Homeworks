package evaluator;

import model.Applicant;
import types.Application;
import types.RejectionReason;
import types.ScholarshipResultType;

/**
 * Evaluator for Research Grant applications (ID prefix: 33).
 * Implements the common evaluation framework for research-based criteria.
 * 
 * This class follows the Single Responsibility Principle (SRP):
 * - Application classes handle data (applicant info, publications, state)
 * - Evaluator classes handle business logic (impact factor calculations, duration logic)
 * 
 * Evaluation Rules:
 * - At least one Publication (P) or Grant Proposal (GRP) required
 * - Average impact factor ≥ 1.50 → Full Scholarship
 * - 1.00 ≤ avg impact < 1.50 → Half Scholarship
 * - avg impact < 1.00 → Rejected
 * - Research Supervisor Approval (RSV) → +1 year extension
 * - Base duration: Full → 1 year, Half → 6 months
 */
public class ResearchScholarshipEvaluator extends ScholarshipEvaluator {

    // Constants for impact factor thresholds (avoiding magic numbers)
    private static final double FULL_SCHOLARSHIP_IMPACT = 1.50;
    private static final double HALF_SCHOLARSHIP_IMPACT = 1.00;
    private static final int FULL_SCHOLARSHIP_BASE_MONTHS = 12;  // 1 year
    private static final int HALF_SCHOLARSHIP_BASE_MONTHS = 6;   // 6 months
    private static final int SUPERVISOR_APPROVAL_EXTENSION_MONTHS = 12;  // +1 year
    private static final int MONTHS_PER_YEAR = 12;

    /**
     * Creates a new ResearchScholarshipEvaluator instance.
     * This evaluator is stateless and can be instantiated as needed.
     */
    public ResearchScholarshipEvaluator() {
        // Default constructor
    }

    @Override
    protected RejectionReason getSpecificRejectionReason(Application application) {
        Applicant applicant = application.getApplicant();
        
        // Must have at least one publication or GRP
        if (!applicant.hasPublicationOrGRP()) {
            return RejectionReason.MISSING_PUBLICATION_OR_PROPOSAL;
        }
        
        // Check average impact factor
        double avgImpact = getAverageImpact(applicant);
        if (avgImpact < HALF_SCHOLARSHIP_IMPACT) {
            return RejectionReason.PUBLICATION_IMPACT_TOO_LOW;
        }
        
        return RejectionReason.NONE;
    }

    @Override
    public ScholarshipResultType getScholarshipType(Application application) {
        Applicant applicant = application.getApplicant();
        double avgImpact = getAverageImpact(applicant);
        
        if (avgImpact >= FULL_SCHOLARSHIP_IMPACT) {
            return ScholarshipResultType.FULL;
        } 
        else if (avgImpact >= HALF_SCHOLARSHIP_IMPACT) {
            return ScholarshipResultType.HALF;
        }
        
        return ScholarshipResultType.NONE;
    }

    @Override
    protected String getApplicationDuration(Application application) {
        Applicant applicant = application.getApplicant();
        ScholarshipResultType type = getScholarshipType(application);
        
        // Base duration
        int months;
        if (type == ScholarshipResultType.FULL) {
            months = FULL_SCHOLARSHIP_BASE_MONTHS;
        } else {
            months = HALF_SCHOLARSHIP_BASE_MONTHS;
        }
        
        // If Research Supervisor Approval exists, add +1 year extension
        if (applicant.hasDocument("RSV")) {
            months += SUPERVISOR_APPROVAL_EXTENSION_MONTHS;
        }
        
        // Format output
        return formatDuration(months);
    }

    /**
     * Helper method to format duration in months to readable string.
     * Converts months to "X year(s) Y months" format.
     * 
     * @param months Total duration in months
     * @return Formatted duration string
     */
    private String formatDuration(int months) {
        if (months >= MONTHS_PER_YEAR) {
            int years = months / MONTHS_PER_YEAR;
            int remainingMonths = months % MONTHS_PER_YEAR;
            
            if (remainingMonths == 0) {
                return years + (years == 1 ? " year" : " years");
            } else {
                return years + (years == 1 ? " year" : " years") + " " + remainingMonths + " months";
            }
        }
        
        return months + " months";
    }
}
