package gg.acai.aurora.hyperparameter;

/**
 * Min and max parameters for hyperparameter tuning.
 *
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

  /**
   * Constructs a new min and max parameters.
   *
   * @param minLearningRate The minimum learning rate.
   * @param maxLearningRate The maximum learning rate.
   * @param learningRateStep The learning rate step.
   * @param minEpochs The minimum epochs.
   * @param maxEpochs The maximum epochs.
   * @param minLayers The minimum layers.
   * @param maxLayers The maximum layers.
   */
  public MinMaxParameters(double minLearningRate, double maxLearningRate, double learningRateStep, int minEpochs, int maxEpochs, int minLayers, int maxLayers) {
    this.minLearningRate = minLearningRate;
    this.maxLearningRate = maxLearningRate;
    this.learningRateStep = learningRateStep;
    this.minEpochs = minEpochs;
    this.maxEpochs = maxEpochs;
    this.minLayers = minLayers;
    this.maxLayers = maxLayers;
  }

  /**
   * Gets the minimum learning rate.
   *
   * @return Returns the minimum learning rate.
   */
  public double minLearningRate() {
    return minLearningRate;
  }

  /**
   * Gets the maximum learning rate.
   *
   * @return Returns the maximum learning rate.
   */
  public double maxLearningRate() {
    return maxLearningRate;
  }

  /**
   * Gets the learning rate step.
   *
   * @return Returns the learning rate step.
   */
  public double learningRateStep() {
    return learningRateStep;
  }

  /**
   * Gets the minimum epochs.
   *
   * @return Returns the minimum epochs.
   */
  public int minEpochs() {
    return minEpochs;
  }

  /**
   * Gets the maximum epochs.
   *
   * @return Returns the maximum epochs.
   */
  public int maxEpochs() {
    return maxEpochs;
  }

  /**
   * Gets the minimum layers.
   *
   * @return Returns the minimum layers.
   */
  public int minLayers() {
    return minLayers;
  }

  /**
   * Gets the maximum layers.
   *
   * @return Returns the maximum layers.
   */
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

    public Builder learningRate(double minLearningRate, double maxLearningRate, double step) {
      this.minLearningRate = minLearningRate;
      this.maxLearningRate = maxLearningRate;
      this.learningRateStep = step;
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
