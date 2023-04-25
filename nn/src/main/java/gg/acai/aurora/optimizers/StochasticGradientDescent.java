package gg.acai.aurora.optimizers;

/**
 * @author Clouke
 * @since 25.04.2023 04:16
 * © Aurora - All Rights Reserved
 */
public class StochasticGradientDescent extends AbstractOptimizer {

  @Override
  public void update(int iteration, double[][] weights_input_to_hidden, double[][] weights_hidden_to_output, double[] delta_hidden, double[] delta_output, double[] biases_hidden, double[] biases_output, double[][] inputs, double[] hidden) {
    for (int j = 0; j < weights_input_to_hidden[0].length; j++) {
      for (int k = 0; k < weights_input_to_hidden.length; k++) {
        weights_input_to_hidden[k][j] += learningRate * delta_hidden[j] * inputs[iteration][k];
      }
      biases_hidden[j] += learningRate * delta_hidden[j];
    }
    for (int j = 0; j < weights_hidden_to_output[0].length; j++) {
      for (int k = 0; k < weights_hidden_to_output.length; k++) {
        weights_hidden_to_output[k][j] += learningRate * delta_output[j] * hidden[k];
      }
      biases_output[j] += learningRate * delta_output[j];
    }
  }

}
