package gg.acai.aurora.hyperparameter.evaluator;

import gg.acai.aurora.hyperparameter.EvaluatorScoreContext;
import gg.acai.aurora.hyperparameter.TuningEvaluator;
import gg.acai.aurora.model.Trainable;

/**
 * @author Clouke
 * @since 16.04.2023 21:22
 * © Aurora - All Rights Reserved
 */
public class BestAccuracyEvaluator implements TuningEvaluator {

  @Override
  public double evaluate(Trainable trainable, double[][] inputs, double[][] outputs) {
    trainable.train(inputs, outputs);
    return trainable.accuracy();
  }

  @Override
  public EvaluatorScoreContext context() {
    return EvaluatorScoreContext.HIGHEST;
  }
}
