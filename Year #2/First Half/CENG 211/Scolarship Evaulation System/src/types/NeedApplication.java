package types;

import evaluator.NeedScholarshipEvaluator;
import model.Applicant;

/**
 * Need-Based Scholarship Application (ID prefix: 22).
 * Uses NeedScholarshipEvaluator for evaluation logic.
 */
public class NeedApplication extends Application {

    /**
     * Constructs a NeedApplication with the given applicant.
     * 
     * @param applicant the applicant for this need-based scholarship
     * @throws IllegalArgumentException if applicant is null
     */
    public NeedApplication(Applicant applicant) {
        super(applicant);
        this.evaluator = new NeedScholarshipEvaluator();
    }

    /**
     * Copy constructor - creates a defensive copy of another NeedApplication.
     * 
     * @param other the need application to copy from
     * @throws IllegalArgumentException if other is null
     */
    public NeedApplication(NeedApplication other) {
        super(other);
        this.evaluator = new NeedScholarshipEvaluator();
    }

    /**
     * Sets the family information for need-based evaluation.
     * 
     * @param familyIncome the monthly family income
     * @param dependents the number of dependents
     */
    public void setFamilyInfo(int familyIncome, int dependents) {
        this.applicant.setFamilyInfo(familyIncome, dependents);
    }

    @Override
    public String getScholarshipCategory() {
        return "Need";
    }
}