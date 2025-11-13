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

    @Override
    public void evaluate() {
        if (!passesGeneralChecks()) {
            return;
        }

        // Calculate adjusted thresholds
        double fullThreshold = 10000;
        double halfThreshold = 15000;

        if (hasDocument("SAV")) {
            fullThreshold *= 1.20;
            halfThreshold *= 1.20;
        }

        if (dependents >= 3) {
            fullThreshold *= 1.10;
            halfThreshold *= 1.10;
        }

        if (familyIncome <= fullThreshold) {
            status = "Accepted";
            scholarshipType = "Full";
        } else if (familyIncome <= halfThreshold) {
            status = "Accepted";
            scholarshipType = "Half";
        } else {
            status = "Rejected";
            rejectionReason = "Financial status unstable";
            return;
        }

        durationInYears = 1;
    }
}