package gg.acai.aurora.epoch;

import gg.acai.aurora.model.EpochAction;
import gg.acai.aurora.NeuralNetwork;

/**
 * An epoch action that automatically saves the model every x epochs.
 * <p> If the model fails to save, the action will be disabled.
 *
 * @author Clouke
 * @since 01.05.2023 19:53
 * © Aurora - All Rights Reserved
 */
public class EpochAutoSave implements EpochAction<NeuralNetwork> {

  private final int saveEvery;
  private final String savePath;
  private boolean broke = false;

  /**
   * Constructs a new epoch auto save action.
   *
   * @param saveEvery The modulo of epochs to save the model on
   * @param savePath The path to save the model to
   */
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
