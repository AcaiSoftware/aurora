package gg.acai.aurora.benchmarks;

/**
 * @author Clouke
 * @since 18.04.2023 00:02
 * © Aurora - All Rights Reserved
 */
public interface BenchmarkResult {

  double averageTimePerEpoch();

  double averageAccuracy();

  double timePerSample();

  double throughput();

}
