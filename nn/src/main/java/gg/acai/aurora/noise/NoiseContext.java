package gg.acai.aurora.noise;

/**
 * @author Clouke
 * @since 11.09.2023 01:47
 * © Aurora - All Rights Reserved
 */
public enum NoiseContext {
  EXPONENTIAL(new ExponentialNoise(0.01)),
  GAUSSIAN(new GaussianNoise(
    0.0,
    0.01
  )),
  SALT_PEPPER(new SaltNPepperNoise(0.01, 0.01)),
  ADJUST_WEIGHT(new AdjustWeightNoise(0.01));

  private final Noise noise;

  NoiseContext(Noise noise) {
    this.noise = noise;
  }

  public Noise get() {
    return noise;
  }
}
