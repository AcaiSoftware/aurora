package gg.acai.aurora;

/**
 * A save point for model saving.
 *
 * @author Clouke
 * @since 27.02.2023 18:49
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface SavePoint extends Runnable {
  /**
   * Stores the model.
   */
  void save();

  @Override
  default void run() {
    save();
  }
}
