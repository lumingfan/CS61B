import org.junit.Test;
import static org.junit.Assert.*;

public class FilkTest {
    @Test
    public void sameTest() {
        int testTimes = 100;
        for (int i = 0; i < testTimes; ++i) {
            int randomInt = (int)(Math.random() * 10);
            int otherInt = (int)(Math.random() * 10);
            assertEquals(randomInt == otherInt, Flik.isSameNumber(randomInt, otherInt));
        }
    }
}
