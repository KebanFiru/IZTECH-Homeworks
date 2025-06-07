public class Ticket implements Comparable<Ticket> {
    private String customerName;
    private String issueDescription;
    private int priority;
    private int arrivalTime;
    private String priorityString;

    public Ticket(String customerName, String issueDescription, int priority, int arrivalTime) {
        this.customerName = customerName;
        this.issueDescription = issueDescription;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
    }

    // Constructor for string priority conversion (for homework compatibility)
    public Ticket(String customerName, String issueDescription, String priorityStr) {
        this.customerName = customerName;
        this.issueDescription = issueDescription;
        this.priority = convertPriorityFromString(priorityStr);
        this.arrivalTime = (int) System.currentTimeMillis() % 100000; // Keep it as int
    }

    private int convertPriorityFromString(String priorityStr) {
        if (priorityStr == null) return 0;
        String lower = priorityStr.toLowerCase().trim();
        switch (lower) {
            case "high": return 1;
            case "medium": case "mid": return 0;
            case "low": return -1;
            default: return 0;
        }
    }

    @Override
    public int compareTo(Ticket ticket) {
        // Higher priority first (1 > 0 > -1), then FIFO within same priority
        int priorityComparison = Integer.compare(ticket.priority, this.priority);
        if (priorityComparison != 0) {
            return priorityComparison;
        }
        return Integer.compare(this.arrivalTime, ticket.arrivalTime);
    }

    @Override
    public String toString() {
        switch (this.priority) {
            case -1:
                priorityString = "Low";
                break;
            case 0:
                priorityString = "Mid";
                break;
            case 1:
                priorityString = "High";
                break;
            default:
                priorityString = "Unknown";
        }
        return String.format("%s-%s[%s]", this.customerName, this.issueDescription, priorityString);
    }

    // Getters needed for homework functionality
    public String getCustomerName() { return customerName; }
    public String getIssueDescription() { return issueDescription; }
    public int getPriority() { return priority; }
    public int getArrivalTime() { return arrivalTime; }
}
