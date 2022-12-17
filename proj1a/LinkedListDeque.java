public class LinkedListDeque<T> {
    private class ItemNode {
        private T item;
        private ItemNode next;
        private ItemNode last;

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

   private T getRecursive(int index, ItemNode ptr) {
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




