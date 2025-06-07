public class GenericQueue <T> implements Queue<T>{

    private Node<T> front;
    private Node<T> rear;
    private int size;

    public GenericQueue(){

        this.front = null;
        this.rear = null;
        this.size = 0;

    }

    @Override
    public void enqueue(T data){

        Node<T> newNode = new Node<>(data);
        if(isEmpty()){

            front = rear = newNode;

        }
        else{

            rear.next = newNode;
            rear = newNode;

        }

        size++;
    }

    @Override
    public T dequeue(){

        if(!isEmpty()) {

            throw new RuntimeException("Queue is empty");

        }

            T removedData = front.data;
            front = front.next;
            if(front == null){

                rear = null;

            }

            size--;
            return removedData;

    }

    @Override
    public T peek(){

        if(isEmpty()){

            throw new RuntimeException("Queue is empty");

        }

        return front.data;

    }

    @Override
    public boolean isEmpty(){

        return front == null;

    }

    public int size(){

        return size;

    }

}
