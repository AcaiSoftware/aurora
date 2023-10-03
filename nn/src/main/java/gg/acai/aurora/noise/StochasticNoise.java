package gg.acai.aurora.noise;

import java.util.Objects;
import java.util.Random;

/**
 * @author Clouke
 * @since 12.09.2023 01:43
 * © Aurora - All Rights Reserved
 */
public class StochasticNoise implements Noise {

  private final Noise noise;
  private final Random random;

  public StochasticNoise(Noise noise, long seed) {
    this.noise = Objects.requireNonNull(
      noise,
      "the provided noise cannot be null"
    );
    this.random = new Random(seed);
  }

  public StochasticNoise(Noise noise) {
    this(
      noise,
      System.currentTimeMillis()
    );
  }

  public StochasticNoise(NoiseContext ctx) {
    this(ctx.get());
  }

  @Override
  public void apply(double[][] weights) {
    if (random.nextBoolean())
      noise.apply(weights);
  }
}
