package gg.acai.aurora;

import gg.acai.aurora.token.Tokenizer;

/**
 * @author Clouke
 * @since 17.07.2023 15:07
 * © Aurora - All Rights Reserved
 */
public class BinaryEncoder implements Encoder {
  @Override
  public double[] encode(String word, Tokenizer tokenizer) {
    int idx = tokenizer.indexOf(word);
    if (idx == -1)
      return new double[tokenizer.size()];
    int numBits = (int) Math.ceil(Math.log(tokenizer.size()) / Math.log(2));
    double[] encoded = new double[numBits];
    for (int i = 0; i < numBits; i++) {
      encoded[i] = (idx & (1 << i)) != 0 ? 1 : 0;
    }
    return encoded;
  }
}
