package gg.acai.aurora.hyperparameter;

/**
 * @author Clouke
 * @since 16.04.2023 21:10
 * © Aurora - All Rights Reserved
 */
public class StandardTune implements Tune {

  private final double learningRate;
  private final int epochs;
  private final int layers;

  public StandardTune(double learningRate, int epochs, int layers) {
    this.learningRate = learningRate;
    this.epochs = epochs;
    this.layers = layers;
  }

  public StandardTune(double learningRate, int epochs) {
    this(learningRate, epochs, -1);
  }

  @Override
  public double learningRate() {
    return learningRate;
  }

  @Override
  public int epochs() {
    return epochs;
  }

  @Override
  public int layers() {
    return layers;
  }

  @Override
  public String toString() {
    return "StandardTune{" +
      "learningRate=" + learningRate +
      ", epochs=" + epochs +
      ", layers=" + layers +
      '}';
  }
}
