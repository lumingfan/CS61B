import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDequeGold {
    @Test
    public void testDeque() {
        StudentArrayDeque<Integer> stu = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> right = new ArrayDequeSolution<>();
        int testTimes = 1000;
        int maxValue = 10000;
        String ope = "\n";
        for (int i = 0; i < testTimes; ++i) {
            double choice = StdRandom.uniform();
            if (choice < 0.2 && !stu.isEmpty() && !right.isEmpty()) {
                int stuRemoveFirst = stu.removeFirst();
                int rightRemoveFirst = right.removeFirst();
                ope += "removeFirst(): " + stuRemoveFirst + "\n";
                assertEquals(ope, stuRemoveFirst, rightRemoveFirst);
            } else if (choice < 0.4 && !stu.isEmpty() && !right.isEmpty()) {
                int stuRemoveLast = stu.removeLast();
                int rightRemoveLast = right.removeLast();
                ope += "removeLast(): " + stuRemoveLast + "\n";
                assertEquals(ope, stuRemoveLast, rightRemoveLast);
            } else if (choice < 0.7) {
                int randomItem = StdRandom.uniform(maxValue) - StdRandom.uniform(maxValue);
                ope += "addFirst(" + randomItem + ")\n";
                stu.addFirst(randomItem);
                right.addFirst(randomItem);
            } else {
                int randomItem = StdRandom.uniform(maxValue) - StdRandom.uniform(maxValue);
                ope += "addLast(" + randomItem + ")\n";
                stu.addLast(randomItem);
                right.addLast(randomItem);
            }
        }
    }
}