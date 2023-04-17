package gg.acai.aurora.hyperparameter;

/**
 * @author Clouke
 * @since 17.04.2023 01:57
 * © Aurora - All Rights Reserved
 */
public class MinMaxParameters {

  public static final MinMaxParameters DEFAULT = new MinMaxParameters(0.01, 0.1, 0.01, 100, 1000, 1, 3);

  public static Builder builder() {
    return new Builder();
  }

  private final double minLearningRate;
  private final double maxLearningRate;
  private final double learningRateStep;

  private final int minEpochs;
  private final int maxEpochs;

  private final int minLayers;
  private final int maxLayers;

  public MinMaxParameters(double minLearningRate, double maxLearningRate, double learningRateStep, int minEpochs, int maxEpochs, int minLayers, int maxLayers) {
    this.minLearningRate = minLearningRate;
    this.maxLearningRate = maxLearningRate;
    this.learningRateStep = learningRateStep;
    this.minEpochs = minEpochs;
    this.maxEpochs = maxEpochs;
    this.minLayers = minLayers;
    this.maxLayers = maxLayers;
  }

  public double minLearningRate() {
    return minLearningRate;
  }

  public double maxLearningRate() {
    return maxLearningRate;
  }

  public double learningRateStep() {
    return learningRateStep;
  }

  public int minEpochs() {
    return minEpochs;
  }

  public int maxEpochs() {
    return maxEpochs;
  }

  public int minLayers() {
    return minLayers;
  }

  public int maxLayers() {
    return maxLayers;
  }

  @Override
  public String toString() {
    return "MinMaxParameters{" +
      "minLearningRate=" + minLearningRate +
      ", maxLearningRate=" + maxLearningRate +
      ", learningRateStep=" + learningRateStep +
      ", minEpochs=" + minEpochs +
      ", maxEpochs=" + maxEpochs +
      ", minLayers=" + minLayers +
      ", maxLayers=" + maxLayers +
      '}';
  }

  public static class Builder {

    private double minLearningRate = 0.01;
    private double maxLearningRate = 0.1;
    private double learningRateStep = 0.01;

    private int minEpochs = 100;
    private int maxEpochs = 1000;

    private int minLayers = 1;
    private int maxLayers = 3;

    public Builder learningRate(double minLearningRate, double maxLearningRate, double learningRateStep) {
      this.minLearningRate = minLearningRate;
      this.maxLearningRate = maxLearningRate;
      this.learningRateStep = learningRateStep;
      return this;
    }

    public Builder epochs(int minEpochs, int maxEpochs) {
      this.minEpochs = minEpochs;
      this.maxEpochs = maxEpochs;
      return this;
    }

    public Builder layers(int minLayers, int maxLayers) {
      this.minLayers = minLayers;
      this.maxLayers = maxLayers;
      return this;
    }

    public MinMaxParameters build() {
      return new MinMaxParameters(minLearningRate, maxLearningRate, learningRateStep, minEpochs, maxEpochs, minLayers, maxLayers);
    }
  }

}
