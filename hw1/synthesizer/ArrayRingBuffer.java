package synthesizer; //todo: Make sure to make this class a part of the synthesizer package

import java.util.Iterator;

//todo: Make sure to make this class and all of its methods public
//todo: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;


    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        //  todo: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.

        this.rb = (T[]) new Object[capacity];
        this.capacity = capacity;
        this.fillCount = 0;
        this.first = 0;
        this.last = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    private int next(int index) {
        return (index + 1) % capacity;
    }
    @Override
    public void enqueue(T x) {
        // todo: Enqueue the item. Don't forget to increase fillCount and update last.
        if (isFull()) {
            throw new RuntimeException("The ArrayRingBuffer if full, can't enqueue anything");
        }

        rb[last] = x;
        last = next(last);
        ++fillCount;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        // todo: Dequeue the first item. Don't forget to decrease fillCount and update
        if (isEmpty()) {
            throw new RuntimeException("The ArrayRingBuffer is empty, can't dequeue anything");
        }
        T returnItem = rb[first];
        first = next(first);
        --fillCount;
        return returnItem;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
         // todo: Return the first item. None of your instance variables should change.
        if (isEmpty()) {
            throw new RuntimeException("The ArrayRingBuffer is empty, can't get anything peek");
        }

        return rb[first];
    }

    // todo: When you get to part 5, implement the needed code to support iteration.

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingIterator();
    }

    private class ArrayRingIterator implements Iterator {

        private int index = first;
        @Override
        public boolean hasNext() {
            return index != last;
        }

        @Override
        public T next() {
            T returnItem = rb[index];
            index = (index + 1) % capacity;
            return returnItem;
        }
    }

}
