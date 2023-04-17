package gg.acai.aurora.logistic;

import gg.acai.aurora.ml.ActivationFunction;
import gg.acai.aurora.ml.MLContextProvider;
import gg.acai.aurora.ml.Trainable;

/**
 * @author Clouke
 * @since 01.04.2023 15:30
 * © Aurora - All Rights Reserved
 */
public class LogisticRegression extends AbstractLogisticRegression implements MLContextProvider, Trainable {

  private final double learningRate;
  private final int epochs;
  private double accuracy;

  public LogisticRegression(int inputSize, int outputSize, double learningRate, int epochs, ActivationFunction activation) {
    super(inputSize, outputSize);
    this.learningRate = learningRate;
    this.epochs = epochs;
    this.activation = activation;
  }

  @Override
  public void train(double[][] inputs, double[][] outputs) {
    if (inputs.length != outputs.length)
      throw new IllegalArgumentException("Inputs and outputs must be the same size");

    for (int epoch = 0; epoch < epochs; epoch++) {
      for (int i = 0; i < inputs.length; i++) {
        double[] input = inputs[i];
        double[] output = outputs[i];
        double prediction = predict(input)[0];

        for (int j = 0; j < weights.length; j++) {
          weights[j] -= learningRate * (prediction - output[0]) * input[j];
        }

        for (int j = 0; j < biases.length; j++) {
          biases[j] -= learningRate * (prediction - output[0]);
        }
      }
    }
  }

  @Override
  public double accuracy() {
    return 0.0;
  }
}
