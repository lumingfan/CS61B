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
    public void TestIsPalindrome() {
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome("a"));
        assertFalse(palindrome.isPalindrome("Aa"));
        assertTrue(palindrome.isPalindrome(null));
        assertTrue(palindrome.isPalindrome(""));
        assertFalse(palindrome.isPalindrome("persiflage"));

        assertTrue(palindrome.isPalindrome("a", new OffByOne()));
        assertFalse(palindrome.isPalindrome("noon", new OffByOne()));
        assertTrue(palindrome.isPalindrome("ab", new OffByOne()));
        assertTrue(palindrome.isPalindrome(null, new OffByOne()));
        assertTrue(palindrome.isPalindrome("ba", new OffByOne()));
        assertTrue(palindrome.isPalindrome("flake", new OffByOne()));
        assertFalse(palindrome.isPalindrome("persiflage", new OffByOne()));
    }
}
