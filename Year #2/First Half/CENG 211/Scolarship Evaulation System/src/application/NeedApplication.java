package application;

public class NeedApplication extends Application {

    public NeedApplication(Applicant applicant) {
        super(applicant);
    }

    public void setFamilyInfo(int familyIncome, int dependents) {
        this.applicant.setFamilyInfo(familyIncome, dependents);
    }

    @Override
    public String getScholarshipCategory() {
        return "Need";
    }
}