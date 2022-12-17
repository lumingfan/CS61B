/** the implements of deque
 *  using circular sentinel topology
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
 *      public T get(int index)         :   Using iteration to get the item at the given index, where 0 is the front, 1 is the next item, and so forth, if no such item exists, returns null
*       public LinkedListDeque()        :   Creates an empty linked list deque
*       public T getRecursive(int index):   Same as get, but uses recursion
*/

/** invariant
 *  addFirst    :   add the item behind the sentinel
 *  addLast     :   add the item before the sentinel
 *  removeFirst :   remove the item behind the sentinel, unless the deque is empty
 *  removeLast  :   remove the item before the sentinel, unless the deque is empty
 *  size        :   the size of the deque
 *  isEmpty     :   size == 0;
*/


public class LinkedListDeque<T> {
    private class ItemNode {
        public T item;
        public ItemNode next;
        public ItemNode last;

        public ItemNode(T item, ItemNode last, ItemNode next) {
            this.item = item;
            this.last = last;
            if (last != null) {
                last.next = this;
            }
            this.next = next;
            if (next != null) {
                next.last = this;
            }

        }
    }

    private ItemNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new ItemNode(null, null, null);
        sentinel.last = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        ++size;
        sentinel.next = new ItemNode(item, sentinel, sentinel.next);
    }

    public void addLast(T item) {
        ++size;
        sentinel.last = new ItemNode(item, sentinel.last, sentinel);

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        ItemNode ptr = sentinel.next;
        while (ptr != sentinel) {
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        --size;
        ItemNode first = sentinel.next;
        sentinel.next = first.next;
        sentinel.next.last = sentinel;
        return first.item;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        --size;
        ItemNode last = sentinel.last;
        sentinel.last = last.last;
        sentinel.last.next = sentinel;
        return last.item;
    }

    public T get(int index) {
        if (isEmpty()) {
            return null;
        }

        ItemNode ptr = sentinel.next;
        while (ptr != sentinel && index-- != 0) {
            ptr = ptr.next;
        }

        if (ptr == sentinel) {
            return null;
        }
        return ptr.item;
    }

   public T getRecursive(int index, ItemNode ptr) {
        if (ptr == sentinel) {
            return null;
        }

        if (index == 0) {
            return ptr.item;
        }

        return getRecursive(index - 1, ptr.next);
   }
    public T getRecursive(int index) {
        return getRecursive(index, sentinel.next);
    }
}




