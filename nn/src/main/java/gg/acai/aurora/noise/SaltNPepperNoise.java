package gg.acai.aurora.noise;

/**
 * @author Clouke
 * @since 11.09.2023 01:41
 * © Aurora - All Rights Reserved
 */
public class SaltNPepperNoise implements Noise {

  private final double saltProb;
  private final double pepperProb;

  public SaltNPepperNoise(double saltProb, double pepperProb) {
    this.saltProb = saltProb;
    this.pepperProb = pepperProb;
  }

  @Override
  public void apply(double[][] weights) {
    for (int i = 0; i < weights.length; i++) {
      for (int j = 0; j < weights[i].length; j++) {
        double randomValue = Math.random();
        if (randomValue < saltProb) weights[i][j] = 1.0;
        else if (randomValue > 1 - pepperProb) weights[i][j] = 0.0;
      }
    }
  }
}
