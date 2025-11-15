package application;

public class ResearchApplication extends Application {

    public ResearchApplication(Applicant applicant) {
        super(applicant);
    }

    @Override
    public String getScholarshipCategory() {
        return "Research";
    }
}