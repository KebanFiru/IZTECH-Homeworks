public interface IBag<T> {

    public boolean add(T newEntry);

    public boolean isEmpty();

    public boolean isFull();

    public T removeByIndex(int index);

    public int getFrequencyOf(T anEntry);

    public int getIndexOf(T anEntry);

    public boolean contains();

    public void clear();

    public void displayItems();

    public int getCurrentSize();

    public T[] toArray();


}
