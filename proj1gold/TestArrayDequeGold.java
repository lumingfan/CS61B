import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDequeGold {
    @Test
    public void testDeque() {
        StudentArrayDeque<Integer> stu = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> right = new ArrayDequeSolution<>();
        int testTimes = 100;
        int maxValue = 100;
        // String ope = "\n";
        for (int i = 0; i < testTimes; ++i) {
            double choice = StdRandom.uniform();
            if (choice < 0.2 && !stu.isEmpty() && !right.isEmpty()) {
                // ope += "removeFirst(): " + stuRemoveFirst + "\n";
                // String ope = "removeFirst(): " + stuRemoveFirst + "\n";
                String ope = "removeFirst()\n";
                assertEquals(ope, stu.removeFirst(), right.removeFirst());
            } else if (choice < 0.4 && !stu.isEmpty() && !right.isEmpty()) {
                // ope += "removeLast(): " + stuRemoveLast + "\n";
                // String ope = "removeLast(): " + stuRemoveLast + "\n";
                String ope = "removeLast()\n";
                assertEquals(ope, stu.removeLast(), right.removeLast());
            } else if (choice < 0.7) {
                int randomItem = StdRandom.uniform(maxValue);
                // ope += "addFirst(" + randomItem + ")\n";
                stu.addFirst(randomItem);
                right.addFirst(randomItem);
            } else {
                int randomItem = StdRandom.uniform(maxValue);
                // ope += "addLast(" + randomItem + ")\n";
                stu.addLast(randomItem);
                right.addLast(randomItem);
            }
        }
    }
}