package gg.acai.aurora;

import gg.acai.aurora.token.Tokenizer;

/**
 * @author Clouke
 * @since 05.07.2023 20:19
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface Encoder {
  double[] encode(
    String word,
    Tokenizer tokenizer
  );
}
