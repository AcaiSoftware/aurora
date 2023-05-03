package gg.acai.aurora.model;

import gg.acai.aurora.sets.TestSet;

/**
 * A predictable model is a model that can be used to predict an output from an input.
 *
 * @author Clouke
 * @since 01.04.2023 15:38
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface Predictable {

  /**
   * Predicts an output from the given input.
   *
   * @param input The input to predict from
   * @return Returns the predicted output
   */
  double[] predict(double[] input);

  /**
   * Performs an evaluation on this predictable model.
   *
   * @param testSet The test set to evaluate on
   * @return Returns the evaluation
   */
  default Evaluation evaluate(TestSet testSet) {
    Evaluation evaluation = new ModelMetrics(this, testSet);
    evaluation.evaluate();
    return evaluation;
  }
}
