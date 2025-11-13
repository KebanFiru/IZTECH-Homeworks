package application;

public class ResearchApplication extends Application {

    public ResearchApplication(String applicantID, String name, double gpa, double income) {
        super(applicantID, name, gpa, income);
    }

    @Override
    public String getScholarshipCategory() {
        return "Research";
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Applicant ID: ").append(applicantID)
                .append(", Name: ").append(name)
                .append(", Scholarship: ").append(getScholarshipCategory());

        return result.toString();
    }
}