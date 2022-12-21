package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        //ArrayRingBuffer arb = new ArrayRingBuffer(10);
        AbstractBoundedQueue<Integer> b2 = new ArrayRingBuffer<Integer>(100);
        assertEquals(0, b2.fillCount);
        AbstractBoundedQueue<Integer> arr = new ArrayRingBuffer<>(10);
        assertEquals(10, arr.capacity());
        arr.enqueue(1);
        assertEquals(1, (int) arr.dequeue());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
