package gg.acai.aurora.earlystop;

import gg.acai.aurora.Attributes;

/**
 * @author Clouke
 * @since 23.04.2023 06:41
 * © Aurora - All Rights Reserved
 */
public class PrecisionHalt extends AbstractEarlyStop {

  private final double target;
  private final int minEpochs;
  private boolean reached;

  public PrecisionHalt(int minEpochs, double target) {
    this.minEpochs = minEpochs;
    this.target = target;
  }

  public PrecisionHalt(double target) {
    this(-1, target);
  }

  @Override
  public void tick(Attributes attributes) {
    double accuracy = attributes.get("accuracy");
    if (minEpochs > 0) {
      int epoch = attributes.get("epoch");
      reached = epoch >= minEpochs && accuracy >= target;
    }
    else reached = accuracy >= target;
  }

  @Override
  public boolean shouldStop() {
    return reached;
  }
}
