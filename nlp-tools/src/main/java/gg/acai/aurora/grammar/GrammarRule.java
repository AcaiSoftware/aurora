package gg.acai.aurora.grammar;

import gg.acai.aurora.Tokenizer;

import java.util.Map;

/**
 * @author Clouke
 * @since 03.05.2023 13:21
 * © Aurora - All Rights Reserved
 */
public interface GrammarRule {

  /**
   * Gets the tokenizer used by this grammar rule
   *
   * @return Returns the tokenizer used by this grammar rule
   */
  Tokenizer tokenizer();

  /**
   * Applies the grammar rule to the text
   * (e.g "Happy I am" to "I am happy")
   *
   * @param text The text to apply the grammar rule to
   * @return Returns the text with the grammar rule applied
   */
  String follow(String text);

  /**
   * Gets the grammar id of the given word
   *
   * @param word The word to get the grammar id of
   * @return Returns the grammar id of the given word
   */
  String id(String word);

  /**
   * Fits the grammar rule to the given rules
   * (e.g "I - subject", "am - verb")
   *
   * @param rules The rules to fit the grammar rule to
   */
  void fit(Map<String, String> rules);

  /**
   * Gets the pattern of rules in this grammar rule
   *
   * @return Returns the pattern of rules in this grammar rule
   */
  String[] pattern();

  /**
   * Gets the grammar rules as a map
   *
   * @return Returns the grammar rules as a map
   */
  Map<String, String> grammar();

}
