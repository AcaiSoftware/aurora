package gg.acai.aurora.earlystop;

import gg.acai.acava.commons.Attributes;
import gg.acai.aurora.QRMath;

/**
 * A stagnation early stopper terminating the epoch iteration when the model stagnates (accuracy does not improve).
 * <p>
 * Checks whether the accuracy has not improved for a given number of cycles
 * and terminates the epoch iteration if so.
 *
 * @author Clouke
 * @since 23.04.2023 03:49
 * © Aurora - All Rights Reserved
 */
public class Stagnation implements EarlyStop {

  private final CycleBuffer buffer;
  private final double maxAccuracy;
  private double lastAccuracy;
  private boolean stagnated;
  private int lastStage = -1;

  /**
   * Constructs a new Stagnation early stopper with the given maximum cycles and maximum accuracy.
   *
   * @param maxCycles The max number of cycles to buffer for before terminating the epoch iteration.
   * @param maxAccuracy If the accuracy is less than this value, the model is considered to have stagnated.
   */
  public Stagnation(int maxCycles, double maxAccuracy) {
    this.buffer = new CycleBuffer(maxCycles);
    this.maxAccuracy = maxAccuracy;
  }

  public Stagnation(int maxCycles) {
    this(maxCycles, 80.0);
  }

  @Override
  public void tick(Attributes attributes) {
    int stage = attributes.get("stage");
    if (lastStage == -1) {
      lastStage = stage;
      return;
    }

    if (stage != lastStage) {
      double accuracy = QRMath.round(attributes.get("accuracy"));
      if (accuracy == lastAccuracy && accuracy < maxAccuracy)
        stagnated = buffer.reached();
      else
        buffer.wipe();
      lastAccuracy = accuracy;
    }
    lastStage = stage;
  }

  @Override
  public String terminationMessage() {
    return "\nThe training process has been stopped due to training stagnation (The model got stuck and is not improving).\n" +
      "Options:\n" +
      "  - Try re-training the model\n" +
      "  - Change the learning rate\n" +
      "  - Change the model architecture\n" +
      "  - Change the training data\n" +
      "  - Change the training parameters (epochs, etc.)\n" +
      "  - Try using HyperparameterTuning to find the best parameters for your model.\n" +
      "Buffered for " + buffer.current() + "/" + buffer.max();
  }

  @Override
  public boolean terminable() {
    return stagnated;
  }
}
