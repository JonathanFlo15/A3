import java.util.Comparator;

public class Term implements Comparable<Term> {
    private String query; // variable for query
    private long weight; // variable for weight of query

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null) {
            throw new IllegalArgumentException("query can't be null");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("weight can't be negative");
        }
        this.query = query;
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new Comparator<Term>() {
            public int compare(Term t1, Term t2) {
                return Long.compare(t2.weight, t1.weight);  // Descending order
            }
        };
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        return new PrefixOrderComparator(r);
    }

    // Private static nested class for comparing terms by prefix
    private static class PrefixOrderComparator implements Comparator<Term> {
        private final int r; // length of prefix

        // Constructor takes the prefix length r
        public PrefixOrderComparator(int r) {
            if (r < 0) {
                throw new IllegalArgumentException("Prefix length shouldn't be negative");
            }
            this.r = r;
        }

        public int compare(Term t1, Term t2) {
            // Get the prefixes of length r from both terms
            String prefixOne = t1.query.substring(0, Math.min(r, t1.query.length()));
            String prefixTwo = t2.query.substring(0, Math.min(r, t2.query.length()));
            return prefixOne.compareTo(prefixTwo);
        }

    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return this.query.compareTo(that.query);
    }


    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return weight + "\t" + query;
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Create some sample terms
        Term t1 = new Term("apple", 100);
        Term t2 = new Term("banana", 50);
        Term t3 = new Term("apple", 100); // Same as t1 to test for zero case
        Term t4 = new Term("grape", 150);

        // Testing compareTo() which is lexographical/natural order
        System.out.println("Testing compareTo() method:");
        System.out.println(
                t1.compareTo(t2));  // Negative since "apple" < "banana"
        System.out.println(
                t2.compareTo(t1));  // Positive since "banana" > "apple"
        System.out.println(t1.compareTo(t3));  // Zero since "apple" == "apple"

        // Testing byReverseWeightOrder(), which descends weight-wise
        Comparator<Term> reverseWeightOrder = Term.byReverseWeightOrder();
        System.out.println("Testing byReverseWeightOrder() method:");
        System.out.println(reverseWeightOrder.compare(t1, t2));  // Positive since 100 > 50
        System.out.println(reverseWeightOrder.compare(t2, t1));  // Negative: 50 < 100
        System.out.println(reverseWeightOrder.compare(t1, t3));  // Zero: 1000 == 1000

        // Test byPrefixOrder with prefix length of 3
        Comparator<Term> prefixOrder = Term.byPrefixOrder(3);
        System.out.println("Testing byPrefixOrder(3) method:");
        System.out.println(
                prefixOrder.compare(t1, t2));  // Negative since "app" < "ban"
        System.out.println(
                prefixOrder.compare(t2, t4));  // Positive since "ban" > "gra"
        System.out.println(prefixOrder.compare(t1,
                                               t3));  // Zero since "app" == "app"
    }


}
