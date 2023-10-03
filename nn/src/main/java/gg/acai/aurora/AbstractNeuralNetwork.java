package gg.acai.aurora;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;
import gg.acai.acava.annotated.Optionally;
import gg.acai.aurora.initializers.ComposedWeights;
import gg.acai.aurora.initializers.WeightInitializer;
import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.model.MLContextProvider;
import gg.acai.aurora.model.MLContext;
import gg.acai.aurora.model.Predictable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Random;

/**
 * An abstract neural network implementation.
 *
 * @author Clouke
 * @since 15.02.2023 06:38
 * © Acava - All Rights Reserved
 */
public abstract class AbstractNeuralNetwork implements MLContextProvider, Predictable {

  @Optionally
  protected String name;
  @SerializedName("activation")
  protected ActivationFunction activationFunction;

  protected double[][] weights_input_to_hidden;
  protected double[][] weights_hidden_to_output;
  protected double[] biases_hidden;
  protected double[] biases_output;

  public AbstractNeuralNetwork(long seed, int inputSize, int hiddenSize, int outputSize, WeightInitializer initializer) {
    ComposedWeights composer = initializer.initialize(
      seed,
      inputSize,
      hiddenSize,
      outputSize
    );

    Preconditions.checkNotNull(
      composer,
      "composer cannot be null, WeightInitializer=" + initializer.getClass().getName()
    );

    weights_input_to_hidden = composer.weights_input_to_hidden();
    weights_hidden_to_output = composer.weights_hidden_to_output();
    biases_hidden = composer.biases_hidden();
    biases_output = composer.biases_output();
  }

  public AbstractNeuralNetwork(double[][] weights_input_to_hidden, double[][] weights_hidden_to_output, double[] biases_hidden, double[] biases_output) {
    this.weights_input_to_hidden = weights_input_to_hidden;
    this.weights_hidden_to_output = weights_hidden_to_output;
    this.biases_hidden = biases_hidden;
    this.biases_output = biases_output;
  }

  public void setActivationFunction(ActivationFunction activationFunction) {
    this.activationFunction = activationFunction;
  }

  @Override
  public double[] predict(double[] input) {
    double[] hidden = new double[weights_input_to_hidden[0].length];
    for (int i = 0; i < hidden.length; i++) {
      hidden[i] = biases_hidden[i];
      for (int j = 0; j < input.length; j++) {
        hidden[i] += input[j] * weights_input_to_hidden[j][i];
      }
      hidden[i] = activationFunction.apply(hidden[i]);
    }
    double[] output = new double[weights_hidden_to_output[0].length];
    for (int i = 0; i < output.length; i++) {
      output[i] = biases_output[i];
      for (int j = 0; j < hidden.length; j++) {
        output[i] += hidden[j] * weights_hidden_to_output[j][i];
      }
      output[i] = activationFunction.apply(output[i]);
    }

    return output;
  }

  /**
   * Gets the activation function of this neural network.
   *
   * @return The activation function of this neural network.
   */
  @Nullable
  public ActivationFunction activation() {
    return activationFunction;
  }

  /**
   * Wraps this neural network into a {@link WrappedNeuralNetwork}.
   *
   * @return The wrapped neural network.
   */
  @Nonnull
  public WrappedNeuralNetwork wrap() {
    return new WrappedNeuralNetwork(
      weights_input_to_hidden,
      weights_hidden_to_output,
      biases_hidden,
      biases_output
    );
  }

  @Override
  public MLContext context() {
    return MLContext.NEURAL_NETWORK;
  }

  @Override
  public String toString() {
    return "AbstractNeuralNetwork{" +
      "model='" + name + '\'' +
      ", activationFunction=" + activationFunction +
      ", weights_input_to_hidden=" + Arrays.deepToString(weights_input_to_hidden) +
      ", weights_hidden_to_output=" + Arrays.deepToString(weights_hidden_to_output) +
      ", biases_hidden=" + Arrays.toString(biases_hidden) +
      ", biases_output=" + Arrays.toString(biases_output) +
      '}';
  }
}
