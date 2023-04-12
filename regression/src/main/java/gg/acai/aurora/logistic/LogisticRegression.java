package gg.acai.aurora.logistic;

import gg.acai.aurora.ml.ActivationFunction;
import gg.acai.aurora.ml.MLContextProvider;

import java.util.Arrays;

/**
 * @author Clouke
 * @since 01.04.2023 15:30
 * © Aurora - All Rights Reserved
 */
public class LogisticRegression extends AbstractLogisticRegression implements MLContextProvider {

  private final double learningRate;
  private final int epochs;

  public LogisticRegression(int inputSize, int outputSize, double learningRate, int epochs, ActivationFunction activation) {
    super(inputSize, outputSize);
    this.learningRate = learningRate;
    this.epochs = epochs;
    this.activation = activation;
  }

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

  public double[] getWeights() {
    return weights;
  }

  public double[] getBiases() {
    return biases;
  }

  public static void main(String[] args) {
    double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
    double[][] outputs = {{0}, {0}, {0}, {1}};

    LogisticRegression model = new LogisticRegression(2, 1, 0.1, 1000, ActivationFunction.SIGMOID);
    model.train(inputs, outputs);

    System.out.println("Weights: " + Arrays.toString(model.getWeights()));
    System.out.println("Biases: " + Arrays.toString(model.getBiases()));

    for (double[] input : inputs) {
      double[] prediction = model.predict(input);
      System.out.println("Input: " + Arrays.toString(input) + " -> Prediction: " + Arrays.toString(prediction));
    }
  }



}
