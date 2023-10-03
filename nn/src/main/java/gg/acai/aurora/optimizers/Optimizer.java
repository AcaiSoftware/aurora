package gg.acai.aurora.optimizers;

/**
 * @author Clouke
 * @since 25.04.2023 11:19
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface Optimizer {
  /**
   * Updates the weights and biases of the neural network.
   *
   * @param iteration The current iteration
   * @param weights_input_to_hidden The weights from the input layer to the hidden layer
   * @param weights_hidden_to_output The weights from the hidden layer to the output layer
   * @param delta_hidden The delta of the hidden layer
   * @param delta_output The delta of the output layer
   * @param biases_hidden The biases of the hidden layer
   * @param biases_output The biases of the output layer
   * @param inputs The inputs
   * @param hidden The hidden layer
   */
  void update(int iteration,
    double[][] weights_input_to_hidden,
    double[][] weights_hidden_to_output,
    double[] delta_hidden,
    double[] delta_output,
    double[] biases_hidden,
    double[] biases_output,
    double[][] inputs,
    double[] hidden,
    double learningRate
  );
}
