package gg.acai.aurora.logistic;

import gg.acai.aurora.Aurora;
import gg.acai.aurora.ml.Model;

/**
 * @author Clouke
 * @since 16.04.2023 20:57
 * © Aurora - All Rights Reserved
 */
public class LogisticRegressionModel extends AbstractLogisticRegression implements Model {

  private final String version;
  private boolean saveOnClose;
  private String saveDirectory;

  public LogisticRegressionModel(double[] weights, double[] biases) {
    super(weights, biases);
    this.version = Aurora.version();
  }

  @Override
  public Model setModel(String name) {
    this.name = name;
    return this;
  }

  @Override
  public Model setSaveDirectory(String saveDirectory) {
    this.saveDirectory = saveDirectory;
    return this;
  }

  @Override
  public Model setSaveOnClose(boolean saveOnClose) {
    this.saveOnClose = saveOnClose;
    return this;
  }

  @Override
  public String getVersion() {
    return version;
  }

  @Override
  public void close() {
    if (saveOnClose) {
      save();
    }
  }

  @Override
  public void save() {
  }

  @Override
  public String serialize() {
    return null;
  }
}
