package gg.acai.aurora;

import java.util.List;

/**
 * @author Clouke
 * @since 03.05.2023 12:56
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface Tokenizer {

  /**
   * Tokenizes the given text
   *
   * @param text The text to tokenize
   * @return Returns the tokenized text
   */
  List<String> tokenize(String text);

  /**
   * Tokenizes the given text to an array
   *
   * @param text The text to tokenize
   * @return Returns the tokenized text as an array
   */
  default String[] toArray(String text) {
    return tokenize(text).toArray(new String[0]);
  }

  /**
   * Gets the default tokenizer
   *
   * @return Returns the default tokenizer
   */
  static Tokenizer simple() {
    return SimpleTokenizer.instance();
  }
}
