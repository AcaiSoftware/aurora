package gg.acai.aurora.initializers;

/**
 * @author Clouke
 * @since 10.09.2023 02:29
 * © Aurora - All Rights Reserved
 */
public class XavierInitializer implements WeightInitializer {
  @Override
  public ComposedWeights initialize(long seed, int inputSize, int hiddenSize, int outputSize) {
    double[][] weights_input_to_hidden = new double[inputSize][hiddenSize];
    double[][] weights_hidden_to_output = new double[hiddenSize][outputSize];
    double[] biases_hidden = new double[hiddenSize];
    double[] biases_output = new double[outputSize];

    double range = Math.sqrt(6.0 / (inputSize + hiddenSize));
    double range2 = Math.sqrt(6.0 / (hiddenSize + outputSize));

    for (int i = 0; i < inputSize; i++) {
      for (int j = 0; j < hiddenSize; j++)
        weights_input_to_hidden[i][j] = Math.random() * range - range / 2;
    }

    for (int i = 0; i < hiddenSize; i++)
      biases_hidden[i] = Math.random() * range - range / 2;

    for (int i = 0; i < hiddenSize; i++) {
      for (int j = 0; j < outputSize; j++)
        weights_hidden_to_output[i][j] = Math.random() * range2 - range2 / 2;
    }

    for (int i = 0; i < outputSize; i++)
      biases_output[i] = Math.random() * range2 - range2 / 2;

    return new ComposedWeights(
      weights_input_to_hidden,
      weights_hidden_to_output,
      biases_hidden,
      biases_output
    );
  }
}
