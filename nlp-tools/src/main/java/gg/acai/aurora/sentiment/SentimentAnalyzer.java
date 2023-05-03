package gg.acai.aurora.sentiment;

import gg.acai.aurora.Tokenizer;

import java.util.Map;

/**
 * @author Clouke
 * @since 03.05.2023 13:03
 * © Aurora - All Rights Reserved
 */
public interface SentimentAnalyzer {

  /**
   * Loads the sentiment analyzer from the given path
   *
   * @param path The path to load the sentiment analyzer from
   */
  void load(String path);

  /**
   * Analyzes the given text
   *
   * @param text The text to analyze
   * @return Returns the sentiment score of the given text
   */
  double analyze(String text);

  /**
   * Fits the sentiment analyzer to the given scores
   *
   * @param scores The scores to fit the sentiment analyzer to
   */
  void fit(Map<String, Integer> scores);

  /**
   * Gets the sentiment type of the given text
   *
   * @param text The text to get the sentiment type of
   * @return Returns the sentiment type of the given text
   */
  Sentiment type(String text);

  /**
   * Gets the tokenizer used by this sentiment analyzer
   *
   * @return Returns the tokenizer used by this sentiment analyzer
   */
  Tokenizer tokenizer();

}
