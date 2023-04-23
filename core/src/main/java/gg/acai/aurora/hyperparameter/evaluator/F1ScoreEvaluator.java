package gg.acai.aurora.hyperparameter.evaluator;

import gg.acai.aurora.hyperparameter.EvaluatorScoreContext;
import gg.acai.aurora.hyperparameter.TuningEvaluator;
import gg.acai.aurora.ml.Predictable;
import gg.acai.aurora.ml.Trainable;

/**
 * @author Clouke
 * @since 18.04.2023 18:16
 * © Aurora - All Rights Reserved
 */
@Deprecated
public class F1ScoreEvaluator implements TuningEvaluator {

  private final double[] predictions;
  private final double positiveThreshold;
  private final double negativeThreshold;

  public F1ScoreEvaluator(double[] predictions, double positiveThreshold, double negativeThreshold) {
    this.predictions = predictions;
    this.positiveThreshold = positiveThreshold;
    this.negativeThreshold = negativeThreshold;
  }

  @Override
  public double evaluate(Trainable trainable, double[][] inputs, double[][] outputs) {
    if (!(trainable instanceof Predictable)) {
      throw new IllegalArgumentException("Trainable must be an instance of Predictable");
    }
    Predictable predictable = (Predictable) trainable;
    double[] predictions = predictable.predict(this.predictions);
    double truePositives = 0.0;
    double falsePositives = 0.0;
    double falseNegatives = 0.0;
    for (int i = 0; i < predictions.length; i++) {
      if (predictions[i] >= positiveThreshold && this.predictions[i] >= positiveThreshold) {
        truePositives++;
      } else if (predictions[i] >= positiveThreshold && this.predictions[i] <= negativeThreshold) {
        falsePositives++;
      } else if (predictions[i] <= negativeThreshold && this.predictions[i] >= positiveThreshold) {
        falseNegatives++;
      }
    }

    double precision = truePositives / (truePositives + falsePositives);
    double recall = truePositives / (truePositives + falseNegatives);

    if (Double.isNaN(precision) || Double.isNaN(recall)) {
      return 0.0;
    }

    return 2.0 * ((precision * recall) / (precision + recall));
  }

  @Override
  public EvaluatorScoreContext context() {
    return EvaluatorScoreContext.HIGHEST;
  }
}
