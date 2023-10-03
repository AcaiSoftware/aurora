package gg.acai.aurora.initializers;

import java.util.Arrays;

/**
 * @author Clouke
 * @since 10.09.2023 02:22
 * © Aurora - All Rights Reserved
 */
public class ComposedWeights {

  private final double[][] weights_input_to_hidden;
  private final double[][] weights_hidden_to_output;
  private final double[] biases_hidden;
  private final double[] biases_output;

  public ComposedWeights(double[][] weights_input_to_hidden, double[][] weights_hidden_to_output, double[] biases_hidden, double[] biases_output) {
    this.weights_input_to_hidden = weights_input_to_hidden;
    this.weights_hidden_to_output = weights_hidden_to_output;
    this.biases_hidden = biases_hidden;
    this.biases_output = biases_output;
  }

  public double[][] weights_input_to_hidden() {
    return weights_input_to_hidden;
  }

  public double[][] weights_hidden_to_output() {
    return weights_hidden_to_output;
  }

  public double[] biases_hidden() {
    return biases_hidden;
  }

  public double[] biases_output() {
    return biases_output;
  }

  @Override
  public String toString() {
    return "ComposedWeights{" +
      "weights_input_to_hidden=" + Arrays.toString(weights_input_to_hidden) +
      ", weights_hidden_to_output=" + Arrays.toString(weights_hidden_to_output) +
      ", biases_hidden=" + Arrays.toString(biases_hidden) +
      ", biases_output=" + Arrays.toString(biases_output) +
      '}';
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + Arrays.deepHashCode(weights_input_to_hidden);
    result = 31 * result + Arrays.deepHashCode(weights_hidden_to_output);
    result = 31 * result + Arrays.hashCode(biases_hidden);
    result = 31 * result + Arrays.hashCode(biases_output);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (!(obj instanceof ComposedWeights)) return false;
    return hashCode() == obj.hashCode();
  }
}
