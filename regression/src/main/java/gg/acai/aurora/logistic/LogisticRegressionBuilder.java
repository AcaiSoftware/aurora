package gg.acai.aurora.logistic;

import com.google.common.base.Preconditions;
import gg.acai.aurora.publics.io.Bar;
import gg.acai.aurora.model.ActivationFunction;

/**
 * A builder class for logistic regression models.
 *
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

  /**
   * Applies the input size to this builder.
   *
   * @param inputSize The input size to apply
   * @return Returns this builder for chaining
   */
  public LogisticRegressionBuilder inputSize(int inputSize) {
    this.inputSize = inputSize;
    return this;
  }

  /**
   * Applies the output size to this builder.
   *
   * @param outputSize The output size to apply
   * @return Returns this builder for chaining
   */
  public LogisticRegressionBuilder outputSize(int outputSize) {
    this.outputSize = outputSize;
    return this;
  }

  /**
   * Applies the learning rate to this builder.
   *
   * @param learningRate The learning rate to apply
   * @return Returns this builder for chaining
   */
  public LogisticRegressionBuilder learningRate(double learningRate) {
    this.learningRate = learningRate;
    return this;
  }

  /**
   * Applies the epochs to this builder.
   *
   * @param epochs The epochs to apply
   * @return Returns this builder for chaining
   */
  public LogisticRegressionBuilder epochs(int epochs) {
    this.epochs = epochs;
    return this;
  }

  /**
   * Applies the progress bar to this builder.
   *
   * @param progressBar The progress bar to apply
   * @return Returns this builder for chaining
   */
  public LogisticRegressionBuilder printing(Bar progressBar) {
    this.progressBar = progressBar;
    return this;
  }

  /**
   * Applies the activation function to this builder.
   *
   * @param activationFunction The activation function to apply
   * @return Returns this builder for chaining
   */
  public LogisticRegressionBuilder activation(ActivationFunction activationFunction) {
    this.activationFunction = activationFunction;
    return this;
  }

  /**
   * Builds the logistic regression model.
   *
   * @return Returns the built logistic regression model
   */
  public LogisticRegression build() {
    Preconditions.checkArgument(inputSize > 0, "Input size must be greater than 0");
    Preconditions.checkArgument(outputSize > 0, "Output size must be greater than 0");
    Preconditions.checkArgument(learningRate > 0, "Learning rate must be greater than 0");
    Preconditions.checkArgument(epochs > 0, "Epochs must be greater than 0");
    return new LogisticRegression(inputSize, outputSize, learningRate, epochs, activationFunction, progressBar);
  }

}
