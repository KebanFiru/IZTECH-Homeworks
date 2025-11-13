package logic;

import java.util.ArrayList;

import application.Application;

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
            String applicentData = String.format("Applicant ID: %d, Name; %s, Scholarship:%s, Status: %s, Type:%s,Duration: %s",
                                        applicant.getApplicantID(),
                                        applicant.getName(),
                                        applicant.getScholarshipCategory(),
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
}



