package gg.acai.aurora.initializers;

/**
 * @author Clouke
 * @since 10.09.2023 02:29
 * © Aurora - All Rights Reserved
 */
public class ZeroInitializer implements WeightInitializer {
  @Override
  public ComposedWeights initialize(long seed, int inputSize, int hiddenSize, int outputSize) {
    double[][] weights_input_to_hidden = new double[inputSize][hiddenSize];
    double[][] weights_hidden_to_output = new double[hiddenSize][outputSize];
    double[] biases_hidden = new double[hiddenSize];
    double[] biases_output = new double[outputSize];

    return new ComposedWeights(
      weights_input_to_hidden,
      weights_hidden_to_output,
      biases_hidden,
      biases_output
    );
  }
}
