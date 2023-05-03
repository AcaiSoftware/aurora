package gg.acai.aurora.benchmarks;

import gg.acai.acava.Requisites;
import gg.acai.aurora.model.Trainable;

/**
 * A builder for {@link TrainableBenchmark}s.
 *
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

  /**
   * Applies the required trainable for this benchmark session.
   *
   * @param trainable The given trainable.
   * @return Returns this builder for chaining.
   */
  public TrainableBenchmarkBuilder trainable(Trainable trainable) {
    this.trainable = trainable;
    return this;
  }

  /**
   * Applies the optional number of warmup cycles for this benchmark session.
   *
   * @param warmupCycles The given number of warmup cycles.
   * @return Returns this builder for chaining.
   */
  public TrainableBenchmarkBuilder warmupCycles(int warmupCycles) {
    this.warmupCycles = warmupCycles;
    return this;
  }

  /**
   * Applies the optional number of benchmark cycles for this benchmark session.
   *
   * @param benchmarkCycles The given number of benchmark cycles.
   * @return Returns this builder for chaining.
   */
  public TrainableBenchmarkBuilder benchmarkCycles(int benchmarkCycles) {
    this.benchmarkCycles = benchmarkCycles;
    return this;
  }

  /**
   * Applies the required inputs for this benchmark session.
   *
   * @param inputs The given inputs.
   * @return Returns this builder for chaining.
   */
  public TrainableBenchmarkBuilder inputs(double[][] inputs) {
    this.inputs = inputs;
    return this;
  }

  /**
   * Applies the required outputs for this benchmark session.
   *
   * @param outputs The given outputs.
   * @return Returns this builder for chaining.
   */
  public TrainableBenchmarkBuilder targets(double[][] outputs) {
    this.outputs = outputs;
    return this;
  }

  /**
   * Applies the required inputs and outputs for this benchmark session.
   *
   * @param inputs The given inputs.
   * @param outputs The given outputs.
   * @return Returns this builder for chaining.
   */
  public TrainableBenchmarkBuilder data(double[][] inputs, double[][] outputs) {
    this.inputs = inputs;
    this.outputs = outputs;
    return this;
  }

  /**
   * Applies the optional parallel flag for this benchmark session.
   *
   * @return Returns this builder for chaining.
   */
  public TrainableBenchmarkBuilder parallel() {
    this.parallel = true;
    return this;
  }

  /**
   * Builds the {@link Benchmark} with the given parameters.
   *
   * @return Returns the built {@link Benchmark}.
   */
  public Benchmark build() {
    Requisites.requireNonNull(trainable, "trainable cannot be null");
    Requisites.requireNonNull(inputs, "inputs cannot be null");
    Requisites.requireNonNull(outputs, "outputs cannot be null");

    return parallel
      ? new ParallelTrainableBenchmark(trainable, warmupCycles, benchmarkCycles, inputs, outputs)
      : new TrainableBenchmark(trainable, warmupCycles, benchmarkCycles, inputs, outputs);
  }

}
