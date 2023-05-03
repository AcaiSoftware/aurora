package gg.acai.aurora.earlystop;

import gg.acai.acava.commons.Attributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Clouke
 * @since 23.04.2023 06:45
 * © Aurora - All Rights Reserved
 */
public final class EarlyStoppers {

  private final List<EarlyStop> earlyStops;

  public EarlyStoppers(List<EarlyStop> earlyStops) {
    this.earlyStops = earlyStops;
  }

  public EarlyStoppers(EarlyStop... earlyStops) {
    this(Arrays.asList(earlyStops));
  }

  public EarlyStoppers() {
    this(new ArrayList<>());
  }

  public EarlyStoppers add(EarlyStop earlyStop) {
    earlyStops.add(earlyStop);
    return this;
  }

  public EarlyStoppers add(EarlyStop... earlyStops) {
    this.earlyStops.addAll(Arrays.asList(earlyStops));
    return this;
  }

  public EarlyStoppers add(Collection<? extends EarlyStop> earlyStops) {
    this.earlyStops.addAll(earlyStops);
    return this;
  }

  public boolean tick(Attributes attributes) {
    earlyStops.forEach(earlyStop -> earlyStop.tick(attributes));
    return earlyStops.stream().anyMatch(EarlyStop::terminable);
  }

  public String terminationMessage() {
    return earlyStops.stream()
      .filter(EarlyStop::terminable)
      .findFirst()
      .map(EarlyStop::terminationMessage)
      .orElse("");
  }

  public void printTerminationMessage() {
    System.out.println(terminationMessage());
  }

  public List<EarlyStop> getEarlyStops() {
    return earlyStops;
  }

}
