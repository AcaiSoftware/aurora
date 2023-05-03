package gg.acai.aurora;

import gg.acai.acava.collect.pairs.Pair;

/**
 * A time estimator for estimating the time remaining.
 *
 * @author Clouke
 * @since 10.02.2023 21:20
 * © Acava - All Rights Reserved
 */
public interface TimeEstimator<T> extends Runnable {

  /**
   * Ticks this time estimator.
   */
  void tick();

  @Override
  default void run() {
    tick();
  }

  /**
   * Gets the estimated time remaining.
   *
   * @param t The object to estimate the time remaining for.
   * @return Returns the estimated time remaining.
   */
  double estimated(T t);

  Pair<Double, Time> estimateWith(T t);

  enum Time {
    HOURS, MINUTES, SECONDS;

    public char plural() {
      return name().toLowerCase().charAt(0);
    }
  }
}
