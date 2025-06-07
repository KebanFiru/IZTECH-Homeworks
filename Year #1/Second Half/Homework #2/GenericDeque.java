public class GenericDeque<T> implements Deque<T> {

    private Node<T> front;
    private Node<T> rear;
    private int size;

    public GenericDeque(){

        this.front = null;
        this.rear = null;
        size = 0;

    }

    public void addFront(T data){

        Node<T> newNode = new Node<>(data);

        if(isEmpty()){

            front = rear = newNode;

        }

        else{

            newNode.next = front;
            front.prev = newNode;
            front = newNode;

        }

        size++;

    }

    public void addRear(T data){

        Node<T> newNode = new Node<>(data);

        if(isEmpty()){

            front = rear = newNode;

        }

        else{

            rear.next = newNode;
            newNode.prev = rear;
            rear = newNode;

        }

        size++;

    }

    public T removeFront(){

        if(isEmpty()){

            throw new RuntimeException("Deque is empty");

        }

        T removed = front.data;
        front = front.next;
        if(front == null){

            rear = null;

        }
        else{

            front.prev = null;

        }
        size--;
        return removed;

    }

    public T removeRear(){

        if(isEmpty()){

            throw new RuntimeException("Deque is empty");

        }

        T removed = rear.data;
        rear = rear.prev;
        if(rear == null){

            front = null;

        }
        else{

            rear.next = null;

        }
        size--;
        return removed;

    }

    public T peekFront(){

        if(isEmpty()){

            throw new RuntimeException("Deque is empty");

        }
        return front.data;

    }

    public T peekRear(){

        if(isEmpty()){

            throw new RuntimeException("Deque is empty");

        }
        return rear.data;

    }

    public boolean isEmpty(){

        return front == null;

    }

    public int size(){

        return size;

    }

}
