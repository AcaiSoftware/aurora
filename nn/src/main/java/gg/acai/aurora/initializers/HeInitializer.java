package gg.acai.aurora.initializers;

import java.util.Random;

/**
 * @author Clouke
 * @since 10.09.2023 02:34
 * © Aurora - All Rights Reserved
 */
public class HeInitializer implements WeightInitializer {
  @Override
  public ComposedWeights initialize(long seed, int inputSize, int hiddenSize, int outputSize) {
    Random random = new Random(seed);
    double[][] weights_input_to_hidden = new double[inputSize][hiddenSize];
    double[][] weights_hidden_to_output = new double[hiddenSize][outputSize];
    double[] biases_hidden = new double[hiddenSize];
    double[] biases_output = new double[outputSize];

    double variance = 2.0 / inputSize;

    for (int i = 0; i < inputSize; i++) {
      for (int j = 0; j < hiddenSize; j++)
        weights_input_to_hidden[i][j] = random.nextGaussian() * Math.sqrt(variance);
    }

    for (int i = 0; i < hiddenSize; i++) {
      for (int j = 0; j < outputSize; j++)
        weights_hidden_to_output[i][j] = random.nextGaussian() * Math.sqrt(variance);
    }

    return new ComposedWeights(
      weights_input_to_hidden,
      weights_hidden_to_output,
      biases_hidden,
      biases_output
    );
  }
}
