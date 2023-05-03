package gg.acai.aurora.lvq;

import gg.acai.acava.commons.Attributes;
import gg.acai.aurora.Aurora;
import gg.acai.aurora.GsonSpec;
import gg.acai.aurora.model.Model;

/**
 * A storable model implementation of an LVQ neural network.
 *
 * @author Clouke
 * @since 28.04.2023 20:09
 * © Aurora - All Rights Reserved
 */
public class LVQNeuralNetworkModel extends AbstractLVQNeuralNetwork implements Model {

  private String name;
  private String version;
  private transient String saveDirectory;
  private transient boolean saveOnClose;

  public LVQNeuralNetworkModel(double[][] weights, int numInputs, int numOutputs) {
    super(weights, numInputs, numOutputs);
  }

  @Override
  public Model name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public Model saveDirectoryPath(String saveDirectory) {
    this.saveDirectory = saveDirectory;
    return this;
  }

  @Override
  public Model saveOnClose(boolean saveOnClose) {
    this.saveOnClose = saveOnClose;
    return this;
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
    if (saveOnClose) {
      save();
    }
  }

  @Override
  public String serialize() {
    this.version = Aurora.version(); // update version for serialization
    return GsonSpec.standard().toJson(this);
  }

  @Override
  public Attributes attributes() {
    throw new UnsupportedOperationException("No attributes used in model");
  }
}
