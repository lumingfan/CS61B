import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("chchchhchchc"));
        for (char ch = 0; ch < 128; ++ch) {
            assertTrue(palindrome.isPalindrome("" + ch));
        }
        assertTrue(palindrome.isPalindrome(""));
        assertFalse(palindrome.isPalindrome("persiflagedasdsa"));

        for (char ch = 0; ch < 128; ++ch) {
            assertTrue(palindrome.isPalindrome("" + ch, new OffByOne()));
        }
        assertFalse(palindrome.isPalindrome("zzzzffff", new OffByOne()));
        assertTrue(palindrome.isPalindrome("aflzkeb", new OffByOne()));
    }
}
