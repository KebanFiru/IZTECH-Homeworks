public class Command 
{
    private String type;
    private String customerName;
    private String issueDescription;
    private String priority;
    private String parameter;
    
    public Command(String type) 
  {
        this.type = type;
   }
    
    public Command(String type, String parameter)
  {
        this.type = type;
        this.parameter = parameter;
   }
    
    public Command(String type, String customerName, String issueDescription, String priority)
  {
        this.type = type;
        this.customerName = customerName;
        this.issueDescription = issueDescription;
        this.priority = priority;
    }
    
    public String getType() { return type; }
    public String getCustomerName() { return customerName; }
    public String getIssueDescription() { return issueDescription; }
    public String getPriority() { return priority; }
    public String getParameter() { return parameter; }
}
