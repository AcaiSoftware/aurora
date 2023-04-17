package gg.acai.aurora.hyperparameter;

import java.util.List;

/**
 * @author Clouke
 * @since 16.04.2023 21:19
 * © Aurora - All Rights Reserved
 */
public class HyperparameterOptions {

  private final TuningEvaluator evaluator;
  private final List<Tune> tunes;
  private final boolean parallel;

  public HyperparameterOptions(TuningEvaluator evaluator, List<Tune> tunes, boolean parallel) {
    this.evaluator = evaluator;
    this.tunes = tunes;
    this.parallel = parallel;
  }

  public TuningEvaluator evaluator() {
    return evaluator;
  }

  public List<Tune> tunes() {
    return tunes;
  }

  public boolean parallel() {
    return parallel;
  }

}
