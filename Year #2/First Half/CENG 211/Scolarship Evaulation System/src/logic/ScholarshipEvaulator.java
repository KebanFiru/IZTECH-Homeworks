package logic;

import java.util.ArrayList;

import application.Application;

public abstract class ScholarshipEvaulator {

    ArrayList<Application> applications = new ArrayList<>();

    private String applicationStatus;
    private String applicationDuration;

    public ScholarshipEvaulator(ArrayList<Application> allApplications){

        this.applications = allApplications;
    }

    public ScholarshipEvaulator(ScholarshipEvaulator another){

    }

    public abstract String getApplicationStatus(Application application);

    public abstract String getApplicationDuration(Application application);

    public abstract String getScholarshipType(Application application);

    protected abstract void calculateApplicationStatus(Application application);

    protected abstract void calculateApplicationDuration(Application application);

    protected abstract void calculateScholarshipType(Application application);

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



