public interface Deque<T> {
    void addLast(T item);
    void addFirst(T item);
    T removeLast();
    T removeFirst();
    boolean isEmpty();
    int size();
    void printDeque();
    T get(int index);
}
