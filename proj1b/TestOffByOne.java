import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void equalCharsTest() {
        for (char ch = 0; ch < 126; ++ch) {
            assertTrue(offByOne.equalChars(ch, (char) (ch + 1)));
            assertFalse(offByOne.equalChars(ch, (char) (ch + 2)));
        }
    }
}
