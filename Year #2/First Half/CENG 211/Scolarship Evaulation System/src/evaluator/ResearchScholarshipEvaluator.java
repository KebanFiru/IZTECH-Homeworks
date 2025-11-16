package evaluator;

import model.Applicant;
import types.Application;
import types.RejectionReason;
import types.ScholarshipResultType;

/**
 * Evaluator for research grant applications.
 * <p>
 * <b>Evaluation Criteria:</b>
 * <ul>
 *   <li>Must have at least one publication (P) or grant proposal (GRP)</li>
 *   <li>Average Impact Factor ≥ 1.50: Full Scholarship</li>
 *   <li>1.00 ≤ Impact Factor &lt; 1.50: Half Scholarship</li>
 *   <li>Impact Factor &lt; 1.00: Rejected</li>
 * </ul>
 * <p>
 * <b>Base Duration Rules:</b>
 * <ul>
 *   <li>Full Scholarship: 12 months (1 year)</li>
 *   <li>Half Scholarship: 6 months</li>
 * </ul>
 * <p>
 * <b>Duration Extensions:</b>
 * <ul>
 *   <li>Research Supervisor Approval (RSV): +12 months extension</li>
 * </ul>
 * <p>
 * This evaluator is stateless and thread-safe.
 * 
 * @see types.ResearchApplication
 */
public class ResearchScholarshipEvaluator extends ScholarshipEvaluator {

    /** Minimum average impact factor for full scholarship */
    private static final double FULL_SCHOLARSHIP_IMPACT = 1.50;
    
    /** Minimum average impact factor for half scholarship */
    private static final double HALF_SCHOLARSHIP_IMPACT = 1.00;
    
    /** Base duration for full scholarship (in months) */
    private static final int FULL_SCHOLARSHIP_BASE_MONTHS = 12;
    
    /** Base duration for half scholarship (in months) */
    private static final int HALF_SCHOLARSHIP_BASE_MONTHS = 6;
    
    /** Extension duration for research supervisor approval (in months) */
    private static final int SUPERVISOR_APPROVAL_EXTENSION_MONTHS = 12;
    
    /** Conversion factor for months to years */
    private static final int MONTHS_PER_YEAR = 12;

    /**
     * Constructs a research scholarship evaluator.
     */
    public ResearchScholarshipEvaluator() {
        super();
    }

    /**
     * Copy constructor (for API consistency).
     * <p>
     * Since evaluators are stateless, this simply creates a new instance.
     * 
     * @param other the evaluator to copy (unused)
     */
    public ResearchScholarshipEvaluator(ResearchScholarshipEvaluator other) {
        super();
    }

    /**
     * Checks research-specific rejection criteria.
     * Requires at least one publication or GRP, and minimum impact factor of 1.0.
     */
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

    /**
     * Determines scholarship type based on average publication impact factor.
     * Full if impact >= 1.50, Half if impact >= 1.00.
     */
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

    /**
     * Calculates duration based on scholarship type and RSV document.
     * Base: Full = 12 months, Half = 6 months. RSV adds +12 months.
     */
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
     * Formats duration from months to human-readable string.
     * <p>
     * Conversion rules:
     * <ul>
     *   <li>Exact years: "X year" or "X years"</li>
     *   <li>Mixed: "X year Y months" or "X years Y months"</li>
     *   <li>Months only: "Y months"</li>
     * </ul>
     * 
     * @param months total duration in months
     * @return formatted duration string
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
