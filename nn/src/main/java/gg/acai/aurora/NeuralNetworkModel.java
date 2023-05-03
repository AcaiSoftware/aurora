package gg.acai.aurora;

import gg.acai.aurora.model.Model;

/**
 * A storable and loadable neural network model.
 *
 * @author Clouke
 * @since 27.02.2023 12:24
 * © Aurora - All Rights Reserved
 */
public class NeuralNetworkModel extends AbstractNeuralNetwork implements Model {

  protected String version = Aurora.version();
  private transient String saveDirectory;
  private transient boolean saveOnClose;

  public NeuralNetworkModel(String s, double[][] weights_input_to_hidden, double[][] weights_hidden_to_output, double[] biases_hidden, double[] biases_output) {
    super(weights_input_to_hidden, weights_hidden_to_output, biases_hidden, biases_output);
    super.name = s;
  }

  public NeuralNetworkModel(double[][] weights_input_to_hidden, double[][] weights_hidden_to_output, double[] biases_hidden, double[] biases_output) {
    super(weights_input_to_hidden, weights_hidden_to_output, biases_hidden, biases_output);
  }

  public NeuralNetworkModel(WrappedNeuralNetwork wrapper) {
    super(wrapper.weights_input_to_hidden(), wrapper.weights_hidden_to_output(), wrapper.biases_hidden(), wrapper.biases_output());
  }

  @Override
  public Model saveDirectoryPath(String saveDirectory) {
    this.saveDirectory = saveDirectory;
    return this;
  }

  @Override
  public Model name(String model) {
    super.name = model;
    return this;
  }

  @Override
  public Model saveOnClose(boolean saveOnClose) {
    this.saveOnClose = saveOnClose;
    return this;
  }

  @Override
  public String serialize() {
    this.version = Aurora.version(); // update version for serialization
    return GsonSpec.standard().toJson(this);
  }

  @Override
  public String toString() {
    return serialize();
  }

  @Override
  public String version() {
    return version;
  }

  @Override
  public String saveDirectoryPath() {
    return saveDirectory;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public void close() {
    if (saveOnClose) save();
  }
}
