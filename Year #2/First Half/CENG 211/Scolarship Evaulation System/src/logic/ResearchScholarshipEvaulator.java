package logic;

import application.Application;

import java.util.ArrayList;

public class ResearchScholarshipEvaulator extends ScholarshipEvaulator{

    public ResearchScholarshipEvaulator(ArrayList<Application> allApplications){
        super(allApplications);
    }

    public ResearchScholarshipEvaulator(ResearchScholarshipEvaulator another){
        super(another);
    }

    @Override
    public String getApplicationStatus(Application application){

        String parentApplicationStatus = super.getApplicationStatus(application);

        if(parentApplicationStatus.equals("Accepted")) {
            if (application.hasPublicationOrGRP()){
                if (getAvarageImpact(application) >= 1.0){
                    return "Accepted";
                }
            }
        }
        return "Rejected";
    }

    @Override
    public String getApplicationDuration(Application application){

    }

    @Override
    public String getScholarshipType(Application application){

    }
}
