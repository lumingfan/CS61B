import org.junit.Test;
import static org.junit.Assert.*;

public class FlikTest {
    @Test
    public void sameTest() {
        assertTrue(Flik.isSameNumber(127, 127));
        assertTrue(Flik.isSameNumber(129, 129));
        assertTrue(Flik.isSameNumber(128, 128));
    }
}
