package gg.acai.aurora;

import com.google.gson.Gson;

import java.util.List;

/**
 * @author Clouke
 * @since 19.05.2023 23:47
 * © Aurora - All Rights Reserved
 */
public class Parameters {

  private final String pythonPath;
  private final double[][] inputs;
  private final double[][] outputs;
  private final int epochs;
  private final double learningRate;
  private final String optimizer;
  private final List<Dense> layers;

  public Parameters(String pythonPath, double[][] inputs, double[][] outputs, int epochs, double learningRate, String optimizer, List<Dense> layers) {
    this.pythonPath = pythonPath;
    this.inputs = inputs;
    this.outputs = outputs;
    this.epochs = epochs;
    this.learningRate = learningRate;
    this.optimizer = optimizer;
    this.layers = layers;
  }

  public String pythonPath() {
    return pythonPath;
  }

  public double[][] inputs() {
    return inputs;
  }

  public double[][] outputs() {
    return outputs;
  }

  public int epochs() {
    return epochs;
  }

  public double learningRate() {
    return learningRate;
  }

  public String optimizer() {
    return optimizer;
  }

  public List<Dense> layers() {
    return layers;
  }

  @Override
  public String toString() {
    Gson gson = GsonSpec.standard();
    return gson.toJson(this);
  }
}
