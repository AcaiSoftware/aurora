package gg.acai.aurora;

import javax.annotation.Nonnull;

/**
 * @author Clouke
 * @since 17.03.2023 01:10
 * © Aurora - All Rights Reserved
 */
public class Tune {

  private final double learningRate;
  private final int epochs;
  private final int hiddenLayers;

  public Tune(double learningRate, int epochs, int hiddenLayers) {
    this.learningRate = learningRate;
    this.epochs = epochs;
    this.hiddenLayers = hiddenLayers;
  }

  public double learningRate() {
    return learningRate;
  }

  public int epochs() {
    return epochs;
  }

  public int hiddenLayers() {
    return hiddenLayers;
  }

  @Override
  public String toString() {
    return "Tune{" +
      "learningRate=" + learningRate +
      ", epochs=" + epochs +
      ", hiddenLayers=" + hiddenLayers +
      '}';
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    temp = Double.doubleToLongBits(learningRate);
    result = (int) (temp ^ (temp >>> 32));
    result = 31 * result + epochs;
    result = 31 * result + hiddenLayers;
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Tune tune = (Tune) o;

    if (Double.compare(tune.learningRate, learningRate) != 0) return false;
    if (epochs != tune.epochs) return false;
    return hiddenLayers == tune.hiddenLayers;
  }

  @Nonnull
  public Tune copy() {
    return new Tune(learningRate, epochs, hiddenLayers);
  }

  public static class Builder {

    private double learningRate;
    private int epochs;
    private int hiddenLayers;

    public Builder learningRate(double learningRate) {
      this.learningRate = learningRate;
      return this;
    }

    public Builder epochs(int epochs) {
      this.epochs = epochs;
      return this;
    }

    public Builder hiddenLayers(int hiddenLayers) {
      this.hiddenLayers = hiddenLayers;
      return this;
    }

    public Tune build() {
      return new Tune(learningRate, epochs, hiddenLayers);
    }

  }
}
