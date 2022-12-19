public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> res = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); ++i) {
            res.addLast(word.charAt(i));
        }
        return res;
    }

    public boolean isPalindrome(String word) {
        if (word == null) {
            return true;
        }

        Deque<Character> deque = wordToDeque(word);
        return isPalindrome(deque);
    }

    private boolean isPalindrome(Deque<Character> deque) {
        if (deque.size() == 1 || deque.isEmpty()) {
            return true;
        }

        if (deque.removeFirst() != deque.removeLast()) {
            return false;
        }
        return isPalindrome(deque);
    }


    public boolean isPalindrome(String word, CharacterComparator cmp) {
        if (word == null) {
            return true;
        }

        int lo = 0, hi = word.length() - 1;
        while (lo < hi) {
            if (!cmp.equalChars(word.charAt(lo), word.charAt(hi))) {
                return false;
            }
            ++lo;
            --hi;
        }
        return true;
    }

}
