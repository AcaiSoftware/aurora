package gg.acai.aurora.ml;

import gg.acai.acava.collect.pairs.ImmutablePair;
import gg.acai.acava.collect.pairs.Pairs;
import gg.acai.aurora.TimeEstimator;

/**
 * @author Clouke
 * @author Jonathan
 * @since 09.02.2023 17:10
 * © Aurora - All Rights Reserved
 */
public final class TrainingTimeEstimator implements TimeEstimator<Integer> {

  private long ticker = -1;
  private final int maxEpochs;
  private double average = -1.0;

  public TrainingTimeEstimator(int maxEpochs) {
    this.maxEpochs = maxEpochs;
  }

  @Override
  public void tick() {
    ticker = System.nanoTime();
  }

  @Override
  public double estimated(Integer currentEpoch) {
    if (ticker == -1) return 0.0;

    long now = System.nanoTime();
    long diff = now - ticker;

    double latest = (diff * (maxEpochs - currentEpoch)) / 1e9;
    average = average == -1.0 ? latest : average * (5.0 / 6.0) + latest * (1.0 / 6.0);

    return Math.round(average * 10.0) / 10.0;
  }

  @Override
  public Pairs<Double, Time> estimateWith(Integer integer) {
    double estimated = estimated(integer);
    if (estimated < 60) return new ImmutablePair<>(estimated, Time.SECONDS);
    else if (estimated < 3600) return new ImmutablePair<>(estimated / 60.0, Time.MINUTES);
    return new ImmutablePair<>(estimated / 3600.0, Time.HOURS);
  }
}