/** the implements of deque
 *  using circular array topology
 *  accept any generic type
 *
 *  API:
 *      public void addFirst(T item)    :   Adds an item of type T to the front of the deque
 *      public void addLast(T item)     :   Adds an item of type T to the back of the deque
 *      public boolean isEmpty()        :   Returns true if deque is empty, false otherwise
 *      public int size()               :   Returns the number of items in the deque
 *      public void printDeque()        :   Prints the items in the deque from first to last, separated by a space
 *      public T removeFirst()          :   Removes and returns the item at the front of the deque, If no sush item exists, return null
 *      public T removeLast()           :   Removes and returns the item at the back of the deque, if no such item exists, returns nulla
 *      public T get(int index)         :   get the item at the given index, where 0 is the front, 1 is the next item, and so forth, if no such item exists, returns null
 *      public ArrayDeque()             :   Creates an empty deque
 *
 */


public class ArrayDeque<Item>{
    // the front of deque
    private int lo;
    // the end of deque
    private int hi;
    // the size of deque
    private int size;
    // the real arr of deque
    private Item[] items;

    public ArrayDeque() {
        items = (Item[]) new Object[8];
        lo = 0;
        hi = 0;
        size = 0;
    }

    private void resize(int capacity) {
        Item[] newItems = (Item[]) new Object[capacity];
        for (int i = 0; i < size; ++i) {
            newItems[i] = get(i);
        }
        lo = 0;
        hi = size - 1;
        items = newItems;
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

    public void addLast(Item item) {
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

    public void addFirst(Item item) {
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
    public Item removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Item res = items[lo];
        items[lo] = null;
        lo = next(lo);
        --size;
        if (size < items.length / 4) {
            resize(items.length / 2);
        }
        return res;
    }

    public Item removeLast() {
        if (isEmpty()) {
            return null;
        }
        Item res = items[hi];
        items[hi] = null;
        hi = last(hi);
        --size;
        if (size < items.length / 4) {
            resize(items.length / 2);
        }
        return res;
    }

    public Item get(int index) {
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
