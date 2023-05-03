package gg.acai.aurora.lvq;

import com.google.common.base.Preconditions;
import gg.acai.aurora.publics.io.Bar;

/**
 * A builder class for LVQ neural networks.
 *
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

  /**
   * Applies the given input size to this LVQ.
   * <p><strong>This is required</strong></p>
   *
   * @param inputSize The input size to apply
   * @return Returns this builder for chaining
   */
  public LVQNeuralNetworkBuilder inputSize(int inputSize) {
    this.inputSize = inputSize;
    return this;
  }

  /**
   * Applies the given output size to this LVQ.
   * <p><strong>This is required</strong></p>
   *
   * @param outputSize The output size to apply
   * @return Returns this builder for chaining
   */
  public LVQNeuralNetworkBuilder outputSize(int outputSize) {
    this.outputSize = outputSize;
    return this;
  }

  /**
   * Applies the given learning rate to this LVQ.
   * <p><strong>This is required</strong></p>
   *
   * @param learningRate The learning rate to apply
   * @return Returns this builder for chaining
   */
  public LVQNeuralNetworkBuilder learningRate(double learningRate) {
    this.learningRate = learningRate;
    return this;
  }

  /**
   * Applies the given decay rate to this LVQ.
   * <p><strong>This is required</strong></p>
   *
   * @param decayRate The decay rate to apply
   * @return Returns this builder for chaining
   */
  public LVQNeuralNetworkBuilder decayRate(double decayRate) {
    this.decayRate = decayRate;
    return this;
  }

  /**
   * Applies the given learning rate step to this LVQ.
   * <p><strong>This is optional</strong></p>
   *
   * @param learningRateStep The learning rate step to apply
   * @return Returns this builder for chaining
   */
  public LVQNeuralNetworkBuilder learningRateStep(double learningRateStep) {
    this.learningRateStep = learningRateStep;
    return this;
  }

  /**
   * Applies the given decay step to this LVQ.
   * <p><strong>This is optional</strong></p>
   *
   * @param decayStep The decay step to apply
   * @return Returns this builder for chaining
   */
  public LVQNeuralNetworkBuilder decayStep(double decayStep) {
    this.decayStep = decayStep;
    return this;
  }

  /**
   * Applies the given epochs to this LVQ.
   * <p><strong>This is required</strong>
   *
   * @param epochs The epochs to apply
   * @return Returns this builder for chaining
   */
  public LVQNeuralNetworkBuilder epochs(int epochs) {
    this.epochs = epochs;
    return this;
  }

  /**
   * Applies the given progress bar to this LVQ.
   * <p><strong>This is optional</strong>
   *
   * @param progressBar The progress bar to apply
   * @return Returns this builder for chaining
   */
  public LVQNeuralNetworkBuilder printing(Bar progressBar) {
    this.progressBar = progressBar;
    return this;
  }

  /**
   * Builds the LVQ neural network.
   *
   * @return Returns the built LVQ neural network
   * @throws IllegalArgumentException If the input size, output size, learning rate or epochs are
   */
  public LVQNeuralNetwork build() {
    Preconditions.checkArgument(inputSize > 0, "The input size must be greater than 0");
    Preconditions.checkArgument(outputSize > 0, "The output size must be greater than 0");
    Preconditions.checkArgument(learningRate > 0, "The learning rate must be greater than 0");
    Preconditions.checkArgument(epochs > 0, "The epochs must be greater than 0");
    return new LVQNeuralNetwork(this);
  }

}
