import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Autocomplete {
    private final Term[] terms; // array of term objects

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) {
            throw new IllegalArgumentException(
                    "Argument to Autocomplete constructor cannot be null");
        }
        // for each loop that checks if a term is null
        for (Term term : terms) {
            if (term == null) {
                throw new IllegalArgumentException(
                        "Terms array cannot contain null items");
            }
        }
        // copy of Terms to sort
        this.terms = new Term[terms.length];
        for (int i = 0; i < terms.length; i++) {
            this.terms[i] = terms[i];
        }
        // sorts array of terms
        Arrays.sort(this.terms);
    }

    // Returns all terms that start with the given prefix,
    // in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("allMatches() is null");
        }
        Term searchTerm = new Term(prefix, 0); // term w/ prefix and weight of 0

        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());

        int firstIndex = BinarySearchDeluxe.firstIndexOf(terms, searchTerm,
                                                         prefixOrder);
        int lastIndex = BinarySearchDeluxe.lastIndexOf(terms, searchTerm, prefixOrder);

        // returns a new blank term if neither indices are found
        if (firstIndex == -1 || lastIndex == -1)
            return new Term[0];

        // finds words that match prefix and copies them
        Term[] matches = Arrays.copyOfRange(terms, firstIndex, lastIndex + 1);

        // sorts by reverse weight
        Arrays.sort(matches, Term.byReverseWeightOrder()); // sort in reverse weight
        return matches;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException(
                    "numberOfMatches() is null");
        }
        Term searchTerm = new Term(prefix, 0);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int firstIndex = BinarySearchDeluxe.firstIndexOf(terms, searchTerm,
                                                         prefixOrder);
        int lastIndex = BinarySearchDeluxe.lastIndexOf(terms, searchTerm, prefixOrder);

        if (firstIndex == -1)
            return 0;
        return lastIndex - firstIndex + 1;

    }


    // unit testing
    public static void main(String[] args) {
        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }

}
