package edu.cnm.deepdive.diceware;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This class implements a simple passphrase generator, using a provided {@link
 * String String[]} of words and a {@link Random} instance as a source of
 * randomness. (The latter should be of cryptographic quality.)
 *
 * @author Nicholas Bennett &amp; Deep Dive Coding Java Cohort 4
 */
public class Generator {

  private static final String NULL_RNG_MESSAGE = "Random number generator must not be null.";
  private static final String NULL_WORDS_MESSAGE = "Array of words must not be null.";
  private static final String EMPTY_WORDS_MESSAGE = "Array of words must not be empty.";
  private static final String NEGATIVE_NUMBER_WORDS_MESSAGE =
      "Number of words to be selected must not be negative.";
  private static final String INSUFFICIENT_WORDS_MESSAGE =
      "Number of distinct words requested must not exceed number of words in pool.";

  private String[] words;
  private Random rng;

  /**
   * Initializes <code>Generator</code> instance with the specified {@link
   * String String[]} word list and {@link Random} instance.
   *
   * @param words                       list of words.
   * @param rng                         source of randomness.
   * @throws NullPointerException       if <code>words</code> or
   *                                    <code>rng</code> is null.
   * @throws IllegalArgumentException   if <code>words</code> is empty.
   */
  public Generator(String[] words, Random rng)
      throws NullPointerException, IllegalArgumentException{
    if (rng == null) {
      throw new NullPointerException(NULL_RNG_MESSAGE);
    }
    if (words == null) {
      throw new NullPointerException(NULL_WORDS_MESSAGE);
    }
    if (words.length == 0) {
      throw new IllegalArgumentException(EMPTY_WORDS_MESSAGE);
    }
    Set<String> pool = new HashSet<>();
    for (String word : words) {
      word = word.toLowerCase();
      if (!pool.contains(word)) {
        pool.add(word);
      }
    }
    this.words = pool.toArray(new String[pool.size()]);
    this.rng = rng;
  }

  /**
   * Returns a randomly selected word.
   *
   * @return  selected word.
   */
  public String next() {
    return words[rng.nextInt(words.length)];
  }

  /**
   * Returns a random passphrase as a {@link String String[]} of words.
   * <code>duplicatesAllowed</code> controls the possible repetition of words in
   * in the passphrase returned.
   *
   * @param numWords                    length of passphrase.
   * @param duplicatesAllowed           flag controlling word repetition.
   * @return                            selected passphrase.
   * @throws NegativeArraySizeException if <code>numWords</code> &lt; 0.
   * @throws IllegalArgumentException   if <code>duplicatesAllowed</code> is
   *                                    <code>false</code> and
   *                                    <code>numWords</code> exceeds the number
   *                                    of words in the word list.
   */
  public String[] next(int numWords, boolean duplicatesAllowed)
      throws NegativeArraySizeException, IllegalArgumentException{
    if (numWords < 0) {
      throw new NegativeArraySizeException(NEGATIVE_NUMBER_WORDS_MESSAGE);
    }
    if (!duplicatesAllowed && numWords > words.length) {
      throw new IllegalArgumentException(INSUFFICIENT_WORDS_MESSAGE);
    }
    List<String> selection = new LinkedList<>();
    while (selection.size() < numWords) {
      String pick = next();
      if (duplicatesAllowed || !selection.contains(pick)) {
        selection.add(pick);
      }
    }
    return selection.toArray(new String[selection.size()]);
  }

  /**
   * Returns a random passphrase as a {@link String String[]} of words, with no
   * restriction on word duplication. Invoking this method is equivalent to
   * invoking {@link #next(int, boolean) next(numWords, true)}.
   *
   * @param numWords                    length of passphrase.
   * @return                            selected passphrase.
   * @throws NegativeArraySizeException if <code>numWords</code> &lt; 0.
   */
  public String[] next(int numWords)
      throws NegativeArraySizeException {
    return next(numWords, true);
  }

}
