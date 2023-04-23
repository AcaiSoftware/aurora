package gg.acai.aurora.benchmarks;

/**
 * @author Clouke
 * @since 18.04.2023 00:04
 * © Aurora - All Rights Reserved
 */
public class WrappedBenchmarkResult implements BenchmarkResult {

  private final double averageTimePerEpoch;
  private final double averageAccuracy;
  private final double timePerSample;
  private final double throughput;

  public WrappedBenchmarkResult(double averageTimePerEpoch, double averageAccuracy, double timePerSample, double throughput) {
    this.averageTimePerEpoch = averageTimePerEpoch;
    this.averageAccuracy = averageAccuracy;
    this.timePerSample = timePerSample;
    this.throughput = throughput;
  }

  @Override
  public double averageTimePerEpoch() {
    return averageTimePerEpoch;
  }

  @Override
  public double averageAccuracy() {
    return averageAccuracy;
  }

  @Override
  public double timePerSample() {
    return timePerSample;
  }

  @Override
  public double throughput() {
    return throughput;
  }

  @Override
  public String toString() {
    return "WrappedBenchmarkResult{" +
      "averageTimePerEpoch=" + averageTimePerEpoch +
      ", averageAccuracy=" + averageAccuracy +
      ", timePerSample=" + timePerSample +
      ", throughput=" + throughput +
      '}';
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    temp = Double.doubleToLongBits(averageTimePerEpoch);
    result = (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(averageAccuracy);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(timePerSample);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(throughput);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    WrappedBenchmarkResult that = (WrappedBenchmarkResult) o;
    return Double.compare(that.averageTimePerEpoch, averageTimePerEpoch) == 0 &&
      Double.compare(that.averageAccuracy, averageAccuracy) == 0 &&
      Double.compare(that.timePerSample, timePerSample) == 0 &&
      Double.compare(that.throughput, throughput) == 0;
  }
}
