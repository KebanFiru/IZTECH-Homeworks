package logic;

import java.util.ArrayList;
import application.Application;

public class ScholarshipEvaulator {

    ArrayList<Application> applications = new ArrayList<>();

    public ScholarshipEvaulator(ArrayList<Application> allApplications){

    }

    public ScholarshipEvaulator(ScholarshipEvaulator another){
        
    }


    public void getApplicants(){

        sortApplicants();
        for(Application applicant: applications){
            System.out.println(applicant);
        }
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
