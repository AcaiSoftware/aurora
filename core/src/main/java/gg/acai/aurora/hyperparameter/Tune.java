package gg.acai.aurora.hyperparameter;

/**
 * @author Clouke
 * @since 16.04.2023 21:07
 * © Aurora - All Rights Reserved
 */
public interface Tune {

  /**
   * Gets the learning rate in this tune.
   *
   * @return Returns the learning rate in this tune.
   */
  double learningRate();

  /**
   * Gets the epochs in this tune.
   *
   * @return Returns the epochs in this tune.
   */
  int epochs();

  /**
   * Gets the layers in this tune.
   *
   * @return Returns the layers in this tune.
   */
  int layers();

}
