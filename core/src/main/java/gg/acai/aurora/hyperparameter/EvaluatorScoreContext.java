package gg.acai.aurora.hyperparameter;

import java.util.Map;
import java.util.function.Function;

/**
 * A traversal context for the evaluator with a scoring function provider.
 *
 * @author Clouke
 * @since 16.04.2023 21:12
 * © Aurora - All Rights Reserved
 */
public enum EvaluatorScoreContext {
  /**
   * The highest score is the best.
   */
  HIGHEST
    ((scores) -> {
      Tune best = null;
      double bestScore = Double.MIN_VALUE;
      for (Map.Entry<Tune, Double> entry : scores.entrySet()) {
        Tune tune = entry.getKey();
        double score = entry.getValue();
        if (score > bestScore) {
          best = tune;
          bestScore = score;
        }
      }
      return new HyperparameterTuningResult(best, bestScore, bestScore);
    }),
  /**
   * The lowest score is the best.
   */
  LOWEST
    ((scores) -> {
      Tune best = null;
      double bestScore = Double.MAX_VALUE;
      for (Map.Entry<Tune, Double> entry : scores.entrySet()) {
        Tune tune = entry.getKey();
        double score = entry.getValue();
        if (score < bestScore) {
          best = tune;
          bestScore = score;
        }
      }
      return new HyperparameterTuningResult(best, bestScore, bestScore);
    });

  private final Function<Map<Tune, Double>, HyperparameterTuningResult> func;

  EvaluatorScoreContext(Function<Map<Tune, Double>, HyperparameterTuningResult> func) {
    this.func = func;
  }

  public HyperparameterTuningResult findBest(Map<Tune, Double> scores) {
    return func.apply(scores);
  }
}
