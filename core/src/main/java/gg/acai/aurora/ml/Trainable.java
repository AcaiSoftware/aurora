package gg.acai.aurora.ml;

import gg.acai.aurora.Accuracy;
import gg.acai.aurora.sets.DataSet;

/**
 * @author Clouke
 * @since 02.04.2023 22:39
 * © Aurora - All Rights Reserved
 */
public interface Trainable extends Accuracy {

  void train(double[][] inputs, double[][] outputs);

  default void train(DataSet set) {
    train(set.inputs(), set.targets());
  }

}
