package application;

public class NeedApplication extends Application {
    private double familyIncome;
    private int dependents;

    public NeedApplication(String applicantID, String name, double gpa, double income) {
        super(applicantID, name, gpa, income);
        this.familyIncome = 0;
        this.dependents = 0;
    }

    public void setFamilyInfo(double familyIncome, int dependents) {
        this.familyIncome = familyIncome;
        this.dependents = dependents;
    }

    @Override
    public String getScholarshipCategory() {
        return "Need";
    }

}