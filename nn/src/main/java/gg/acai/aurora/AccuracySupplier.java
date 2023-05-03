package gg.acai.aurora;

/**
 * An Accuracy supplier for the purpose of testing the accuracy of a neural network
 *
 * @author Clouke
 * @since 02.05.2023 20:42
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface AccuracySupplier {
  /**
   * A test to be performed on each epoch iteration for the purpose of accuracy
   *
   * @return The accuracy of the test
   */
  double[] test();
}
