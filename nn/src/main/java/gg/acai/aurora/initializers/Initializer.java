package gg.acai.aurora.initializers;

/**
 * @author Clouke
 * @since 10.09.2023 02:30
 * © Aurora - All Rights Reserved
 */
public enum Initializer {
  ZERO(new ZeroInitializer()),
  XAVIER(new XavierInitializer()),
  RANDOM(new RandomInitializer()),
  GAUSSIAN(new GaussianInitializer()),
  LE_CUN(new LeCunInitializer()),
  HE(new HeInitializer());

  private final WeightInitializer initializer;

  Initializer(WeightInitializer initializer) {
    this.initializer = initializer;
  }

  public WeightInitializer get() {
    return initializer;
  }
}
