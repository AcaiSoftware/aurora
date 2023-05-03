package gg.acai.aurora.model;

/**
 * A standard accuracy provider.
 *
 * @author Clouke
 * @since 16.04.2023 22:01
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface Accuracy {
  /**
   * Gets the accuracy of this model.
   *
   * @return Returns the accuracy of this model.
   */
  double accuracy();
}
