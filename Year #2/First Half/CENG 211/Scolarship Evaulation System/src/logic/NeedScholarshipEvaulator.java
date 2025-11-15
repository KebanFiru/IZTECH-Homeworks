package logic;

import java.util.ArrayList;

import application.Application;
import application.NeedApplication;

public class NeedScholarshipEvaulator extends ScholarshipEvaulator{

    public NeedScholarshipEvaulator(ArrayList<Application> allApplications){
        super(allApplications);
    }

    public NeedScholarshipEvaulator(NeedScholarshipEvaulator another){

        super(another);
    }

    @Override
    public String getApplicationStatus(Application application){

        String parentApplicationStatus = super.getApplicationStatus(application);

        NeedApplication needApplication;
        double fullThreshold = 10000.0;
        double halfThreshold = 15000.0;
        if(parentApplicationStatus.equals("Accepted")){
            if (application.hasDocument("SAV")) {
                fullThreshold *= 1.2;
                halfThreshold *= 1.2;
            }
            if(application instanceof NeedApplication){
                needApplication = (NeedApplication)application;

                if (needApplication.getDependents() >= 3.0) {
                    fullThreshold *= 1.1;  // 10% increase
                    halfThreshold *= 1.1;
                }

                double income = needApplication.getFamilyIncome();

                if (income <= fullThreshold) {
                    return "Accepted";
                }
                else if (income <= halfThreshold) {
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
