package gg.acai.aurora.model;

import java.util.Optional;
import java.util.function.DoubleUnaryOperator;

/**
 * An activation function which is applied to the output of a neuron to determine its output.
 *
 * @author Clouke
 * @since 08.03.2023 22:19
 * © Aurora - All Rights Reserved
 */
public final class ActivationFunction {

  // Common activation functions
  public static final ActivationFunction LINEAR = new ActivationFunction("linear", x -> x, x -> 1);
  public static final ActivationFunction SIGMOID = new ActivationFunction("sigmoid", x -> 1 / (1 + Math.exp(-x)), x -> x * (1 - x));
  public static final ActivationFunction TANH = new ActivationFunction("tanh", Math::tanh, x -> 1 - Math.pow(x, 2));
  public static final ActivationFunction RELU = new ActivationFunction("relu", x -> Math.max(0, x), x -> x > 0 ? 1 : 0);
  public static final ActivationFunction SOFTMAX = new ActivationFunction("softmax", x -> 1 / (1 + Math.exp(-x)), x -> x * (1 - x));
  public static final ActivationFunction SOFTPLUS = new ActivationFunction("softplus", x -> Math.log(1 + Math.exp(x)), x -> 1 / (1 + Math.exp(-x)));

  private final String name;
  private final DoubleUnaryOperator function;
  private final DoubleUnaryOperator derivative;

  /**
   * Constructs a new activation function with the given name, function and derivative.
   *
   * @param name The name of the activation function
   * @param function The function of the activation function
   * @param derivative The derivative of the activation function
   */
  public ActivationFunction(String name, DoubleUnaryOperator function, DoubleUnaryOperator derivative) {
    this.name = name;
    this.function = function;
    this.derivative = derivative;
  }

  /**
   * Applies the activation function to the given value.
   *
   * @param x The value to apply the activation function to
   * @return Returns the value after the activation function has been applied
   */
  public double apply(double x) {
    return function.applyAsDouble(x);
  }

  /**
   * Applies the derivative of the activation function to the given value.
   *
   * @param x The value to apply the derivative of the activation function to
   * @return Returns the value after the derivative of the activation function has been applied
   */
  public double derivative(double x) {
    return derivative.applyAsDouble(x);
  }

  /**
   * Gets the name of the activation function.
   *
   * @return Returns the name of the activation function
   */
  @Override
  public String toString() {
    return name;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    ActivationFunction other = (ActivationFunction) obj;
    return name.hashCode() == other.name.hashCode();
  }

  /**
   * Gets the activation function with the given name.
   *
   * @param name The name of the activation function
   * @return Returns the activation function with the given name
   * @throws IllegalArgumentException If the activation function with the given name does not exist
   */
  public static ActivationFunction of(String name) {
    switch (name.toLowerCase()) {
      case "sigmoid": return SIGMOID;
      case "tanh": return TANH;
      case "relu": return RELU;
      case "linear": return LINEAR;
      case "softmax": return SOFTMAX;
      case "softplus": return SOFTPLUS;
      default:
        throw new IllegalArgumentException("Unknown activation function: " + name);
    }
  }

  /**
   * Gets the activation function with the given name, or returns an empty optional if the activation function does not exist.
   *
   * @param name The name of the activation function
   * @return Returns the activation function with the given name, or returns an empty optional if the activation function does not exist
   */
  public static Optional<ActivationFunction> optionally(String name) {
    try {
      ActivationFunction activation = of(name);
      return Optional.of(activation);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

}