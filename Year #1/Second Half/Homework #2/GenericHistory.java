public class GenericHistory<T> {
    private Node<T> head;
    private int size;

    public GenericHistory() {
        this.head = null;
        this.size = 0;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
            newNode.prev = current;
        }
        size++;
    }

    public Object[] getAll() {
        Object[] array = new Object[size];
        Node<T> current = head;
        int index = 0;
        while (current != null) {
            array[index++] = current.data;
            current = current.next;
        }
        return array;
    }

    public void display() {
        Node<T> current = head;
        int index = 1;
        while (current != null) {
            System.out.println(index + ". " + current.data);
            current = current.next;
            index++;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    // Custom bubble sort for sorting by customer name
    private void sortByCustomerName(Object[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] instanceof Ticket && array[j + 1] instanceof Ticket) {
                    Ticket t1 = (Ticket) array[j];
                    Ticket t2 = (Ticket) array[j + 1];
                    if (t1.getCustomerName().compareTo(t2.getCustomerName()) > 0) {
                        Object temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                    }
                }
            }
        }
    }

    public void displaySorted() {
        Object[] sortedArray = getAll();
        sortByCustomerName(sortedArray);
        for (int i = 0; i < sortedArray.length; i++) {
            System.out.println((i + 1) + ". " + sortedArray[i]);
        }
    }

    public void displayByArrivalTime(boolean ascending) {
        Object[] array = getAll();
        if (!ascending) {
            // Reverse the array for descending order
            for (int i = 0; i < array.length / 2; i++) {
                Object temp = array[i];
                array[i] = array[array.length - 1 - i];
                array[array.length - 1 - i] = temp;
            }
        }
        for (int i = 0; i < array.length; i++) {
            System.out.println((i + 1) + ". " + array[i]);
        }
    }
}
