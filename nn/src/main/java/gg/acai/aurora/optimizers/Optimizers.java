package gg.acai.aurora.optimizers;

/**
 * @author Clouke
 * @since 10.09.2023 15:58
 * © Aurora - All Rights Reserved
 */
public enum Optimizers {

  SGD(new StochasticGradientDescent()),
  ADAM(new AdamBuilder().build());

  private final Optimizer optimizer;

  Optimizers(Optimizer optimizer) {
    this.optimizer = optimizer;
  }

  public Optimizer get() {
    return optimizer;
  }

}
