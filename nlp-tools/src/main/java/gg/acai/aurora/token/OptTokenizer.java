package gg.acai.aurora.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * OptTokenizer - A fast trainable tokenizer model that uses a delimiter pattern to tokenize text
 *
 * @author Clouke
 * @since 04.07.2023 23:59
 * © Aurora - All Rights Reserved
 */
public class OptTokenizer extends AbstractTokenizerModel {

  private static final Pattern DELIMITER = Pattern.compile("[()?!.,:;*+\\-/\\\\|\\[\\]{}<>=~&^%$#@`\"'\\d]");

  public OptTokenizer(Map<String, Integer> indices, String name) {
    super(indices);
    name(name);
  }

  public OptTokenizer() {
    super();
  }

  @Override
  public Collection<String> tokenize(String text) {
    List<String> tokens = new ArrayList<>();
    Matcher matcher = DELIMITER.matcher(text);
    int previousEnd = 0;

    while (matcher.find()) {
      if (matcher.start() > previousEnd) {
        String token = text.substring(previousEnd, matcher.start()).trim();
        if (!token.isEmpty()) {
          if (indices.containsKey(token)) {
            tokens.add(token);
          } else {
            String[] words = token.split("\\s+");
            tokens.addAll(Arrays.asList(words));
          }
        }
      }

      String delimiter = matcher.group();
      tokens.add(delimiter);

      previousEnd = matcher.end();
    }

    if (previousEnd < text.length()) {
      String lastToken = text.substring(previousEnd).trim();
      if (!lastToken.isEmpty()) {
        String[] words = lastToken.split("\\s+");
        tokens.addAll(Arrays.asList(words));
      }
    }

    return tokens;
  }

  @Override
  public int indexOf(String token) {
    return indices.getOrDefault(
      token,
      -1
    );
  }

  @Override
  public String wordOf(int index) {
    for (Map.Entry<String, Integer> entry : indices.entrySet()) {
      if (entry.getValue() == index) return entry.getKey();
    }
    return null;
  }

  @Override
  public void fit(Iterable<String> corpus) {
    for (String word : corpus) {
      if (!indices.containsKey(word))
        indices.put(
          word,
          indices.size()
        );
    }
  }

  @Override
  public long countCharacters() {
    return indices.keySet()
      .stream()
      .mapToLong(String::length)
      .sum();
  }

  @Override
  public String toString() {
    return "OptTokenizer{" +
      "indices=" + indices +
      '}';
  }
}
