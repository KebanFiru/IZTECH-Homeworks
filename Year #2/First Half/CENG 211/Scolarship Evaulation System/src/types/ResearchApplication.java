package types;

import evaluator.ResearchScholarshipEvaluator;
import model.Applicant;

/**
 * Research Grant Application (ID prefix: 33).
 * Uses ResearchScholarshipEvaluator for evaluation logic.
 */
public class ResearchApplication extends Application {

    /**
     * Constructs a ResearchApplication with the given applicant.
     * 
     * @param applicant the applicant for this research grant
     * @throws IllegalArgumentException if applicant is null
     */
    public ResearchApplication(Applicant applicant) {
        super(applicant);
        this.evaluator = new ResearchScholarshipEvaluator();
    }

    /**
     * Copy constructor - creates a defensive copy of another ResearchApplication.
     * 
     * @param other the research application to copy from
     * @throws IllegalArgumentException if other is null
     */
    public ResearchApplication(ResearchApplication other) {
        super(other);
        this.evaluator = new ResearchScholarshipEvaluator();
    }

    @Override
    public String getScholarshipCategory() {
        return "Research";
    }
}