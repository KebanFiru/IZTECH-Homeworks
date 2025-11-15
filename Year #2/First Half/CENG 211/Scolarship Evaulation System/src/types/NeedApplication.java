package types;

import evaluator.NeedScholarshipEvaluator;
import model.Applicant;

/**
 * Need-Based Scholarship Application (ID prefix: 22).
 * Uses NeedScholarshipEvaluator for evaluation logic.
 */
public class NeedApplication extends Application {

    public NeedApplication(Applicant applicant) {
        super(applicant);
        this.evaluator = new NeedScholarshipEvaluator();
    }

    public void setFamilyInfo(int familyIncome, int dependents) {
        this.applicant.setFamilyInfo(familyIncome, dependents);
    }

    @Override
    public String getScholarshipCategory() {
        return "Need";
    }
}