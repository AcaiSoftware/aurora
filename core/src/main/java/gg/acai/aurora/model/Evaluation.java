package gg.acai.aurora.model;

/**
 * @author Clouke
 * @since 27.04.2023 21:04
 * © Aurora - All Rights Reserved
 */
public interface Evaluation {

  /**
   * Computes the metrics of this model.
   */
  void evaluate();

  /**
   * Gets the accuracy of this model.
   *
   * @return the accuracy of this model, if computed
   */
  double accuracy();

  /**
   * Gets the precision of this model.
   *
   * @return the precision of this model, if computed
   */
  double precision();

  /**
   * Gets the recall of this model.
   *
   * @return the recall of this model, if computed
   */
  double recall();

  /**
   * Gets the F1 score of this model.
   *
   * @return the F1 score of this model, if computed
   */
  double f1Score();

  /**
   * Gets a summary of this model's metrics.
   *
   * @return a summary of this model's metrics, if computed
   */
  String summary();

  /**
   * Prints a summary of this model's metrics.
   */
  default void printSummary() {
    System.out.println(summary());
  }

}
