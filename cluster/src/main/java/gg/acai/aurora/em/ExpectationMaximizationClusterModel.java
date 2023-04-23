package gg.acai.aurora.em;

import gg.acai.aurora.GsonSpec;
import gg.acai.aurora.model.Model;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

/**
 * A model implementation for the EM algorithm that stores the clusters of the data points.
 *
 * @author Clouke
 * @since 18.04.2023 04:06
 * © Aurora - All Rights Reserved
 */
public class ExpectationMaximizationClusterModel implements Model, ExpectationMaximization, Predicate<Integer> {

  private String name = "ExpectationMaximizationClusterModel";
  private transient String saveDirectory;
  private boolean close;
  private final List<Integer> cluster;

  public ExpectationMaximizationClusterModel(List<Integer> cluster) {
    this.cluster = cluster;
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
    this.close = saveOnClose;
    return this;
  }

  @Override @Nullable
  public String version() {
    return "ignore";
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
    if (close) {
      save();
    }
  }

  @Override
  public String serialize() {
    return GsonSpec.standard().toJson(this);
  }

  @Override
  public List<Integer> cluster() {
    return cluster;
  }

  @Override
  public boolean test(Integer integer) {
    return cluster.contains(integer);
  }
}
