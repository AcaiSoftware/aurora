package gg.acai.aurora.earlystop;

import gg.acai.aurora.Attribute;

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

  public void tick(Attribute attribute) {
    earlyStops.forEach(earlyStop -> earlyStop.tick(attribute));
  }

  public boolean shouldStop() {
    return earlyStops.stream().anyMatch(EarlyStop::shouldStop);
  }

  public List<EarlyStop> getEarlyStops() {
    return earlyStops;
  }

}
