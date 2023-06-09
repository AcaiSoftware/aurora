package gg.acai.aurora.logistic;

import gg.acai.acava.commons.Attributes;
import gg.acai.acava.commons.AttributesMapper;
import gg.acai.aurora.model.ModelConvertible;
import gg.acai.aurora.publics.io.Bar;
import gg.acai.aurora.publics.io.ComplexProgressTicker;
import gg.acai.aurora.model.ActivationFunction;
import gg.acai.aurora.model.MLContextProvider;
import gg.acai.aurora.model.Trainable;

/**
 * A trainable logistic regression implementation.
 *
 * @author Clouke
 * @since 01.04.2023 15:30
 * © Aurora - All Rights Reserved
 */
public class LogisticRegression extends AbstractLogisticRegression implements MLContextProvider, ModelConvertible<LogisticRegressionModel>, Trainable {

  private final ComplexProgressTicker ticker;
  private final Attributes attributes;
  private final double learningRate;
  private final int epochs;
  private double accuracy = 0.0;
  private double loss = 0.0;

  public LogisticRegression(int inputSize, int outputSize, double learningRate, int epochs, ActivationFunction activation, Bar progressBar) {
    super(inputSize, outputSize);
    this.learningRate = learningRate;
    this.epochs = epochs;
    this.activation = activation;
    this.ticker = new ComplexProgressTicker(progressBar, 1);
    this.attributes = new AttributesMapper();
  }

  /**
   * Fits the model to the given data.
   *
   * @param inputs The inputs to train on
   * @param outputs The outputs to train on
   */
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

        for (int j = 0; j < weights.length; j++)
          weights[j] -= learningRate * error * input[j];

        for (int j = 0; j < biases.length; j++)
          biases[j] -= learningRate * error;

        double loss = Math.pow(error, 2);
        double accuracy = 1.0 - loss;
        this.accuracy = accuracy <= 1.0 ? accuracy : this.accuracy;
        this.loss = loss != 0.0 ? loss : this.loss;
      }
      double percent = (double) epoch / epochs * 100.0;
      int pct = (int) percent;
      attributes.set("epoch", epoch)
        .set("accuracy", accuracy)
        .set("loss", loss)
        .set("stage", pct);

      ticker.tick(pct, attributes);
    }
    ticker.close();
  }

  @Override
  public double accuracy() {
    return accuracy;
  }

  @Override
  public LogisticRegressionModel toModel(String name) {
    LogisticRegressionModel model = new LogisticRegressionModel(weights, biases);
    model.name(name);
    return model;
  }
}
