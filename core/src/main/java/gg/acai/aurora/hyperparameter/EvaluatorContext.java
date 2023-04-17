package gg.acai.aurora.hyperparameter;

import java.util.Map;

/**
 * @author Clouke
 * @since 16.04.2023 21:12
 * © Aurora - All Rights Reserved
 */
public enum EvaluatorContext {
  HIGHEST,
  LOWEST;

  public HyperparameterTuningResult findBest(Map<Tune, Double> scores) {
    Tune best = null;
    boolean highest = this == HIGHEST;
    double bestScore = highest ? Double.MIN_VALUE : Double.MAX_VALUE;

    for (Map.Entry<Tune, Double> entry : scores.entrySet()) {
      Tune tune = entry.getKey();
      double score = entry.getValue();
      if (highest) {
        if (score > bestScore) {
          best = tune;
          bestScore = score;
        }
      } else {
        if (score < bestScore) {
          best = tune;
          bestScore = score;
        }
      }
    }

    return new HyperparameterTuningResult(best, bestScore, bestScore);
  }
}
