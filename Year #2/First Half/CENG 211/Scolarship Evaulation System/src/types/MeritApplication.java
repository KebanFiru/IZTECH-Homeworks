package types;

import evaluator.MeritScholarshipEvaluator;
import model.Applicant;

/**
 * Merit-Based Scholarship Application (ID prefix: 11).
 * Uses MeritScholarshipEvaluator for evaluation logic.
 */
public class MeritApplication extends Application {

    public MeritApplication(Applicant applicant) {
        super(applicant);
        this.evaluator = new MeritScholarshipEvaluator();
    }

    @Override
    public String getScholarshipCategory() {
        return "Merit";
    }
}