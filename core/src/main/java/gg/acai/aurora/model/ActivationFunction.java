package gg.acai.aurora.model;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

  private static List<ActivationFunction> functions;

  // Common activation functions
  public static final ActivationFunction LINEAR = new ActivationFunction("linear", x -> x, x -> 1);
  public static final ActivationFunction SIGMOID = new ActivationFunction("sigmoid", x -> 1 / (1 + Math.exp(-x)), x -> x * (1 - x));
  public static final ActivationFunction TANH = new ActivationFunction("tanh", Math::tanh, x -> 1 - Math.pow(x, 2));
  public static final ActivationFunction RELU = new ActivationFunction("relu", x -> Math.max(0, x), x -> x > 0 ? 1 : 0);
  public static final ActivationFunction SOFTMAX = new ActivationFunction("softmax", ActivationFunction::softmax, ActivationFunction::softmaxDerivative);
  public static final ActivationFunction SOFTPLUS = new ActivationFunction("softplus", x -> Math.log(1 + Math.exp(x)), x -> 1 / (1 + Math.exp(-x)));
  public static final ActivationFunction SOFTSIGN = new ActivationFunction("softsign", x -> x / (1 + Math.abs(x)), x -> 1 / Math.pow(1 + Math.abs(x), 2));
  public static final ActivationFunction SINUSOID = new ActivationFunction("sinusoid", Math::sin, Math::cos);
  public static final ActivationFunction GAUSSIAN = new ActivationFunction("gaussian", x -> Math.exp(-Math.pow(x, 2)), x -> -2 * x * Math.exp(-Math.pow(x, 2)));
  public static final ActivationFunction ARCTAN = new ActivationFunction("arctan", Math::atan, x -> 1 / (Math.pow(x, 2) + 1));
  public static final ActivationFunction ELU = new ActivationFunction("elu", x -> x > 0 ? x : Math.exp(x) - 1, x -> x > 0 ? 1 : Math.exp(x));
  public static final ActivationFunction SELU = new ActivationFunction("selu", x -> 1.0507 * (x > 0 ? x : 1.67326 * Math.exp(x) - 1.67326), x -> 1.0507 * (x > 0 ? 1 : 1.67326 * Math.exp(x)));
  public static final ActivationFunction LEAKY_RELU = new ActivationFunction("leaky_relu", x -> x > 0 ? x : 0.01 * x, x -> x > 0 ? 1 : 0.01);
  public static final ActivationFunction PRELU = new ActivationFunction("prelu", x -> x > 0 ? x : 0.01 * x, x -> x > 0 ? 1 : 0.01);
  public static final ActivationFunction SWISH = new ActivationFunction("swish", x -> x / (1 + Math.exp(-x)), x -> x * (1 + x * (1 - 1 / (1 + Math.exp(-x)))));

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
    String lowercaseName = name.toLowerCase();
    return values().stream()
      .filter(activation -> activation.name.equals(lowercaseName))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException("No activation function with the name '" + name + "' exists"));
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

  public static List<ActivationFunction> values() {
    if (functions != null)
      return functions;

    List<ActivationFunction> act = new ArrayList<>();
    Arrays.stream(ActivationFunction.class.getDeclaredFields())
      .filter(f -> {
        int modifiers = f.getModifiers();
        return Modifier.isPublic(modifiers)
          && Modifier.isStatic(modifiers)
          && Modifier.isFinal(modifiers);
      })
      .filter(f -> f.getType() == ActivationFunction.class)
      .forEach(field -> {
        try {
          act.add((ActivationFunction) field.get(null));
        } catch (IllegalAccessException ignore) {}
      });

    return functions = Collections.unmodifiableList(act);
  }

  public static double softmax(double x) {
    double expX = Math.exp(x);
    double expSum = Math.exp(1) + expX;
    return expX / expSum;
  }

  public static double softmaxDerivative(double x) {
    double softmaxX = softmax(x);
    return softmaxX * (1 - softmaxX);
  }

}