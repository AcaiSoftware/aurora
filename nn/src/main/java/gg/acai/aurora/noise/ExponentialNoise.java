package gg.acai.aurora.noise;

import java.util.Random;

/**
 * @author Clouke
 * @since 11.09.2023 01:41
 * © Aurora - All Rights Reserved
 */
public class ExponentialNoise implements Noise {

  private static final Random RANDOM = new Random();
  private final double lambda;

  public ExponentialNoise(double lambda) {
    this.lambda = lambda;
  }

  @Override
  public void apply(double[][] weights) {
    for (int i = 0; i < weights.length; i++) {
      for (int j = 0; j < weights[i].length; j++) {
        double u = RANDOM.nextDouble();
        weights[i][j] -= Math.log(1 - u) / lambda;
      }
    }
  }
}
