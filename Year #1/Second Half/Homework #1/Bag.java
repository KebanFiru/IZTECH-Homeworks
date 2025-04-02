public class Bag<T> implements IBag<T> {
    private T[] bag;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 25;
    private static final int MAX_CAPACITY = 10000;

    public Bag() {
        this.bag = (T[]) new Object[DEFAULT_CAPACITY];
        this.numberOfEntries = 0;
    }

    public Bag(int capacity) {
        if (capacity <= MAX_CAPACITY) {
            this.bag = (T[]) new Object[capacity];
        } else {
            this.bag = (T[]) new Object[MAX_CAPACITY];
        }
        this.numberOfEntries = 0;
    }

    public boolean add(T newEntry) {
        if (isFull()) {
            doubleCapacity();
        }
        bag[numberOfEntries] = newEntry;
        numberOfEntries++;
        return true;
    }

    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    public boolean isFull() {
        return numberOfEntries >= bag.length;
    }

    public T removeByIndex(int index) {
        if (index < 0 || index >= numberOfEntries) {
            return null;
        }
        
        T result = bag[index];
        
        for (int i = index; i < numberOfEntries - 1; i++) {
            bag[i] = bag[i + 1];
        }
        
        bag[numberOfEntries - 1] = null;
        numberOfEntries--;
        
        return result;
    }

    public boolean remove(T anEntry) {
        int index = getIndexOf(anEntry);
        if (index < 0) {
            return false;
        }

        removeByIndex(index);
        return true;
    }

    public T remove() {
        if (isEmpty()) {
            return null;
        }

        T result = bag[numberOfEntries - 1];
        bag[numberOfEntries - 1] = null;
        numberOfEntries--;

        return result;
    }

    public int getFrequencyOf(T anEntry) {
        int count = 0;
        for (int i = 0; i < numberOfEntries; i++) {
            if (bag[i].equals(anEntry)) {
                count++;
            }
        }
        return count;
    }

    public int getIndexOf(T anEntry) {
        for (int i = 0; i < numberOfEntries; i++) {
            if (bag[i].equals(anEntry)) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(T anEntry) {
        return getIndexOf(anEntry) >= 0;
    }

    public void clear() {
        for (int i = 0; i < numberOfEntries; i++) {
            bag[i] = null;
        }
        numberOfEntries = 0;
    }

    public void displayItems() {
        for (int i = 0; i < numberOfEntries; i++) {
            System.out.println(bag[i]);
        }
    }

    public int getCurrentSize() {
        return numberOfEntries;
    }

    public T[] toArray() {
        T[] result = (T[]) new Object[numberOfEntries];
        for (int i = 0; i < numberOfEntries; i++) {
            result[i] = bag[i];
        }
        return result;
    }

    private void doubleCapacity() {
        int newCapacity = 2 * bag.length;
        if (newCapacity <= MAX_CAPACITY) {
            T[] newBag = (T[]) new Object[newCapacity];
            for (int i = 0; i < numberOfEntries; i++) {
                newBag[i] = bag[i];
            }
            bag = newBag;
        }
    }
}

