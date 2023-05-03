package gg.acai.aurora.earlystop;

import gg.acai.acava.commons.Attributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * A house-holding class for early stoppers.
 *
 * @author Clouke
 * @since 23.04.2023 06:45
 * © Aurora - All Rights Reserved
 */
public final class EarlyStoppers {

  private final List<EarlyStop> earlyStops;

  /**
   * Constructs a new {@link EarlyStoppers} with the given {@link EarlyStop}s.
   *
   * @param earlyStops The given {@link EarlyStop}s.
   */
  public EarlyStoppers(List<EarlyStop> earlyStops) {
    this.earlyStops = earlyStops;
  }

  /**
   * Constructs a new {@link EarlyStoppers} with the given {@link EarlyStop}s.
   *
   * @param earlyStops The given {@link EarlyStop}s.
   */
  public EarlyStoppers(EarlyStop... earlyStops) {
    this(Arrays.asList(earlyStops));
  }

  /**
   * Constructs a new {@link EarlyStoppers} with an empty list of {@link EarlyStop}s.
   */
  public EarlyStoppers() {
    this(new ArrayList<>());
  }

  /**
   * Registers an {@link EarlyStop} to this house-holder.
   *
   * @param earlyStop The early stop to register.
   * @return Returns this house-holder for chaining.
   */
  public EarlyStoppers add(EarlyStop earlyStop) {
    earlyStops.add(earlyStop);
    return this;
  }

  /**
   * Registers a collection of {@link EarlyStop}s to this house-holder.
   *
   * @param earlyStops The early stops to register.
   * @return Returns this house-holder for chaining.
   */
  public EarlyStoppers add(EarlyStop... earlyStops) {
    this.earlyStops.addAll(Arrays.asList(earlyStops));
    return this;
  }

  /**
   * Registers a collection of {@link EarlyStop}s to this house-holder.
   *
   * @param earlyStops The early stops to register.
   * @return Returns this house-holder for chaining.
   */
  public EarlyStoppers add(Collection<? extends EarlyStop> earlyStops) {
    this.earlyStops.addAll(earlyStops);
    return this;
  }

  /**
   * Ticks all the {@link EarlyStop}s in this house-holder.
   *
   * @param attributes The attributes to tick with.
   * @return Returns whether any of the {@link EarlyStop}s have terminated.
   */
  public boolean tick(Attributes attributes) {
    earlyStops.forEach(earlyStop -> earlyStop.tick(attributes));
    return earlyStops.stream().anyMatch(EarlyStop::terminable);
  }

  /**
   * Gets the termination message of the first {@link EarlyStop} that has terminated.
   *
   * @return Returns the termination message of the first {@link EarlyStop} that has terminated.
   */
  public String terminationMessage() {
    return earlyStops.stream()
      .filter(EarlyStop::terminable)
      .findFirst()
      .map(EarlyStop::terminationMessage)
      .orElse("");
  }

  /**
   * Prints the termination message of the first {@link EarlyStop} that has terminated.
   */
  public void printTerminationMessage() {
    System.out.println(terminationMessage());
  }

  /**
   * Gets the {@link EarlyStop}s in this house-holder.
   *
   * @return Returns the {@link EarlyStop}s in this house-holder.
   */
  public List<EarlyStop> getEarlyStops() {
    return earlyStops;
  }

}
