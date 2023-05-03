package gg.acai.aurora.grammar;

import gg.acai.aurora.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Clouke
 * @since 03.05.2023 13:48
 * © Aurora - All Rights Reserved
 */
public class GrammarRuleModel implements GrammarRule {

  private final Tokenizer tokenizer;
  private final Map<String, String> grammar;

  public GrammarRuleModel(Tokenizer tokenizer) {
    this.tokenizer = tokenizer;
    this.grammar = new HashMap<>();
  }

  @Override
  public Tokenizer tokenizer() {
    return tokenizer;
  }

  @Override
  public String follow(String text) {
    String[] words = tokenizer.toArray(text);
    String[] pattern = pattern();
    if (words.length == pattern.length) {
      boolean valid = true;
      for (int i = 0; i < words.length; i++) {
        String id = id(words[i]);
        if (!id.equals(pattern[i])) {
          valid = false;
          break;
        }
      }
      if (valid) {
        return text;
      }
    }

    // reordering words
    Map<String, String> wordMap = new HashMap<>();
    for (String word : words) {
      String id = id(word);
      wordMap.put(id, word);
    }

    List<String> fixedWords = new ArrayList<>();
    Set<String> addedWords = new HashSet<>(); // keep track of words that have already been added
    for (String expectedId : pattern) {
      String word = wordMap.get(expectedId);
      if (word != null && !addedWords.contains(word)) {
        fixedWords.add(word);
        addedWords.add(word);
      }
    }

    return String.join(" ", fixedWords);
  }


  @Override
  public String id(String word) {
    return grammar.getOrDefault(word, "unknown");
  }

  @Override
  public void fit(Map<String, String> rules) {
    grammar.putAll(rules);
  }

  @Override
  public String[] pattern() {
    return grammar.values().toArray(new String[0]);
  }

  @Override
  public Map<String, String> grammar() {
    return grammar;
  }

}
