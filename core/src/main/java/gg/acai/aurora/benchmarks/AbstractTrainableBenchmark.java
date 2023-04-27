package gg.acai.aurora.benchmarks;

import gg.acai.aurora.model.Trainable;

/**
 * @author Clouke
 * @since 18.04.2023 03:32
 * © Aurora - All Rights Reserved
 */
public abstract class AbstractTrainableBenchmark implements Benchmark {

  protected final Trainable trainable;
  protected final int warmupCycles;
  protected final int benchmarkCycles;
  protected final double[][] inputs;
  protected final double[][] outputs;

  public AbstractTrainableBenchmark(Trainable trainable, int warmupCycles, int benchmarkCycles, double[][] inputs, double[][] outputs) {
    this.trainable = trainable;
    this.warmupCycles = warmupCycles;
    this.benchmarkCycles = benchmarkCycles;
    this.inputs = inputs;
    this.outputs = outputs;
  }

}
