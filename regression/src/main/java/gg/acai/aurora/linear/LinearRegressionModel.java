package gg.acai.aurora.linear;

import gg.acai.aurora.Aurora;
import gg.acai.aurora.GsonSpec;
import gg.acai.aurora.model.Model;

/**
 * @author Clouke
 * @since 03.05.2023 11:38
 * © Aurora - All Rights Reserved
 */
public class LinearRegressionModel extends AbstractLinearRegression implements Model {

  private String name;
  private transient String saveDirectory;
  private boolean saveOnClose;
  private final String version = Aurora.version();

  public LinearRegressionModel(double intercept, double slope) {
    super(intercept, slope);
  }

  public LinearRegressionModel() {
    // empty constructor
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
