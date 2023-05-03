package gg.acai.aurora.lvq;

import com.google.common.base.Preconditions;
import gg.acai.aurora.publics.io.Bar;

/**
 * @author Clouke
 * @since 28.04.2023 20:12
 * © Aurora - All Rights Reserved
 */
public class LVQNeuralNetworkBuilder {

  protected int inputSize;
  protected int outputSize;
  protected double learningRate;
  protected double decayRate;
  protected double learningRateStep = 0.99;
  protected double decayStep = 0.99;
  protected int epochs;
  protected Bar progressBar = Bar.CLASSIC;

  public LVQNeuralNetworkBuilder inputSize(int inputSize) {
    this.inputSize = inputSize;
    return this;
  }

  public LVQNeuralNetworkBuilder outputSize(int outputSize) {
    this.outputSize = outputSize;
    return this;
  }

  public LVQNeuralNetworkBuilder learningRate(double learningRate) {
    this.learningRate = learningRate;
    return this;
  }

  public LVQNeuralNetworkBuilder decayRate(double decayRate) {
    this.decayRate = decayRate;
    return this;
  }

  public LVQNeuralNetworkBuilder learningRateStep(double learningRateStep) {
    this.learningRateStep = learningRateStep;
    return this;
  }

  public LVQNeuralNetworkBuilder decayStep(double decayStep) {
    this.decayStep = decayStep;
    return this;
  }

  public LVQNeuralNetworkBuilder epochs(int epochs) {
    this.epochs = epochs;
    return this;
  }

  public LVQNeuralNetworkBuilder printing(Bar progressBar) {
    this.progressBar = progressBar;
    return this;
  }

  public LVQNeuralNetwork build() {
    Preconditions.checkArgument(inputSize > 0, "The input size must be greater than 0");
    Preconditions.checkArgument(outputSize > 0, "The output size must be greater than 0");
    Preconditions.checkArgument(learningRate > 0, "The learning rate must be greater than 0");
    Preconditions.checkArgument(epochs > 0, "The epochs must be greater than 0");
    return new LVQNeuralNetwork(this);
  }

}
