package gg.acai.aurora.benchmarks;

import gg.acai.acava.Requisites;
import gg.acai.aurora.model.Trainable;

/**
 * @author Clouke
 * @since 18.04.2023 00:06
 * © Aurora - All Rights Reserved
 */
public class TrainableBenchmarkBuilder {

  private Trainable trainable;
  private int warmupCycles = 10;
  private int benchmarkCycles = 100;
  private double[][] inputs;
  private double[][] outputs;
  private boolean parallel = false;

  public TrainableBenchmarkBuilder trainable(Trainable trainable) {
    this.trainable = trainable;
    return this;
  }

  public TrainableBenchmarkBuilder warmupCycles(int warmupCycles) {
    this.warmupCycles = warmupCycles;
    return this;
  }

  public TrainableBenchmarkBuilder benchmarkCycles(int benchmarkCycles) {
    this.benchmarkCycles = benchmarkCycles;
    return this;
  }

  public TrainableBenchmarkBuilder inputs(double[][] inputs) {
    this.inputs = inputs;
    return this;
  }

  public TrainableBenchmarkBuilder targets(double[][] outputs) {
    this.outputs = outputs;
    return this;
  }

  public TrainableBenchmarkBuilder data(double[][] inputs, double[][] outputs) {
    this.inputs = inputs;
    this.outputs = outputs;
    return this;
  }

  public TrainableBenchmarkBuilder parallel() {
    this.parallel = true;
    return this;
  }

  public Benchmark build() {
    Requisites.requireNonNull(trainable, "trainable cannot be null");
    Requisites.requireNonNull(inputs, "inputs cannot be null");
    Requisites.requireNonNull(outputs, "outputs cannot be null");

    return parallel
      ? new ParallelTrainableBenchmark(trainable, warmupCycles, benchmarkCycles, inputs, outputs)
      : new TrainableBenchmark(trainable, warmupCycles, benchmarkCycles, inputs, outputs);
  }

}
