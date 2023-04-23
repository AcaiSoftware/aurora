package gg.acai.aurora.logistic;

import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.ml.MLContextProvider;
import gg.acai.aurora.ml.Trainable;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Clouke
 * @since 01.04.2023 15:30
 * © Aurora - All Rights Reserved
 */
public class LogisticRegression extends AbstractLogisticRegression implements MLContextProvider, Trainable {

  private final double learningRate;
  private final int epochs;
  private double accuracy = 0.0;
  private double loss = 0.0;
  private int lastPrintedEpoch = 0;

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

    for (int epoch = 0; epoch <= epochs; epoch++) {
      for (int i = 0; i < inputs.length; i++) {
        double[] input = inputs[i];
        double[] output = outputs[i];
        double prediction = predict(input)[0];
        double error = prediction - output[0];

        for (int j = 0; j < weights.length; j++) {
          weights[j] -= learningRate * error * input[j];
        }

        for (int j = 0; j < biases.length; j++) {
          biases[j] -= learningRate * error;
        }

        tick(epoch, error);
      }
    }
    System.out.println("\n");
  }

  private void tick(int epoch, double error) {
    double loss = Math.pow(error, 2);
    double acc = 1.0 - loss;
    double lastLoss = this.loss;
    this.accuracy = acc < 1.0 ? acc : this.accuracy;
    this.loss = loss != 0.0 ? loss : this.loss;
    if (lastLoss != loss && loss != 0.0 && epoch % 10_000 == 0 && epoch != lastPrintedEpoch) {
      int steps = epoch / 10_000;
      System.out.println("Epoch: " + epoch + " | Step: " + (steps) + " | Loss: " + loss + " | Accuracy: " + accuracy);
      lastPrintedEpoch = epoch;
    }
  }

  @Override
  public double accuracy() {
    return accuracy;
  }
}
