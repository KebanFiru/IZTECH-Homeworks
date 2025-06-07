public class GenericPriorityQueue <T extends Comparable<T>> implements PriorityQueue<T>{

    private T[] heap;
    private int size;

    public GenericPriorityQueue(){

        heap = (T[]) new Comparable[10];
        size = 0;

    }

    public void enqueue(T element){

        if(size == heap.length){

            resize();

        }

        heap[size] = element;
        heapifyUp(size);
        size++;

    }

    public T dequeue(){

        if(isEmpty()){

            throw new RuntimeException("Queue is empty");

        }

        T top = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        return top;

    }

    public T peek(){

        if(isEmpty()){

            throw new RuntimeException("Queue is empty");

        }

        return heap[0];

    }

    public boolean isEmpty(){

        return size == 0;

    }

    public int size(){

        return size;

    }

    private void heapifyUp(int index){

        while(index > 0 ){

            int parent = (index - 1) / 2;
            if(heap[index].compareTo(heap[parent]) < 0){

                swap(index, parent);
                index = parent;

            }
            else{

                break;

            }

        }

    }

    private void heapifyDown(int index){

        int left, right, smallest;
        while(index < size){

            smallest = index;
            left = 2 * index + 1;
            right = 2* index + 2;

            if(left < size && heap[left].compareTo(heap[smallest]) < 0){

                smallest = left;

            }
            if(right < size && heap[right].compareTo(heap[smallest]) < 0){

                smallest = right;

            }
            if(smallest != index){

                swap(index, smallest);
                index = smallest;

            }
            else{

                break;

            }

        }

    }

    private void swap(int i, int j){

        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;

    }

    private void resize(){

        T[] newHeap = (T[]) new Comparable[heap.length * 2];
        for(int i=0; i< size; i++){

            newHeap[i] = heap[i];

        }
        heap = newHeap;

    }

}
