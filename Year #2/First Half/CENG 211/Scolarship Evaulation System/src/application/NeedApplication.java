package application;

public class NeedApplication extends Application {
    private double familyIncome;
    private int dependents;

    public NeedApplication(String applicantID, String name, double gpa, double income,String transcriptStatus) {
        super(applicantID, name, gpa, income, transcriptStatus);
        this.familyIncome = 0;
        this.dependents = 0;
    }

    public void setFamilyInfo(double familyIncome, int dependents) {
        this.familyIncome = familyIncome;
        this.dependents = dependents;
    }

    public double getFamilyIncome(){
        return familyIncome;
    }

    public int getDependents(){
        return dependents;
    }

    @Override
    public String getScholarshipCategory() {
        return "Need";
    }

}