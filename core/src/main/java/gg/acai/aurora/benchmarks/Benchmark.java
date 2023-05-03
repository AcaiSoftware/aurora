package gg.acai.aurora.benchmarks;

/**
 * Benchmark for benchmarking models.
 * <p> Example usage: (e.g. benchmarking a neural network)
 * <pre>
 *  {@code
 *      Benchmark benchmark = new TrainableBenchmarkBuilder()
 *       .trainable(new NeuralNetworkBuilder()
 *         .learningRate(0.01)
 *         .epochs(100_000)
 *         .layers(mapper -> mapper
 *           .inputLayers(3)
 *           .hiddenLayers(2)
 *           .outputLayers(1))
 *         .disableStatsPrint()
 *         .build())
 *       .inputs(inputs)
 *       .targets(outputs)
 *       .warmupCycles(20)
 *       .benchmarkCycles(100)
 *       .build();
 *  }
 *  </pre>
 *
 * <p> The benchmark can then be run by calling {@link Benchmark#compose()}
 *  which will return a {@link BenchmarkResult} containing the results of the benchmark.
 *
 * <p>
 *  Benchmarking is thread-safe and supports parallel benchmarking.
 *  by default, the benchmark will run on a single thread, but can be parallelized by calling
 *  {@link TrainableBenchmarkBuilder#parallel()} before building the benchmark.
 * </p>
 *
 * @author Clouke
 * @since 18.04.2023 00:04
 * © Aurora - All Rights Reserved
 */
@FunctionalInterface
public interface Benchmark {
  /**
   * Composes the benchmark and returns the results.
   *
   * @return the results of the benchmark
   */
  BenchmarkResult compose();
}
