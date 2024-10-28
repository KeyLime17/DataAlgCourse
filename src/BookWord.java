public class BookWord implements Comparable<BookWord> {

    private String text;
    private Integer count;

    public BookWord(String wordText) {
        this.text = wordText.toLowerCase().trim();
        this.count = 1;
    }

    public String getText() {
        return text;
    }

    public Integer getCount() {
        return count;
    }

    public void incrementCount() {
        count++;
    }

    @Override
    public boolean equals(Object wordToCompare) {
        if (this == wordToCompare) return true;
        if (wordToCompare == null || getClass() != wordToCompare.getClass()) return false;
        BookWord bookWord = (BookWord) wordToCompare;
        return text.equals(bookWord.text);
    }

    /**
     * Hashcode I found that looked pretty simple and cool on stack overflow, it is a Polynomial Rolling Hash
     * Reference: https://stackoverflow.com/questions/2624192/good-hash-function-for-strings
     * To explain how it works, it starts with a prime number of '7'
     * each character in the string is multipled by 31, which is also an odd prime number, and has less chances at collisions
     * In the comments of the post, there was further explanation on why primes work better, and why 31 is used for string hashing.
     * http://computinglife.wordpress.com/2008/11/20/why-do-hash-functions-use-prime-numbers/
     */
    @Override
    public int hashCode() {
        int hash = 7;
        for (int i = 0; i < text.length(); i++) {
            hash = hash * 31 + text.charAt(i);
        }
        return hash;
    }


    @Override
    public String toString() {
        return "BookWord{text='" + text + "', count=" + count + "}";
    }

    @Override
    public int compareTo(BookWord other) {
        return this.text.compareTo(other.text);
    }
}
