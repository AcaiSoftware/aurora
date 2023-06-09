package gg.acai.aurora;

/**
 * @author Clouke
 * @since 19.05.2023 21:29
 * © Aurora - All Rights Reserved
 */
public class Dense {

  private final int units;
  private String activation = "sigmoid";
  private String kernelInitializer = "glorot_uniform";
  private String biasInitializer = "zeros";

  public Dense(int units, String activation, String kernelInitializer, String biasInitializer) {
    this.units = units;
    this.activation = activation;
    this.kernelInitializer = kernelInitializer;
    this.biasInitializer = biasInitializer;
  }

  public Dense(int units, String activation, String kernelInitializer) {
    this.units = units;
    this.activation = activation;
    this.kernelInitializer = kernelInitializer;
  }

  public Dense(int units, String activation) {
    this.units = units;
    this.activation = activation;
  }

  public Dense(int units) {
    this.units = units;
  }

  public int units() {
    return units;
  }

  public String activation() {
    return activation;
  }

  public String kernelInitializer() {
    return kernelInitializer;
  }

  public String biasInitializer() {
    return biasInitializer;
  }

  @Override
  public String toString() {
    return "Dense{" +
      "units=" + units +
      ", activation='" + activation + '\'' +
      ", kernelInitializer='" + kernelInitializer + '\'' +
      ", biasInitializer='" + biasInitializer + '\'' +
      '}';
  }
}
