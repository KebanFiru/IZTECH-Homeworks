package logic;

import java.util.ArrayList;

import application.Application;
import application.Publication;

public abstract class ScholarshipEvaulator {

    protected ArrayList<Application> applications = new ArrayList<>();


    public ScholarshipEvaulator(ArrayList<Application> allApplications){

        this.applications = allApplications;
    }

    public ScholarshipEvaulator(ScholarshipEvaulator another){

        this.applications = new ArrayList<>(another.applications);
    }

    protected String getApplicationStatus(Application application){

        if (!application.hasDocument("ENR"))
            return "Rejected";
        if (!application.getTranscriptValidation())
            return "Rejected";
        if (application.getGpa() < 2.5)
            return "Rejected";

        return "Accepted";
    }

    protected double getAvarageImpact(Application application){
        if (application.getPublications().size() == 0) {
            return 0.0;
        }
        double totalImpact = 0.0;
        for (Publication publication : application.getPublications()) {
            totalImpact += publication.getImpactFactor();
        }
        return totalImpact / application.getPublications().size();
    }

    protected abstract String getApplicationDuration(Application application);

    protected abstract String getScholarshipType(Application application);

    private void sortApplicants(){
        for(int i = 0; i<applications.size(); i++){
            if(i != applications.size()-1){
                for(int j=i+1; j<applications.size(); j++){
                    if(Integer.parseInt(applications.get(j).getApplicantID()) > Integer.parseInt(applications.get(i).getApplicantID())){
                        Application tempApplication = applications.get(i);
                        applications.set(i, applications.get(j));
                        applications.set(j, tempApplication);

                    }
                }
            }

        }
    }
    public void getApplicants(){

        sortApplicants();
        for(Application applicant: applications){
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



