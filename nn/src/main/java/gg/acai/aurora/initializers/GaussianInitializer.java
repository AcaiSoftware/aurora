package gg.acai.aurora.initializers;

import java.util.Random;

/**
 * @author Clouke
 * @since 08.09.2023 04:14
 * © Aurora - All Rights Reserved
 */
public class GaussianInitializer implements WeightInitializer {
  @Override
  public ComposedWeights initialize(long seed, int inputSize, int hiddenSize, int outputSize) {
    Random random = new Random(seed);
    double[][] weights_input_to_hidden = new double[inputSize][hiddenSize];
    double[][] weights_hidden_to_output = new double[hiddenSize][outputSize];
    double[] biases_hidden = new double[hiddenSize];
    double[] biases_output = new double[outputSize];

    for (int i = 0; i < inputSize; i++) {
      for (int j = 0; j < hiddenSize; j++)
        weights_input_to_hidden[i][j] = random.nextGaussian();
    }

    for (int i = 0; i < hiddenSize; i++)
      biases_hidden[i] = random.nextGaussian();

    for (int i = 0; i < hiddenSize; i++) {
      for (int j = 0; j < outputSize; j++)
        weights_hidden_to_output[i][j] = random.nextGaussian();
    }

    for (int i = 0; i < outputSize; i++)
      biases_output[i] = random.nextGaussian();

    return new ComposedWeights(
      weights_input_to_hidden,
      weights_hidden_to_output,
      biases_hidden,
      biases_output
    );
  }
}
