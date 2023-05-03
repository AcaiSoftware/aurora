package gg.acai.aurora.hyperparameter;

import gg.acai.aurora.model.Trainable;

/**
 * @author Clouke
 * @since 16.04.2023 21:11
 * © Aurora - All Rights Reserved
 */
public interface TuningEvaluator {

  double evaluate(Trainable trainable, double[][] inputs, double[][] outputs);

  EvaluatorScoreContext context();

}
