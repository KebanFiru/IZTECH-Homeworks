public class CommandProcessor {
    private GenericPriorityQueue<Ticket> ticketQueue;
    private GenericHistory<Ticket> resolvedHistory;

    public CommandProcessor() {
        this.ticketQueue = new GenericPriorityQueue<>();
        this.resolvedHistory = new GenericHistory<>();
    }

    public void processCommands(Command[] commands) {
        for (int i = 0; i < commands.length; i++) {
            Command command = commands[i];
            if (command == null) continue;

            switch (command.getType().toLowerCase()) {
                case "new":
                    addNewTicket(command);
                    break;
                case "resolve":
                    resolveTicket();
                    break;
                case "display":
                    displayTickets(command.getParameter());
                    break;
                case "history":
                    displayHistory(command.getParameter());
                    break;
            }
        }
    }

    private void addNewTicket(Command command) {
        Ticket ticket = new Ticket(command.getCustomerName(),
                command.getIssueDescription(),
                command.getPriority());
        ticketQueue.offer(ticket);
        System.out.println("Adding Ticket: " + ticket);
    }

    private void resolveTicket() {
        Ticket resolved = ticketQueue.poll();
        if (resolved != null) {
            resolvedHistory.add(resolved);
            System.out.println("Resolving Ticket:");
            System.out.println("Resolved: " + resolved);
        } else {
            System.out.println("No tickets to resolve.");
        }
        System.out.println();
    }

    private void displayTickets(String parameter) {
        Object[] ticketObjects = ticketQueue.getAll();

        if (ticketObjects.length == 0) {
            System.out.println("No active tickets.");
            return;
        }

        // Convert to Ticket array manually
        Ticket[] tickets = new Ticket[ticketObjects.length];
        for (int i = 0; i < ticketObjects.length; i++) {
            tickets[i] = (Ticket) ticketObjects[i];
        }

        if (parameter == null || parameter.equals("priority")) {
            System.out.println("--- Displaying Active Tickets (By Priority) ---");
            for (int i = 0; i < tickets.length; i++) {
                System.out.println((i + 1) + ". " + tickets[i]);
            }
        } else if (parameter.equals("asc")) {
            System.out.println("--- Displaying Active Tickets (By ASC - Oldest First) ---");
            sortByArrivalTime(tickets, true);
            for (int i = 0; i < tickets.length; i++) {
                System.out.println((i + 1) + ". " + tickets[i]);
            }
        } else if (parameter.equals("desc")) {
            System.out.println("--- Displaying Active Tickets (By DESC - Newest First) ---");
            sortByArrivalTime(tickets, false);
            for (int i = 0; i < tickets.length; i++) {
                System.out.println((i + 1) + ". " + tickets[i]);
            }
        }
        System.out.println();
    }

    private void sortByArrivalTime(Ticket[] tickets, boolean ascending) {
        for (int i = 0; i < tickets.length - 1; i++) {
            for (int j = 0; j < tickets.length - i - 1; j++) {
                boolean shouldSwap = ascending ?
                        tickets[j].getArrivalTime() > tickets[j + 1].getArrivalTime() :
                        tickets[j].getArrivalTime() < tickets[j + 1].getArrivalTime();

                if (shouldSwap) {
                    Ticket temp = tickets[j];
                    tickets[j] = tickets[j + 1];
                    tickets[j + 1] = temp;
                }
            }
        }
    }

    private void displayHistory(String parameter) {
        Object[] historyObjects = resolvedHistory.getAll();

        if (historyObjects.length == 0) {
            System.out.println("No resolved tickets in history.");
            return;
        }

        if (parameter == null) {
            System.out.println("--- Resolved Ticket History (Sorted by Customer Name) ---");
            resolvedHistory.displaySorted();
        } else if (parameter.equals("asc")) {
            System.out.println("--- Resolved Ticket History (ASC - Oldest First) ---");
            resolvedHistory.displayByArrivalTime(true);
        } else if (parameter.equals("desc")) {
            System.out.println("--- Resolved Ticket History (DESC - Newest First) ---");
            resolvedHistory.displayByArrivalTime(false);
        }
        System.out.println();
    }
}
