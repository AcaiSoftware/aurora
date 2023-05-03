package gg.acai.aurora.benchmarks;

/**
 * Holds the results of a benchmark.
 *
 * @author Clouke
 * @since 18.04.2023 00:02
 * © Aurora - All Rights Reserved
 */
public interface BenchmarkResult {

  /**
   * Gets the average time per epoch in nanoseconds
   *
   * @return The average time per epoch in nanoseconds
   */
  double averageTimePerEpoch();

  /**
   * Gets the average accuracy over all epochs
   *
   * @return The average accuracy over all epochs
   */
  double averageAccuracy();

  /**
   * Gets the time per sample in nanoseconds
   *
   * @return The time per sample in nanoseconds
   */
  double timePerSample();

  /**
   * Gets the throughput in samples per second
   *
   * @return The throughput in samples per second
   */
  double throughput();

}
