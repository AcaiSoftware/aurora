package gg.acai.aurora.noise;

import java.util.Random;

/**
 * @author Clouke
 * @since 11.09.2023 01:42
 * © Aurora - All Rights Reserved
 */
public class GaussianNoise implements Noise {

  private static final Random RANDOM = new Random();

  private final double mean;
  private final double standardDeviation;

  public GaussianNoise(double mean, double standardDeviation) {
    this.mean = mean;
    this.standardDeviation = standardDeviation;
  }

  @Override
  public void apply(double[][] weights) {
    for (int i = 0; i < weights.length; i++) {
      for (int j = 0; j < weights[i].length; j++) {
        weights[i][j] += RANDOM.nextGaussian() * standardDeviation + mean;
      }
    }
  }
}
