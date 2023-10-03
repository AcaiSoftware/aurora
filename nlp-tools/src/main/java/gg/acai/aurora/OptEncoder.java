package gg.acai.aurora;

import gg.acai.aurora.token.Tokenizer;

/**
 * @author Clouke
 * @since 09.08.2023 20:26
 * © Aurora - All Rights Reserved
 */
public class OptEncoder implements Encoder, ResizableEncoder {

  private int maxLen;

  public OptEncoder(int maxLen) {
    this.maxLen = maxLen;
  }

  public OptEncoder() {
    this(100);
  }

  @Override
  public double[] encode(String s, Tokenizer tokenizer) {
    double[] encoder = new double[maxLen];
    int idx = 0;
    for (String token : tokenizer.tokenizeToArray(s)) {
      int tokenId = tokenizer.indexOf(token);
      if (tokenId == -1) {
        idx++;
        continue;
      }
      encoder[idx] = tokenId;
      idx++;

      if (idx >= maxLen)
        throw new IllegalArgumentException("Input string is too long");
    }
    return encoder;
  }

  @Override
  public void resize(int newSize) {
    this.maxLen = newSize;
  }
}
