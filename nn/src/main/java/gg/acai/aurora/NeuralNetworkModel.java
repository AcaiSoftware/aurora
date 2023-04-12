package gg.acai.aurora;

import gg.acai.aurora.ml.Model;

import java.io.File;
import java.io.FileWriter;

/**
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
  public Model setSaveDirectory(String saveDirectory) {
    this.saveDirectory = saveDirectory;
    return this;
  }

  @Override
  public Model setModel(String model) {
    super.name = model;
    return this;
  }

  @Override
  public Model setSaveOnClose(boolean saveOnClose) {
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
  public String getVersion() {
    return version;
  }

  @Override
  @SuppressWarnings("ResultOfMethodCallIgnored")
  public void save() {
    long start = System.currentTimeMillis();
    String path = saveDirectory == null ? "" : saveDirectory + File.separator;
    String name = path + (this.name == null || this.name.isEmpty()
      ? "model-" + System.currentTimeMillis()
      : this.name) + ".json";
    File file = new File(name);
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    try {
      FileWriter writer = new FileWriter(file);
      String json = serialize();
      writer.write(json);
      writer.flush();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      System.out.println("Saved model to " + name + " in " + (System.currentTimeMillis() - start) + "ms");
    }
  }

  @Override
  public void close() {
    if (saveOnClose)
      save();
  }
}
