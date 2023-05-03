package gg.acai.aurora.hyperparameter;

import gg.acai.aurora.model.Trainable;

/**
 * @author Clouke
 * @since 16.04.2023 21:11
 * © Aurora - All Rights Reserved
 */
public interface TuningEvaluator {

  /**
   * Evaluates the accuracy of the trainable with the given inputs and outputs.
   *
   * @param trainable The trainable to evaluate.
   * @param inputs The inputs to evaluate with.
   * @param outputs The outputs to evaluate with.
   * @return Returns the score metric.
   */
  double evaluate(Trainable trainable, double[][] inputs, double[][] outputs);

  /**
   * Gets the context of this evaluator.
   *
   * @return Returns the context of this evaluator.
   */
  EvaluatorScoreContext context();

}
