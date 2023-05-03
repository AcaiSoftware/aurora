package gg.acai.aurora.benchmarks;

import gg.acai.aurora.model.Trainable;

/**
 * The default implementation of a trainable benchmark.
 *
 * @author Clouke
 * @since 18.04.2023 00:03
 * © Aurora - All Rights Reserved
 */
public class TrainableBenchmark extends AbstractTrainableBenchmark {

  public TrainableBenchmark(Trainable trainable, int warmupCycles, int benchmarkCycles, double[][] inputs, double[][] outputs) {
    super(trainable, warmupCycles, benchmarkCycles, inputs, outputs);
  }

  @Override
  public BenchmarkResult compose() {
    for (int i = 0; i < warmupCycles; i++) {
      trainable.train(inputs, outputs);
    }

    long totalTime = 0L;
    double totalAccuracy = 0.0;
    for (int i = 0; i < benchmarkCycles; i++) {
      long start = System.nanoTime();
      trainable.train(inputs, outputs);
      long end = System.nanoTime();
      totalTime += end - start;
      totalAccuracy += trainable.accuracy();
    }

    int in = inputs.length;
    double averageTimePerEpoch = (double) totalTime / benchmarkCycles;
    double averageAccuracy = totalAccuracy / benchmarkCycles;
    double timePerSample = averageTimePerEpoch / in;
    double throughput = inputs.length * benchmarkCycles / averageTimePerEpoch;
    return new WrappedBenchmarkResult(averageTimePerEpoch, averageAccuracy, timePerSample, throughput);
  }


}
