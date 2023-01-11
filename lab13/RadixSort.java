/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    private static final int BASE = 256;
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // todo: Implement LSD Sort
        if (asciis == null || asciis.length <= 1) {
            return asciis;
        }
        int maxLength = findMaxLen(asciis);
        String[] newAsciis = new String[asciis.length];
        System.arraycopy(asciis, 0, newAsciis, 0, asciis.length);
        for (int i = maxLength - 1; i >= 0; --i) {
            sortHelperLSD(newAsciis, i);
        }
        return newAsciis;
    }


    private static int findMaxLen(String[] asciis) {
        int max = 0;
        for (String s : asciis) {
            if (s.length() > max) {
                max = s.length();
            }
        }
        return max;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        int[] count = new int[BASE + 1];
        for (String s : asciis) {
            ++count[getIndex(s, index) + 1];
        }

        for (int i = 1; i <= BASE; ++i) {
            count[i] += count[i - 1];
        }

        String[] newAsciis = new String[asciis.length];
        for (String s : asciis) {
            newAsciis[count[getIndex(s, index)]++] = s;
        }

        System.arraycopy(newAsciis, 0, asciis, 0, newAsciis.length);
    }

    private static int getIndex(String s, int index) {
        if (index >= s.length()) {
            return 0;
        }
        return s.charAt(index);
    }


    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
