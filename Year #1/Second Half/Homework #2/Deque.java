public interface Deque <T>{

    void addFront(T data);

    void addRear(T data);

    T removeFront();

    T removeRear();

    T peekFront();

    T peekRear();

    boolean isEmpty();

    int size();

}
