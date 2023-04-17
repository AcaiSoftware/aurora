package gg.acai.aurora.ml;

import java.util.Optional;
import java.util.function.DoubleUnaryOperator;

/**
 * @author Clouke
 * @since 08.03.2023 22:19
 * © Aurora - All Rights Reserved
 */
public final class ActivationFunction {

  public static final ActivationFunction LINEAR = new ActivationFunction("linear", x -> x, x -> 1);
  public static final ActivationFunction SIGMOID = new ActivationFunction("sigmoid", x -> 1 / (1 + Math.exp(-x)), x -> x * (1 - x));
  public static final ActivationFunction TANH = new ActivationFunction("tanh", Math::tanh, x -> 1 - Math.pow(x, 2));
  public static final ActivationFunction RELU = new ActivationFunction("relu", x -> Math.max(0, x), x -> x > 0 ? 1 : 0);
  public static final ActivationFunction SOFTMAX = new ActivationFunction("softmax", x -> 1 / (1 + Math.exp(-x)), x -> x * (1 - x));
  public static final ActivationFunction SOFTPLUS = new ActivationFunction("softplus", x -> Math.log(1 + Math.exp(x)), x -> 1 / (1 + Math.exp(-x)));

  private final String name;
  private final DoubleUnaryOperator function;
  private final DoubleUnaryOperator derivative;

  public ActivationFunction(String name, DoubleUnaryOperator function, DoubleUnaryOperator derivative) {
    this.name = name;
    this.function = function;
    this.derivative = derivative;
  }

  public double apply(double x) {
    return function.applyAsDouble(x);
  }

  public double derivative(double x) {
    return derivative.applyAsDouble(x);
  }

  @Override
  public String toString() {
    return name;
  }

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

  public static Optional<ActivationFunction> optionally(String name) {
    try {
      ActivationFunction activation = of(name);
      return Optional.of(activation);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

}