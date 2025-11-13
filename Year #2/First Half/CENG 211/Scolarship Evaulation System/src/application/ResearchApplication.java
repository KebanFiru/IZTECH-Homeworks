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
                .append(", Scholarship: ").append(getScholarshipCategory())
                .append(", Status: ").append(status);

        if (status.equals("Accepted")) {
            result.append(", Type: ").append(scholarshipType)
                    .append(", Duration: ");

            if (durationInYears == 0) {
                result.append("6 months");
            } else {
                result.append(durationInYears)
                        .append(durationInYears == 1 ? " year" : " years");
            }
        } else {
            result.append(", Reason: ").append(rejectionReason);
        }

        return result.toString();
    }
}