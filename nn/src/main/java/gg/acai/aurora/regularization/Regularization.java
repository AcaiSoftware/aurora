package gg.acai.aurora.regularization;

/**
 * @author Clouke
 * @since 08.09.2023 03:27
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface Regularization {
  void apply(
    double[][] weights_input_to_hidden,
    double[][] weights_hidden_to_output,
    double[] biases_hidden,
    double[] biases_output,
    double learningRate
  );
}
