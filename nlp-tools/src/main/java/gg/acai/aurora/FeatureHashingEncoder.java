package gg.acai.aurora;

import gg.acai.aurora.token.Tokenizer;

import java.util.Arrays;

/**
 * @author Clouke
 * @since 17.07.2023 15:09
 * © Aurora - All Rights Reserved
 */
public class FeatureHashingEncoder implements Encoder, ResizableEncoder {

  private int numFeatures;

  public FeatureHashingEncoder(int numFeatures) {
    this.numFeatures = numFeatures;
  }

  public FeatureHashingEncoder() {}

  @Override
  public double[] encode(String word, Tokenizer tokenizer) {
    int[] hashedIndices = hashFeatures(word, tokenizer);
    double[] encoded = new double[numFeatures];
    for (int index : hashedIndices) {
      encoded[Math.abs(index) % numFeatures] += Math.signum(index);
    }
    return encoded;
  }

  private int[] hashFeatures(String word, Tokenizer tokenizer) {
    int numTokens = tokenizer.size();
    int[] hashedIndices = new int[numTokens];
    Arrays.fill(hashedIndices, 0);

    String[] tokens = tokenizer.tokenizeToArray(word);
    for (String token : tokens) {
      int hash = token.hashCode();
      int index = hash % numTokens;
      hashedIndices[index] = hash;
    }

    return hashedIndices;
  }

  @Override
  public void resize(int newSize) {
    this.numFeatures = newSize;
  }
}
