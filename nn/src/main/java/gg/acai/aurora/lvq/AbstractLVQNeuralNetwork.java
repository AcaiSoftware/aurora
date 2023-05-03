package gg.acai.aurora.lvq;

import gg.acai.aurora.QRMath;
import gg.acai.aurora.model.MLContext;
import gg.acai.aurora.model.MLContextProvider;
import gg.acai.aurora.model.Predictable;

import java.util.Random;
import java.util.function.BiFunction;

/**
 * @author Clouke
 * @since 28.04.2023 20:09
 * © Aurora - All Rights Reserved
 */
public abstract class AbstractLVQNeuralNetwork implements LVQ, Predictable, MLContextProvider {

  /**
   * Euclidean distance function.
   */
  protected static final BiFunction<double[], double[], Double> EUC = QRMath::distance;

  protected final transient Random random = new Random();
  protected final double[][] weights;
  protected final int numInputs;
  protected final int numOutputs;

  public AbstractLVQNeuralNetwork(double[][] weights, int numInputs, int numOutputs) {
    this.weights = weights;
    this.numInputs = numInputs;
    this.numOutputs = numOutputs;
  }

  public AbstractLVQNeuralNetwork(int numInputs, int numOutputs) {
    this.numInputs = numInputs;
    this.numOutputs = numOutputs;
    this.weights = new double[numOutputs][numInputs];
    for (int i = 0; i < numOutputs; i++) {
      for (int j = 0; j < numInputs; j++) {
        weights[i][j] = random.nextDouble();
      }
    }
  }

  /**
   * Classifies the input and returns the index of the winning neuron.
   *
   * @param input the input to classify
   * @return Returns the index of the winning neuron
   */
  @Override
  public double[] predict(double[] input) {
    return new double[] {
      classify(input)
    };
  }

  /**
   * Classifies the input and returns the index of the winning neuron.
   *
   * @param input the input to classify
   * @return Returns the index of the winning neuron
   */
  @Override
  public int classify(double[] input) {
    int winner = 0;
    double minDistance = EUC.apply(input, weights[0]);
    for (int i = 1; i < numOutputs; i++) {
      double distance = EUC.apply(input, weights[i]);
      if (distance < minDistance) {
        winner = i;
        minDistance = distance;
      }
    }
    return winner;
  }

  /**
   * Gets the ML context of this LVQ neural network.
   *
   * @return Returns the ML context of this LVQ neural network.
   */
  @Override
  public MLContext context() {
    return MLContext.LEARNING_VECTOR_QUANTIZATION;
  }
}
