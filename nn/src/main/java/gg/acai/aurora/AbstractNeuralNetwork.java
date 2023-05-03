package gg.acai.aurora;

import com.google.gson.annotations.SerializedName;
import gg.acai.acava.annotated.Optionally;
import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.model.MLContextProvider;
import gg.acai.aurora.model.MLContext;
import gg.acai.aurora.model.Predictable;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
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
  protected final double[] biases_hidden;
  protected final double[] biases_output;

  public AbstractNeuralNetwork(int inputSize, int hiddenSize, int outputSize) {
    weights_input_to_hidden = new double[inputSize][hiddenSize];
    biases_hidden = new double[hiddenSize];
    Random random = ThreadLocalRandom.current();
    for (int i = 0; i < inputSize; i++) {
      for (int j = 0; j < hiddenSize; j++) {
        weights_input_to_hidden[i][j] = random.nextGaussian();
      }
    }
    for (int i = 0; i < hiddenSize; i++) {
      biases_hidden[i] = random.nextGaussian();
    }
    weights_hidden_to_output = new double[hiddenSize][outputSize];
    biases_output = new double[outputSize];
    for (int i = 0; i < hiddenSize; i++) {
      for (int j = 0; j < outputSize; j++) {
        weights_hidden_to_output[i][j] = random.nextGaussian();
      }
    }
    for (int i = 0; i < outputSize; i++) {
      biases_output[i] = random.nextGaussian();
    }
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

  public ActivationFunction activation() {
    return activationFunction;
  }

  public WrappedNeuralNetwork wrap() {
    return new WrappedNeuralNetwork(weights_input_to_hidden, weights_hidden_to_output, biases_hidden, biases_output);
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
