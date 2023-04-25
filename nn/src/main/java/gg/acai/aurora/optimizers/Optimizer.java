package gg.acai.aurora.optimizers;

/**
 * @author Clouke
 * @since 25.04.2023 11:19
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface Optimizer {

  void update(int iteration,
    double[][] weights_input_to_hidden,
    double[][] weights_hidden_to_output,
    double[] delta_hidden,
    double[] delta_output,
    double[] biases_hidden,
    double[] biases_output,
    double[][] inputs,
    double[] hidden
  );

  default Optimizer apply(double learningRate) {
    throw new UnsupportedOperationException("Learning rate cannot be applied to this optimizer.");
  }

}
