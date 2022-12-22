package synthesizer;

public class TestEnhancedForLoop {
    public static void main(String[] args) {
        ArrayRingBuffer<String> arr = new ArrayRingBuffer<>(5);
        arr.enqueue("I");
        arr.enqueue("am");
        arr.enqueue("a");
        arr.enqueue("Chinese");
        for (String item : arr) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
}
