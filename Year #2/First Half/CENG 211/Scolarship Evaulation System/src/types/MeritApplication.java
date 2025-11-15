package types;

import model.Applicant;

public class MeritApplication extends Application {

    public MeritApplication(Applicant applicant) {
        super(applicant);
    }

    @Override
    public String getScholarshipCategory() {
        return "Merit";
    }
}