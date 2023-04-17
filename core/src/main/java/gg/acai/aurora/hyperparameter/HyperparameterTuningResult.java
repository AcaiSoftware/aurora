package gg.acai.aurora.hyperparameter;

/**
 * @author Clouke
 * @since 16.04.2023 22:28
 * © Aurora - All Rights Reserved
 */
public class HyperparameterTuningResult {

  private final Tune tune;
  private final double accuracy;
  private final double score;

  public HyperparameterTuningResult(Tune tune, double accuracy, double score) {
    this.tune = tune;
    this.accuracy = accuracy;
    this.score = score;
  }

  public Tune tune() {
    return tune;
  }

  public double accuracy() {
    return accuracy;
  }

  public double score() {
    return score;
  }

  @Override
  public String toString() {
    return "HyperparameterTuningResult{" +
      "tune=" + tune +
      ", accuracy=" + accuracy +
      ", score=" + score +
      '}';
  }
}
