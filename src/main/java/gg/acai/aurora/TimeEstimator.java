package gg.acai.aurora;

import gg.acai.acava.collect.pairs.Pairs;

/**
 * @author Clouke
 * @since 10.02.2023 21:20
 * © Acava - All Rights Reserved
 */
public interface TimeEstimator<T> {

  void tick();

  double estimated(T t);

  Pairs<Double, Time> estimateWith(T t);

  enum Time {
    HOURS, MINUTES, SECONDS;

    public char plural() {
      return name().toLowerCase().charAt(0);
    }
  }
}
