package evaulator;

import  model.Applicant;

import java.util.ArrayList;

public class ResearchScholarshipEvaulator extends ScholarshipEvaulator{

    public ResearchScholarshipEvaulator(ArrayList<Applicant> allApplications){
        super(allApplications);
    }

    public ResearchScholarshipEvaulator(ResearchScholarshipEvaulator another){
        super(another);
    }

    @Override
    public String getApplicationStatus(Applicant application){

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
    public String getApplicationDuration(Applicant application){

    }

    @Override
    public String getScholarshipType(Applicant application){

    }
}