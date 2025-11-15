package evaulator;

import java.util.ArrayList;
import types.Application;

public class MeritScholarshipEvaulator extends ScholarshipEvaulator{


    public MeritScholarshipEvaulator(ArrayList<Application> allApplications){

        super(allApplications);
    }

    public MeritScholarshipEvaulator(MeritScholarshipEvaulator another){

        super(another);
    }

    @Override
    public String getApplicationStatus(Application application){

        String parentApplicationStatus = super.getApplicationStatus(application);

        if(parentApplicationStatus.equals("Accepted")){
            if (application.getGpa() >= 3.2){
                return "Accepted";

            }
            else if (application.getGpa() >= 3.0){
                return "Accepted";

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