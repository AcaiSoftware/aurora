package gg.acai.aurora.initializers;

/**
 * @author Clouke
 * @since 08.09.2023 04:14
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface WeightInitializer {
  ComposedWeights initialize(
    long seed,
    int inputSize,
    int hiddenSize,
    int outputSize
  );
}
