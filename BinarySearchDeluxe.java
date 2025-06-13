import edu.princeton.cs.algs4.StdRandom;

import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException("Can't be null");
        }
        int lo = 0;
        int hi = a.length - 1;
        int result = -1;


        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int comp = comparator.compare(a[mid], key);
            if (comp < 0) {
                lo = mid + 1;
            }
            else {
                if (comp == 0)
                    result = mid;
                hi = mid - 1;
            }
        }
        return result; // return the current position of i
    }


    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException("Can't be null");
        }
        int lo = 0;
        int hi = a.length - 1;
        int result = -1;


        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int comp = comparator.compare(a[mid], key);
            if (comp > 0) {
                hi = mid - 1;
            }
            else {
                if (comp == 0)
                    result = mid;
                lo = mid + 1;
            }
        }
        return result; // return the current position of i

    }

    // unit testing (required)
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]); // Get the integer n from command-line argument
        Integer[] testArray = new Integer[5 * n];

        // filling array of 5n integers
        int index = 0;  // Keep track of the index in testArray
        for (int i = 1; i <= n; i++) {  // Outer loop goes from 1 to n
            for (int j = 0; j < 5; j++) {  // Inner loop fills 5 copies of i
                testArray[index++] = i;
            }
        }
        // Pick a random key between 0 and n+1
        int key = StdRandom.uniformInt(0, n + 2);


        // Run firstIndexOf and lastIndexOf with the default integer comparator
        Comparator<Integer> comparator = Integer::compareTo;
        int firstIndex = firstIndexOf(testArray, key, comparator);
        int lastIndex = lastIndexOf(testArray, key, comparator);

        // Brute force search for firstIndex and lastIndex
        int expectedFirstIndex = -1;
        int expectedLastIndex = -1;

        for (int k = 0; k < testArray.length; k++) {
            if (testArray[k] == key) {
                if (expectedFirstIndex == -1) {
                    expectedFirstIndex = k;  // record the first occurrence
                }
                expectedLastIndex = k; // update to the last occurrence
            }
        }

        // Print the test results
        System.out.println("Key: " + key);
        System.out.println("First index: " + firstIndex);
        System.out.println("Last index: " + lastIndex);
        System.out.println("Expected first index: " + expectedFirstIndex);
        System.out.println("Expected last index: " + expectedLastIndex);

        // Check if the binary search results match the brute force results
        if (firstIndex != expectedFirstIndex || lastIndex != expectedLastIndex) {
            System.out.println("Error!");
        }
        else {
            System.out.println("Success!");
        }
    }
}










