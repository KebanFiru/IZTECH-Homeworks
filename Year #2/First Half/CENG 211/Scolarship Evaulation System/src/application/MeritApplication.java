package application;

public class MeritApplication extends Application {

    public MeritApplication(String applicantID, String name, double gpa, double income) {
        super(applicantID, name, gpa, income);
    }

    @Override
    public String getScholarshipCategory() {
        return "Merit";
    }

    @Override
    public void evaluate() {
        if (!passesGeneralChecks()) {
            return;
        }

        if (gpa >= 3.20) {
            status = "Accepted";
            scholarshipType = "Full";
        }
        else if (gpa >= 3.00) {
            status = "Accepted";
            scholarshipType = "Half";
        }
        else {
            status = "Rejected";
            rejectionReason = "GPA below 3.0";
            return;
        }

        if (hasDocument("REC")) {
            durationInYears = 2;
        } else {
            durationInYears = 1;
        }
    }
}