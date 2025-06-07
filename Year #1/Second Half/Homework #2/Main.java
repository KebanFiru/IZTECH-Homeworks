public class Main {
    public static void main(String[] args) {
        // Test your basic ticket functionality first
        System.out.println("=== Basic Ticket Test ===");
        Ticket ticket = new Ticket("A", "B", -1, 1);
        System.out.println(ticket.toString());
        System.out.println();

        // Test the homework functionality
        System.out.println("=== Homework CSV Processing ===");
        String csvFilePath = "Files/example_commands.csv";
        
        Command[] commands = FileIO.readCommands(csvFilePath);
        
        if (commands.length == 0) {
            System.out.println("No commands found in the file: " + csvFilePath);
            System.out.println("Please make sure the file exists and contains valid commands.");
            
            // If no CSV file, run a demo
            System.out.println("\n=== Running Demo Instead ===");
            runDemo();
            return;
        }
        
        CommandProcessor processor = new CommandProcessor();
        processor.processCommands(commands);
    }
    
    private static void runDemo() {
        CommandProcessor processor = new CommandProcessor();
        
        // Create demo commands
        Command[] demoCommands = {
            new Command("new", "John Doe", "Internet not working", "High"),
            new Command("new", "Jane Smith", "Payment issue", "Low"),
            new Command("new", "Mike Brown", "Router problem", "High"),
            new Command("new", "Sarah Lee", "Billing question", "Medium"),
            new Command("display", "priority"),
            new Command("display", "asc"),
            new Command("resolve"),
            new Command("resolve"),
            new Command("display", "priority"),
            new Command("history"),
            new Command("history", "asc")
        };
        
        processor.processCommands(demoCommands);
    }
}
