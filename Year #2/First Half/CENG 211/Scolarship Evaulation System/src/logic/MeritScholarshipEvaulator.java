package logic;

import java.util.ArrayList;
import application.Application;

public class MeritScholarshipEvaulator extends ScholarshipEvaulator{


    public MeritScholarshipEvaulator(ArrayList<Application> allApplications){
        super(allApplications);
    }

    public MeritScholarshipEvaulator(MeritScholarshipEvaulator another){

        super(another);

    }

    public String getApplicationStatus(Application application){

        
    }

    public String getApplicationDuration(Application application){

    }

    public String getScholarshipType(Application application){

    }

    protected void calculateApplicationStatus(Application application){

    }

    protected void calculateApplicationDuration(Application application){

    }

    protected void calculateScholarshipType(Application application){

    }

}
