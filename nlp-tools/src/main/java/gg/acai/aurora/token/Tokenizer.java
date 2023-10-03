package gg.acai.aurora.token;

import gg.acai.aurora.model.Model;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Clouke
 * @since 03.05.2023 12:56
 * © Aurora - All Rights Reserved
 */
public interface Tokenizer extends Model {

  /**
   * Tokenizes the given text
   *
   * @param text The text to tokenize
   * @return Returns the tokenized text
   */
  Collection<String> tokenize(String text);

  /**
   * Provides a stream of the tokenized text
   *
   * @param text The text to tokenize
   * @return Returns a stream of the tokenized text
   */
  default Stream<String> stream(String text) {
    return tokenize(text).stream();
  }

  /**
   * Tokenizes the given text and returns it as an array
   *
   * @param text The text to tokenize
   * @return Returns the tokenized text as an array
   */
  default String[] tokenizeToArray(String text) {
    return tokenize(text).toArray(new String[0]);
  }

  /**
   * Gets the index of the given token
   *
   * @param token The token to get the index of
   * @return Returns the index of the given token or -1 if the token is not in the tokenizer
   */
  int indexOf(String token);

  /**
   * Gets the word of the given index
   *
   * @param index The index to get the word of
   * @return Returns the word of the given index
   */
  String wordOf(int index);

  /**
   * Gets the size of the tokenizer
   *
   * @return Returns the size of the tokenizer
   */
  int size();

  /**
   * Fits the tokenizer to the given corpus
   *
   * @param corpus The corpus to fit the tokenizer to
   */
  void fit(Iterable<String> corpus);

  /**
   * Gets the indices of the tokenizer
   *
   * @return Returns the indices of the tokenizer
   */
  Map<String, Integer> indices();

  /**
   * Gets the amount of characters in the tokenizer
   *
   * @return Returns the amount of characters in the tokenizer
   */
  long countCharacters();

  /**
   * Checks if the tokenizer supports indexing
   *
   * @return Returns true if the tokenizer supports indexing
   */
  default boolean supportsIndexing() {
    boolean supportsIndexing = false;
    try {
      indexOf("");
      supportsIndexing = true;
    } catch (Exception ignored) {}
    return supportsIndexing;
  }
}
