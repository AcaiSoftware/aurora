package gg.acai.aurora.model;

import gg.acai.aurora.sets.TestSet;

/**
 * @author Clouke
 * @since 01.04.2023 15:38
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface Predictable {

  double[] predict(double[] input);

  default Evaluation evaluate(TestSet testSet) {
    Evaluation evaluation = new ModelMetrics(this, testSet);
    evaluation.evaluate();
    return evaluation;
  }
}
