public interface PriorityQueue<T> {

    void enqueue(T ticket);

    T dequeue();

    T peek();

    boolean isEmpty();

    int size();

}
