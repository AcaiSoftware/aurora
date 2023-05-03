package gg.acai.aurora.model;

/**
 * @author Clouke
 * @since 01.05.2023 19:51
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface EpochAction<T> {
  /**
   * Action to be performed on each epoch iteration
   *
   * @param epoch The current epoch
   * @param t The type of the epoch action - most likely a model
   */
  void onEpochIteration(int epoch, T t);
}
