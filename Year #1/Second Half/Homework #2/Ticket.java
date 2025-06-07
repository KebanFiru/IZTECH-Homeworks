public class Ticket implements Comparable<Ticket>{

    private String customerName;
    private String issueDescription;
    private int priority;
    private int arrivalTime;

    private String priorityString;

    public Ticket(String customerName, String issueDescription, int priority, int arrivalTime){

        this.customerName = customerName;
        this.issueDescription = issueDescription;
        this.priority = priority;
        this.arrivalTime = arrivalTime;

    }

    @Override
    public int compareTo(Ticket ticket){

        return Integer.compare(this.priority, ticket.priority);

    }

    public String toString(){

        switch(this.priority){
            case -1:
                priorityString = "Low";
                break;
            case 0:
                priorityString = "Mid";
                break;
            case 1:
                priorityString = "High";
                break;
        }

        return String.format("%s-%s[%s]", this.customerName,this.issueDescription, priorityString);

    }


}
