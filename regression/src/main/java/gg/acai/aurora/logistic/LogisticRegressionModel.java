package gg.acai.aurora.logistic;

import gg.acai.aurora.Aurora;
import gg.acai.aurora.GsonSpec;
import gg.acai.aurora.model.Model;

/**
 * A storable & loadable logistic regression model implementation.
 *
 * @author Clouke
 * @since 16.04.2023 20:57
 * © Aurora - All Rights Reserved
 */
public class LogisticRegressionModel extends AbstractLogisticRegression implements Model {

  private final String version;
  private transient boolean saveOnClose;
  private transient String saveDirectory;

  public LogisticRegressionModel(double[] weights, double[] biases) {
    super(weights, biases);
    this.version = Aurora.version();
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
    return GsonSpec.standard().toJson(this);
  }
}
