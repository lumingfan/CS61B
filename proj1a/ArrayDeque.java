public class ArrayDeque<T>{
    // the front of deque
    private int lo;
    // the end of deque
    private int hi;
    // the size of deque
    private int size;
    // the real arr of deque
    private T[] items;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        lo = 0;
        hi = 0;
        size = 0;
    }

    private void resize(int capacity) {
        T[] newTs = (T[]) new Object[capacity];
        for (int i = 0; i < size; ++i) {
            newTs[i] = get(i);
        }
        lo = 0;
        hi = size - 1;
        items = newTs;
    }

    private int next(int i) {
        return (i + 1) % items.length;
    }

    private int last(int i) {
        return (i + items.length - 1) % items.length;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        boolean empty = size == 0;
        if (empty) {
            lo = 0;
            hi = 0;
        }
        return empty;
    }

    public void addLast(T item) {
        if (isEmpty()) {
            items[hi] = item;
            ++size;
            return ;
        }
        if (size == items.length) {
            resize(size << 1);
        }
        hi = next(hi);
        items[hi] = item;
        ++size;
    }

    public void addFirst(T item) {
        if (isEmpty()) {
            items[lo] = item;
            ++size;
            return ;
        }
        if (size == items.length) {
            resize(size << 1);
        }
        lo = last(lo);
        items[lo] = item;
        ++size;
    }
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T res = items[lo];
        items[lo] = null;
        lo = next(lo);
        --size;
        if (size < items.length / 4) {
            resize(items.length / 2);
        }
        return res;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T res = items[hi];
        items[hi] = null;
        hi = last(hi);
        --size;
        if (size < items.length / 4) {
            resize(items.length / 2);
        }
        return res;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[(lo + index) % items.length];
    }

    public void printDeque() {
        for (int i = 0, cur = lo; i < size; ++i, cur = next(cur)) {
            System.out.print(items[cur] + " ");
        }
        System.out.println();
    }
}
