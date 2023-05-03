package gg.acai.aurora.benchmarks;

import gg.acai.aurora.model.Trainable;

/**
 * An abstract implementation of a trainable benchmark.
 * <p>
 * Holding common variables and methods for all trainable benchmarks.
 *
 * @author Clouke
 * @since 18.04.2023 03:32
 * © Aurora - All Rights Reserved
 */
public abstract class AbstractTrainableBenchmark implements Benchmark {

  /**
   * The given trainable to benchmark.
   */
  protected final Trainable trainable;
  /**
   * The number of warmup cycles to run before benchmarking.
   */
  protected final int warmupCycles;
  /**
   * The number of benchmark cycles to run.
   */
  protected final int benchmarkCycles;
  /**
   * The inputs to train on.
   */
  protected final double[][] inputs;
  /**
   * The outputs to train on.
   */
  protected final double[][] outputs;

  /**
   * Constructs a new {@link AbstractTrainableBenchmark} with the given parameters.
   *
   * @param trainable The given trainable to benchmark.
   * @param warmupCycles The number of warmup cycles to run before benchmarking.
   * @param benchmarkCycles The number of benchmark cycles to run.
   * @param inputs The inputs to train on.
   * @param outputs The outputs to train on.
   */
  public AbstractTrainableBenchmark(Trainable trainable, int warmupCycles, int benchmarkCycles, double[][] inputs, double[][] outputs) {
    this.trainable = trainable;
    this.warmupCycles = warmupCycles;
    this.benchmarkCycles = benchmarkCycles;
    this.inputs = inputs;
    this.outputs = outputs;
  }

}
