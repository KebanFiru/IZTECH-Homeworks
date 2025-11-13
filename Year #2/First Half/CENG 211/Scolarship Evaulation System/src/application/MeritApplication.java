package application;

public class MeritApplication extends Application {

    public MeritApplication(String applicantID, String name, double gpa, double income) {
        super(applicantID, name, gpa, income);
    }

    @Override
    public String getScholarshipCategory() {
        return "Merit";
    }
}