package evaluator;

import model.Applicant;
import types.Application;
import types.RejectionReason;
import types.ScholarshipResultType;

/**
 * Evaluator for Need-Based Scholarship applications (ID prefix: 22).
 * Implements the common evaluation framework for need-based criteria.
 * 
 * This class follows the Single Responsibility Principle (SRP):
 * - Application classes handle data (applicant info, family info, state)
 * - Evaluator classes handle business logic (income calculations, threshold adjustments)
 * 
 * Evaluation Rules:
 * - Monthly income ≤ 10,000 → Full Scholarship
 * - 10,000 < income ≤ 15,000 → Half Scholarship
 * - Income > 15,000 → Rejected
 * - If Savings Document (SAV) exists, thresholds increase by 20%
 * - If 3+ dependents, thresholds increase by additional 10%
 * - Duration: 1 year
 */
public class NeedScholarshipEvaluator extends ScholarshipEvaluator {

    // Constants for income thresholds (avoiding magic numbers)
    private static final double BASE_FULL_THRESHOLD = 10000.0;
    private static final double BASE_HALF_THRESHOLD = 15000.0;
    private static final double SAVINGS_ADJUSTMENT_FACTOR = 1.2;  // 20% increase
    private static final double DEPENDENTS_ADJUSTMENT_FACTOR = 1.1;  // 10% increase
    private static final int MINIMUM_DEPENDENTS_FOR_ADJUSTMENT = 3;
    private static final String SCHOLARSHIP_DURATION = "1 year";

    /**
     * Creates a new NeedScholarshipEvaluator instance.
     * This evaluator is stateless and can be instantiated as needed.
     */
    public NeedScholarshipEvaluator() {
        // Default constructor
    }

    /**
     * Helper method to calculate adjusted income thresholds based on applicant's situation.
     * Reduces code duplication by centralizing threshold calculation logic.
     * 
     * @param applicant The applicant whose thresholds to calculate
     * @return Array with [fullThreshold, halfThreshold]
     */
    private double[] calculateAdjustedThresholds(Applicant applicant) {
        double fullThreshold = BASE_FULL_THRESHOLD;
        double halfThreshold = BASE_HALF_THRESHOLD;
        
        // Adjust for Savings Document (SAV) - 20% increase
        if (applicant.hasDocument("SAV")) {
            fullThreshold *= SAVINGS_ADJUSTMENT_FACTOR;
            halfThreshold *= SAVINGS_ADJUSTMENT_FACTOR;
        }
        
        // Adjust for dependents (3+) - additional 10% increase
        if (applicant.getDependents() >= MINIMUM_DEPENDENTS_FOR_ADJUSTMENT) {
            fullThreshold *= DEPENDENTS_ADJUSTMENT_FACTOR;
            halfThreshold *= DEPENDENTS_ADJUSTMENT_FACTOR;
        }
        
        return new double[] { fullThreshold, halfThreshold };
    }

    @Override
    protected RejectionReason getSpecificRejectionReason(Application application) {
        Applicant applicant = application.getApplicant();
        double[] thresholds = calculateAdjustedThresholds(applicant);
        double halfThreshold = thresholds[1];
        double income = applicant.getFamilyIncome();
        
        // Check if income exceeds threshold
        if (income > halfThreshold) {
            return RejectionReason.FINANCIAL_STATUS_UNSTABLE;
        }
        
        return RejectionReason.NONE;
    }

    @Override
    public ScholarshipResultType getScholarshipType(Application application) {
        Applicant applicant = application.getApplicant();
        double[] thresholds = calculateAdjustedThresholds(applicant);
        double fullThreshold = thresholds[0];
        double halfThreshold = thresholds[1];
        double income = applicant.getFamilyIncome();
        
        if (income <= fullThreshold) {
            return ScholarshipResultType.FULL;
        } 
        else if (income <= halfThreshold) {
            return ScholarshipResultType.HALF;
        }
        
        return ScholarshipResultType.NONE;
    }

    @Override
    protected String getApplicationDuration(Application application) {
        // Need-based scholarships always have 1 year duration
        return SCHOLARSHIP_DURATION;
    }
}
