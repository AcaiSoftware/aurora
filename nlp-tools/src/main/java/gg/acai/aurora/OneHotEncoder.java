package gg.acai.aurora;

import gg.acai.aurora.token.Tokenizer;

import java.util.Collection;

/**
 * @author Clouke
 * @since 06.07.2023 16:37
 * © Aurora - All Rights Reserved
 */
public class OneHotEncoder implements Encoder {
  @Override
  public double[] encode(String word, Tokenizer tokenizer) {
    Collection<String> tokens = tokenizer.tokenize(word);
    double[] encoded = new double[tokenizer.size()];
    for (String token : tokens) {
      int idx = tokenizer.indexOf(token);
      if (idx == -1)
        continue;
      encoded[idx] = 1;
    }
    return encoded;
  }
}