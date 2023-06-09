package gg.acai.aurora;

import gg.acai.aurora.sets.DataSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Clouke
 * @since 19.05.2023 21:12
 * © Aurora - All Rights Reserved
 */
public class NeuralNetworkTFBuilder {

  protected String pythonPath;
  protected String optimizer = "adam";
  protected final List<Dense> layers = new ArrayList<>();
  protected int epochs;
  protected double learningRate;
  protected DataSet dataSet;

  public NeuralNetworkTFBuilder optimizer(String optimizer) {
    this.optimizer = optimizer;
    return this;
  }

  public NeuralNetworkTFBuilder epochs(int epochs) {
    this.epochs = epochs;
    return this;
  }

  public NeuralNetworkTFBuilder learningRate(double learningRate) {
    this.learningRate = learningRate;
    return this;
  }

  public NeuralNetworkTFBuilder withDataSet(DataSet dataSet) {
    this.dataSet = dataSet;
    return this;
  }

  public NeuralNetworkTFBuilder scriptPath(String pythonPath) {
    this.pythonPath = pythonPath;
    return this;
  }

  public NeuralNetworkTFBuilder addLayer(Dense layer) {
    layers.add(layer);
    return this;
  }

  public NeuralNetworkTFBuilder addLayers(Dense... layers) {
    for (Dense layer : layers) {
      addLayer(layer);
    }
    return this;
  }

  public NeuralNetworkTFBuilder addLayers(Collection<Dense> layers) {
    this.layers.addAll(layers);
    return this;
  }

  public NeuralNetworkTF build() {
    return new NeuralNetworkTF(this);
  }
}
