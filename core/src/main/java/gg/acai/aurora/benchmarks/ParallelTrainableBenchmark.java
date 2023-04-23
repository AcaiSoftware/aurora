package gg.acai.aurora.benchmarks;

import gg.acai.aurora.ml.Trainable;

import java.util.stream.IntStream;

/**
 * @author Clouke
 * @since 18.04.2023 01:52
 * © Aurora - All Rights Reserved
 */
public class ParallelTrainableBenchmark extends AbstractTrainableBenchmark {

  private long totalTime = 0L;
  private double totalAccuracy = 0.0;

  public ParallelTrainableBenchmark(Trainable trainable, int warmupCycles, int benchmarkCycles, double[][] inputs, double[][] outputs) {
    super(trainable, warmupCycles, benchmarkCycles, inputs, outputs);
  }

  @Override
  public BenchmarkResult compose() {
    for (int i = 0; i < warmupCycles; i++) {
      trainable.train(inputs, outputs);
    }

    IntStream.range(0, benchmarkCycles)
      .parallel()
      .forEach(i -> {
        long start = System.nanoTime();
        trainable.train(inputs, outputs);
        long end = System.nanoTime();
        totalTime += end - start;
        totalAccuracy += trainable.accuracy();
      });

    int in = inputs.length;
    double averageTimePerEpoch = (double) totalTime / benchmarkCycles;
    double averageAccuracy = totalAccuracy / benchmarkCycles;
    double timePerSample = averageTimePerEpoch / in;
    double throughput = inputs.length * benchmarkCycles / averageTimePerEpoch;
    return new WrappedBenchmarkResult(averageTimePerEpoch, averageAccuracy, timePerSample, throughput);
  }
}
