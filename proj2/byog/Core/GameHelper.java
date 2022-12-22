package byog.Core;

public class GameHelper {

    public static long getSeed(String str) {
        long res = 0;
        for (int i = 1; i < str.length() && Character.isDigit(str.charAt(i)); ++i) {
            res = 10 * res + Character.digit(str.charAt(i), 10);
        }
        return res;
    }
}
