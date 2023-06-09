package gg.acai.aurora.model;

import gg.acai.acava.collect.pairs.ImmutablePair;
import gg.acai.acava.collect.pairs.Pair;
import gg.acai.aurora.TimeEstimator;
import gg.acai.aurora.publics.Ticker;

/**
 * An iterable time estimator which estimates the time remaining for a given number of epochs.
 * <p>
 * Tick by each iteration to feed the estimator with data.
 *
 * @author Clouke
 * @author Jonathan
 * @since 09.02.2023 17:10
 * © Aurora - All Rights Reserved
 */
public final class IterableTimeEstimator implements TimeEstimator<Integer> {

  private static final Ticker<Long> TICKER = Ticker.systemNanos();

  private long tick = -1;
  private final int maxEpochs;
  private double average = -1.0;
  private int iteration;

  public IterableTimeEstimator(int maxEpochs) {
    this.maxEpochs = maxEpochs;
  }

  @Override
  public void tick() {
    tick = TICKER.read();
  }

  @Override
  public double estimated(Integer iteration) {
    if (tick == -1)
      return 0.0;

    long now = TICKER.read();
    long diff = now - tick;

    double latest = (diff * (maxEpochs - iteration)) / 1e9;
    average = average == -1.0 ? latest : average * (5.0 / 6.0) + latest * (1.0 / 6.0);

    return Math.round(average * 10.0) / 10.0;
  }

  @Override
  public Pair<Double, Time> estimateWith(Integer integer) {
    double estimated = estimated(integer);
    if (estimated < 60) return new ImmutablePair<>(estimated, Time.SECONDS);
    else if (estimated < 3600) return new ImmutablePair<>(estimated / 60.0, Time.MINUTES);
    return new ImmutablePair<>(estimated / 3600.0, Time.HOURS);
  }

  @Override
  public void setIteration(int iteration) {
    this.iteration = iteration;
  }

  @Override
  public String toString() {
    double estimated = estimated(iteration);
    if (estimated < 60) return estimated + "s";
    else if (estimated < 3600) {
      int minutes = (int) (estimated / 60.0);
      int seconds = (int) (estimated % 60.0);
      return minutes + "m, " + seconds + "s";
    } else {
      int hours = (int) (estimated / 3600.0);
      int minutes = (int) ((estimated % 3600.0) / 60.0);
      int seconds = (int) ((estimated % 3600.0) % 60.0);
      return hours + "h, " + minutes + "m, " + seconds + "s";
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (!(obj instanceof IterableTimeEstimator)) return false;
    IterableTimeEstimator other = (IterableTimeEstimator) obj;
    return other.tick == tick;
  }

  @Override
  public int hashCode() {
    return (int) (tick ^ (tick >>> 32));
  }
}