package gg.acai.aurora.core.ml.nn;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * @author Clouke
 * @since 02.03.2023 17:10
 * © Aurora - All Rights Reserved
 */
public class WrappedNeuralNetwork {

  private final double[][] weights_input_to_hidden;
  private final double[][] weights_hidden_to_output;
  private final double[] biases_hidden;
  private final double[] biases_output;

  public WrappedNeuralNetwork(double[][] weights_input_to_hidden, double[][] weights_hidden_to_output, double[] biases_hidden, double[] biases_output) {
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

  public <T extends AbstractNeuralNetwork> T unwrapAs(Class<T> clazz) {
    Constructor<T> constructor;
    try {
      constructor = clazz.getDeclaredConstructor(double[][].class, double[][].class, double[].class, double[].class);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException("Could not find constructor for class " + clazz.getName(), e);
    }
    constructor.setAccessible(true);
    try {
      return constructor.newInstance(weights_input_to_hidden, weights_hidden_to_output, biases_hidden, biases_output);
    } catch (Exception e) {
      throw new RuntimeException("Could not instantiate class " + clazz.getName(), e);
    }
  }

  @Override
  public String toString() {
    return "WrappedNeuralNetwork{" +
      "weights_input_to_hidden=" + Arrays.deepToString(weights_input_to_hidden) +
      ", weights_hidden_to_output=" + Arrays.deepToString(weights_hidden_to_output) +
      ", biases_hidden=" + Arrays.toString(biases_hidden) +
      ", biases_output=" + Arrays.toString(biases_output) +
      '}';
  }

  @Override
  public int hashCode() {
    int result = Arrays.deepHashCode(weights_input_to_hidden);
    result = 31 * result ^ Arrays.deepHashCode(weights_hidden_to_output);
    result = 31 * result ^ Arrays.hashCode(biases_hidden);
    result = 31 * result ^ Arrays.hashCode(biases_output);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    WrappedNeuralNetwork that = (WrappedNeuralNetwork) o;

    if (!Arrays.deepEquals(weights_input_to_hidden, that.weights_input_to_hidden)) return false;
    if (!Arrays.deepEquals(weights_hidden_to_output, that.weights_hidden_to_output)) return false;
    if (!Arrays.equals(biases_hidden, that.biases_hidden)) return false;
    return Arrays.equals(biases_output, that.biases_output);
  }

  @Override @SuppressWarnings("MethodDoesntCallSuperMethod")
  public WrappedNeuralNetwork clone() {
    return new WrappedNeuralNetwork(
      weights_input_to_hidden.clone(),
      weights_hidden_to_output.clone(),
      biases_hidden.clone(),
      biases_output.clone()
    );
  }
}
