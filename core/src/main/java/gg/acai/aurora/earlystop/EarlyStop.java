package gg.acai.aurora.earlystop;

import gg.acai.acava.commons.Attributes;

/**
 * Early stopping performs a check on model performance and terminates training if a certain condition is met.
 *
 * @author Clouke
 * @since 23.04.2023 03:47
 * © Aurora - All Rights Reserved
 */
public interface EarlyStop {

  /**
   * Performs a check on model performance and terminates training if a certain condition is met.
   *
   * @param attributes The attributes to check.
   */
  void tick(Attributes attributes);

  /**
   * Gets whether the training should be terminated.
   *
   * @return Returns whether the training should be terminated.
   */
  boolean terminable();

  /**
   * Gets the termination message.
   *
   * @return Returns the termination message.
   */
  String terminationMessage();

}
