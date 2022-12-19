public interface Deque<T> {
    public void addLast(T item);
    public void addFirst(T item);
    public T removeLast();
    public T removeFirst();
    public boolean isEmpty();
    public int size();
    public void printDeque();
    public T get(int index);
}