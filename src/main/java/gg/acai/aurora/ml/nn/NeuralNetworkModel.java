package gg.acai.aurora.ml.nn;

import gg.acai.acava.io.Closeable;
import gg.acai.aurora.Aurora;
import gg.acai.aurora.GsonSpec;
import gg.acai.aurora.SavePoint;
import gg.acai.aurora.Serializer;

import java.io.File;
import java.io.FileWriter;

/**
 * @author Clouke
 * @since 27.02.2023 12:24
 * © Aurora - All Rights Reserved
 */
public class NeuralNetworkModel extends AbstractNeuralNetwork implements Serializer, SavePoint, Closeable {

  protected String version = Aurora.version();
  private transient String saveDirectory;
  private transient boolean saveOnClose;

  public NeuralNetworkModel(String s, double[][] weights_input_to_hidden, double[][] weights_hidden_to_output, double[] biases_hidden, double[] biases_output) {
    super(weights_input_to_hidden, weights_hidden_to_output, biases_hidden, biases_output);
    super.model = s;
  }

  public NeuralNetworkModel(double[][] weights_input_to_hidden, double[][] weights_hidden_to_output, double[] biases_hidden, double[] biases_output) {
    super(weights_input_to_hidden, weights_hidden_to_output, biases_hidden, biases_output);
  }

  public NeuralNetworkModel(WrappedNeuralNetwork wrapper) {
    super(wrapper.weights_input_to_hidden(), wrapper.weights_hidden_to_output(), wrapper.biases_hidden(), wrapper.biases_output());
  }

  public NeuralNetworkModel setSaveDirectory(String saveDirectory) {
    this.saveDirectory = saveDirectory;
    return this;
  }

  public NeuralNetworkModel setModel(String model) {
    super.model = model;
    return this;
  }

  public NeuralNetworkModel setSaveOnClose(boolean saveOnClose) {
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

  public String getVersion() {
    return version;
  }

  @Override
  @SuppressWarnings("ResultOfMethodCallIgnored")
  public void save() {
    String path = saveDirectory == null ? "" : saveDirectory + File.separator;
    String name = path + (model == null || model.isEmpty() ? "model" : model) + ".json";
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
      System.out.println("Saved model to " + name);
    }
  }

  @Override
  public void close() {
    if (saveOnClose)
      save();
  }
}
