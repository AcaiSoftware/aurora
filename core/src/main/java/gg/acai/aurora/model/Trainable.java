package gg.acai.aurora.model;

import gg.acai.aurora.sets.DataSet;

/**
 * A trainable model is a model that can be trained on a data set.
 *
 * @author Clouke
 * @since 02.04.2023 22:39
 * © Aurora - All Rights Reserved
 */
public interface Trainable extends Accuracy {

  /**
   * Trains the model on the given inputs and outputs.
   *
   * @param inputs The inputs to train on
   * @param outputs The outputs to train on
   */
  void train(double[][] inputs, double[][] outputs);

  /**
   * Trains the model on the given data set.
   *
   * @param set The data set to train on
   */
  default void train(DataSet set) {
    train(set.inputs(), set.targets());
  }

  /**
   * Trains the model on pre-set data.
   *
   * @throws UnsupportedOperationException If the model does not support training without data.
   */
  default void train() {
    throw new UnsupportedOperationException("This model does not support training without data.");
  }

}
