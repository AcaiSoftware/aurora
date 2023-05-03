package gg.acai.aurora.epoch;

import gg.acai.aurora.model.EpochAction;
import gg.acai.aurora.NeuralNetwork;

/**
 * @author Clouke
 * @since 01.05.2023 19:53
 * © Aurora - All Rights Reserved
 */
public class EpochAutoSave implements EpochAction<NeuralNetwork> {

  private final int saveEvery;
  private final String savePath;
  private boolean broke = false;

  public EpochAutoSave(int saveEvery, String savePath) {
    this.saveEvery = saveEvery;
    this.savePath = savePath;
  }

  @Override
  public void onEpochIteration(int epoch, NeuralNetwork nn) {
    if (epoch % saveEvery != 0 || broke) {
      return;
    }

    try {
      nn.toModel().save(savePath);
    } catch (Exception e) {
      broke = true;
    }
  }
}
