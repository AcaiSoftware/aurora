package gg.acai.aurora.noise;

/**
 * @author Clouke
 * @since 11.09.2023 01:29
 * © Aurora - All Rights Reserved
 */
public class AdjustWeightNoise implements Noise {

  private final double noiseRate;

  public AdjustWeightNoise(double noiseRate) {
    this.noiseRate = noiseRate;
  }

  @Override
  public void apply(double[][] weights) {
    for (int i = 0; i < weights.length; i++) {
      for (int j = 0; j < weights[i].length; j++) {
        double rand_value = (2.0 * Math.random() - 1.0);
        weights[i][j] += rand_value * noiseRate;
      }
    }
  }
}
