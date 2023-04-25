package gg.acai.aurora.optimizers;

/**
 * @author Clouke
 * @since 25.04.2023 11:22
 * © Aurora - All Rights Reserved
 */
public abstract class AbstractOptimizer implements Optimizer {

  protected double learningRate;

  public AbstractOptimizer(double learningRate) {
    this.learningRate = learningRate;
  }

  public AbstractOptimizer() {}

  @Override
  public Optimizer apply(double learningRate) {
    this.learningRate = learningRate;
    return this;
  }
}
