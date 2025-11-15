package application;

public class MeritApplication extends Application {

    public MeritApplication(String applicantID, String name, double gpa, double income,String transcriptStatus) {
        super(applicantID, name, gpa, income, transcriptStatus);
    }

    @Override
    public String getScholarshipCategory() {
        return "Merit";
    }
}