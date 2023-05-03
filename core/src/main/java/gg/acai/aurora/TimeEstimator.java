package gg.acai.aurora;

import gg.acai.acava.collect.pairs.Pair;

/**
 * @author Clouke
 * @since 10.02.2023 21:20
 * © Acava - All Rights Reserved
 */
public interface TimeEstimator<T> extends Runnable {

  void tick();

  @Override
  default void run() {
    tick();
  }

  double estimated(T t);

  Pair<Double, Time> estimateWith(T t);

  enum Time {
    HOURS, MINUTES, SECONDS;

    public char plural() {
      return name().toLowerCase().charAt(0);
    }
  }
}
