package types;

import evaluator.ResearchScholarshipEvaluator;
import model.Applicant;

/**
 * Research Grant Application (ID prefix: 33).
 * Uses ResearchScholarshipEvaluator for evaluation logic.
 */
public class ResearchApplication extends Application {

    public ResearchApplication(Applicant applicant) {
        super(applicant);
        this.evaluator = new ResearchScholarshipEvaluator();
    }

    @Override
    public String getScholarshipCategory() {
        return "Research";
    }
}