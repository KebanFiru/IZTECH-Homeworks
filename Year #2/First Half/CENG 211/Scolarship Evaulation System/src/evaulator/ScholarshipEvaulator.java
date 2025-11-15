package evaulator;

import java.util.ArrayList;

import model.Applicant;
import model.Publication;

public abstract class ScholarshipEvaulator {

    protected ArrayList<Applicant> applications = new ArrayList<>();


    public ScholarshipEvaulator(ArrayList<Applicant> allApplications){

        this.applications = allApplications;
    }

    public ScholarshipEvaulator(ScholarshipEvaulator another){

        this.applications = new ArrayList<>(another.applications);
    }

    protected String getApplicationStatus(Applicant application){

        if (!application.hasDocument("ENR"))
            return "Rejected";
        if (!application.getTranscriptValidation())
            return "Rejected";
        if (application.getGpa() < 2.5)
            return "Rejected";

        return "Accepted";
    }

    protected double getAvarageImpact(Applicant application){
        if (application.getPublications().size() == 0) {
            return 0.0;
        }
        double totalImpact = 0.0;
        for (Publication publication : application.getPublications()) {
            totalImpact += publication.getImpactFactor();
        }
        return totalImpact / application.getPublications().size();
    }

    protected abstract String getApplicationDuration(Applicant application);

    protected abstract String getScholarshipType(Applicant application);

    private void sortApplicants(){
        for(int i = 0; i<applications.size(); i++){
            if(i != applications.size()-1){
                for(int j=i+1; j<applications.size(); j++){
                    if(Integer.parseInt(applications.get(j).getApplicantID()) > Integer.parseInt(applications.get(i).getApplicantID())){
                        Applicant tempApplication = applications.get(i);
                        applications.set(i, applications.get(j));
                        applications.set(j, tempApplication);

                    }
                }
            }

        }
    }
    public void getApplicants(){

        sortApplicants();
        for(Applicant applicant: applications){
            String applicentData = String.format("Applicant ID: %d, Name; %s, Scholarship:%s, Status: %s, Type:%s,Duration: %s",
                    applicant.getApplicantID(),
                    applicant.getName(),
                    applicant.getScholarshipCategory(),
                    getApplicationStatus(applicant),
                    getScholarshipType(applicant),
                    getApplicationDuration(applicant));
            System.out.println(applicentData);
        }
    }
}