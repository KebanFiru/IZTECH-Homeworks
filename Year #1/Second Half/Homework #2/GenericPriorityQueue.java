public class GenericPriorityQueue<T extends Comparable<T>> implements PriorityQueue<T> {
    private GenericQueue<T> highPriorityQueue;   // priority = 1
    private GenericQueue<T> mediumPriorityQueue; // priority = 0
    private GenericQueue<T> lowPriorityQueue;    // priority = -1

    public GenericPriorityQueue() {
        this.highPriorityQueue = new GenericQueue<>();
        this.mediumPriorityQueue = new GenericQueue<>();
        this.lowPriorityQueue = new GenericQueue<>();
    }


    public void offer(T data) {
        if (data instanceof Ticket) {
            Ticket ticket = (Ticket) data;
            switch (ticket.getPriority()) {
                case 1: // High
                    highPriorityQueue.enqueue(data);
                    break;
                case 0: // Medium
                    mediumPriorityQueue.enqueue(data);
                    break;
                case -1: // Low
                    lowPriorityQueue.enqueue(data);
                    break;
                default:
                    mediumPriorityQueue.enqueue(data);
            }
        } else {
            // For non-Ticket items, use toString() to determine priority
            String str = data.toString().toLowerCase();
            if (str.contains("high")) {
                highPriorityQueue.enqueue(data);
            } else if (str.contains("medium") || str.contains("mid")) {
                mediumPriorityQueue.enqueue(data);
            } else {
                lowPriorityQueue.enqueue(data);
            }
        }
    }


    public T poll() {
        if (!highPriorityQueue.isEmpty()) {
            return highPriorityQueue.dequeue();
        } else if (!mediumPriorityQueue.isEmpty()) {
            return mediumPriorityQueue.dequeue();
        } else if (!lowPriorityQueue.isEmpty()) {
            return lowPriorityQueue.dequeue();
        }
        return null;
    }

    @Override
    public T peek() {
        if (!highPriorityQueue.isEmpty()) {
            return highPriorityQueue.peek();
        } else if (!mediumPriorityQueue.isEmpty()) {
            return mediumPriorityQueue.peek();
        } else if (!lowPriorityQueue.isEmpty()) {
            return lowPriorityQueue.peek();
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return highPriorityQueue.isEmpty() &&
                mediumPriorityQueue.isEmpty() &&
                lowPriorityQueue.isEmpty();
    }

    @Override
    public int size() {
        return highPriorityQueue.size() + mediumPriorityQueue.size() + lowPriorityQueue.size();
    }

    public Object[] getAll() {
        int totalSize = size();
        Object[] result = new Object[totalSize];
        int index = 0;

        // Add high priority items first
        Object[] highItems = highPriorityQueue.getAll();
        for (int i = 0; i < highItems.length; i++) {
            if (highItems[i] != null) {
                result[index++] = highItems[i];
            }
        }

        // Add medium priority items
        Object[] mediumItems = mediumPriorityQueue.getAll();
        for (int i = 0; i < mediumItems.length; i++) {
            if (mediumItems[i] != null) {
                result[index++] = mediumItems[i];
            }
        }

        // Add low priority items
        Object[] lowItems = lowPriorityQueue.getAll();
        for (int i = 0; i < lowItems.length; i++) {
            if (lowItems[i] != null) {
                result[index++] = lowItems[i];
            }
        }

        return result;
    }
}
