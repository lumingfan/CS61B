import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDequeGold {
    @Test
    public void testDeque() {
        StudentArrayDeque<Integer> stu = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> right = new ArrayDequeSolution<>();
        int testTimes = 10000;
        int maxValue = 100;
        String ope = "";
        for (int i = 0; i < testTimes; ++i) {
            double choice = StdRandom.uniform();
            Integer stuAnswer = 0, rightAnswer = 0;
            if (choice < 0.2 &&!right.isEmpty() && !stu.isEmpty()) {
                stuAnswer = stu.removeFirst();
                rightAnswer = right.removeFirst();
                ope += "removeFirst()\n";
            } else if (choice < 0.4 && !right.isEmpty() && !stu.isEmpty()) {
                stuAnswer = stu.removeLast();
                rightAnswer = right.removeLast();
                ope += "removeLast()\n";
            } else if (choice < 0.7) {
                int randomItem = StdRandom.uniform(maxValue);
                ope += "addFirst(" + randomItem + ")\n";
                stu.addFirst(randomItem);
                right.addFirst(randomItem);
            } else {
                int randomItem = StdRandom.uniform(maxValue);
                ope += "addLast(" + randomItem + ")\n";
                stu.addLast(randomItem);
                right.addLast(randomItem);
            }
            assertEquals(ope, rightAnswer, stuAnswer);
        }
    }
}