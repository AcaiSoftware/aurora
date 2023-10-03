package gg.acai.aurora.regularization;

/**
 * @author Clouke
 * @since 08.09.2023 03:33
 * © Aurora - All Rights Reserved
 */
public class L2 implements Regularization {

  private final double regularizationRate;

  public L2(double regularizationRate) {
    this.regularizationRate = regularizationRate;
  }

  @Override
  public void apply(double[][] weights_input_to_hidden, double[][] weights_hidden_to_output, double[] biases_hidden, double[] biases_output, double learningRate) {
    for (int j = 0; j < weights_input_to_hidden[0].length; j++) {
      for (int k = 0; k < weights_input_to_hidden.length; k++) {
        weights_input_to_hidden[k][j] -= learningRate * regularizationRate * weights_input_to_hidden[k][j];
      }
    }
    for (int j = 0; j < weights_hidden_to_output[0].length; j++) {
      for (int k = 0; k < weights_hidden_to_output.length; k++) {
        weights_hidden_to_output[k][j] -= learningRate * regularizationRate * weights_hidden_to_output[k][j];
      }
    }
  }
}
