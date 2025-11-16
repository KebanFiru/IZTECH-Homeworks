package types;

import evaluator.MeritScholarshipEvaluator;
import model.Applicant;

/**
 * Merit-Based Scholarship Application (ID prefix: 11).
 * Uses MeritScholarshipEvaluator for evaluation logic.
 */
public class MeritApplication extends Application {

    /**
     * Constructs a MeritApplication with the given applicant.
     * 
     * @param applicant the applicant for this merit scholarship
     * @throws IllegalArgumentException if applicant is null
     */
    public MeritApplication(Applicant applicant) {
        super(applicant);
        this.evaluator = new MeritScholarshipEvaluator();
    }

    /**
     * Copy constructor - creates a defensive copy of another MeritApplication.
     * 
     * @param other the merit application to copy from
     * @throws IllegalArgumentException if other is null
     */
    public MeritApplication(MeritApplication other) {
        super(other);
        this.evaluator = new MeritScholarshipEvaluator();
    }

    @Override
    public String getScholarshipCategory() {
        return "Merit";
    }
}