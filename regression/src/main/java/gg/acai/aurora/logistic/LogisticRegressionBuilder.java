package gg.acai.aurora.logistic;

import com.google.common.base.Preconditions;
import gg.acai.aurora.publics.io.Bar;
import gg.acai.aurora.model.ActivationFunction;

/**
 * @author Clouke
 * @since 27.04.2023 15:08
 * © Aurora - All Rights Reserved
 */
public class LogisticRegressionBuilder {

  private int inputSize;
  private int outputSize;
  private double learningRate;
  private int epochs;
  private Bar progressBar = Bar.CLASSIC;
  private ActivationFunction activationFunction = ActivationFunction.SIGMOID;

  public LogisticRegressionBuilder inputSize(int inputSize) {
    this.inputSize = inputSize;
    return this;
  }

  public LogisticRegressionBuilder outputSize(int outputSize) {
    this.outputSize = outputSize;
    return this;
  }

  public LogisticRegressionBuilder learningRate(double learningRate) {
    this.learningRate = learningRate;
    return this;
  }

  public LogisticRegressionBuilder epochs(int epochs) {
    this.epochs = epochs;
    return this;
  }

  public LogisticRegressionBuilder printing(Bar progressBar) {
    this.progressBar = progressBar;
    return this;
  }

  public LogisticRegressionBuilder activation(ActivationFunction activationFunction) {
    this.activationFunction = activationFunction;
    return this;
  }

  public LogisticRegression build() {
    Preconditions.checkArgument(inputSize > 0, "Input size must be greater than 0");
    Preconditions.checkArgument(outputSize > 0, "Output size must be greater than 0");
    Preconditions.checkArgument(learningRate > 0, "Learning rate must be greater than 0");
    Preconditions.checkArgument(epochs > 0, "Epochs must be greater than 0");
    return new LogisticRegression(inputSize, outputSize, learningRate, epochs, activationFunction, progressBar);
  }

}
