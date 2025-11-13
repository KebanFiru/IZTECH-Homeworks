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
    public void evaluate() {
        if (!passesGeneralChecks()) {
            return;
        }

        if (publications.isEmpty() && !hasDocument("GRP")) {
            status = "Rejected";
            rejectionReason = "Missing publication or proposal";
            return;
        }

        double avgImpactFactor = 0.0;
        if (!publications.isEmpty()) {
            double totalImpact = 0.0;
            for (Publication pub : publications) {
                totalImpact += pub.getImpactFactor();
            }
            avgImpactFactor = totalImpact / publications.size();
        }

        if (avgImpactFactor >= 1.50) {
            status = "Accepted";
            scholarshipType = "Full";
            durationInYears = 1;
        }
        else if (avgImpactFactor >= 1.00) {
            status = "Accepted";
            scholarshipType = "Half";
            durationInYears = 0;
        }
        else {
            status = "Rejected";
            rejectionReason = "Publication impact too low";
            return;
        }

        if (hasDocument("RSV")) {
            durationInYears += 1;
        }
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