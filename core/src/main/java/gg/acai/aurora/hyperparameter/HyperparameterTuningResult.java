package gg.acai.aurora.hyperparameter;

/**
 * A hyperparameter tuning result containing the tune, accuracy and score.
 *
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

  /**
   * The winning tune of the tuning.
   *
   * @return Returns the winning tune.
   */
  public Tune tune() {
    return tune;
  }

  /**
   * The accuracy of the winning tune.
   *
   * @return Returns the accuracy of the winning tune.
   */
  public double accuracy() {
    return accuracy;
  }

  /**
   * The score of the winning tune.
   *
   * @return Returns the score of the winning tune.
   */
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
