package gg.acai.aurora;

/**
 * @author Clouke
 * @since 27.02.2023 18:49
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface SavePoint extends Runnable {

  void save();

  @Override
  default void run() {
    save();
  }
}
