package logic;

import java.util.ArrayList;

public class ScholarshipEvaulator {

    ArrayList<Application> applications = new ArrayList<>();

    private String applicationStatus;
    private String applicationDuration;

    public ScholarshipEvaulator(ArrayList<Application> allApplications){


    }

    public ScholarshipEvaulator(ScholarshipEvaulator another){
        
    }

    public String getApplicationStatus(Application application){
        calculateApplicationStatus(application);
        return;
    }

    public String getApplicationDuration(Application application){
        calculateApplicationDuration(application);
        return;
    }

    public void getApplicants(){

        sortApplicants();
        for(Application applicant: applications){
            applicentData = String.format("Applicant ID: %d, Name; %s, Scholarship:%s, Status: %s, Type:%s,Duration: %s",
                                                                                                    applicant.getId(),
                                                                                                    applicant.getName(),
                                                                                                    applicant.getScholarship(),
                                                                                                    getApplicationStatus(applicant),
                                                                                                    applicant.getType(),
                                                                                                    getApplicationDuration(applicant));
            System.out.println(applicentData);
        }
    }

    private void calculateApplicationStatus(Application application){

    }

    private void calculateApplicationDuration(Application application){

    }

    private void sortApplicants(){

    }
}
