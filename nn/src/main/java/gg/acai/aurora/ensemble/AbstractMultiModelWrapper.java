package gg.acai.aurora.ensemble;

import gg.acai.aurora.NeuralNetwork;
import gg.acai.aurora.Serializer;
import gg.acai.aurora.model.Model;
import gg.acai.aurora.model.ModelConvertible;
import gg.acai.aurora.model.Predictable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Clouke
 * @since 11.09.2023 00:29
 * © Aurora - All Rights Reserved
 */
public abstract class AbstractMultiModelWrapper implements Model, Predictable {

  private String name = "MultiModelWrapper";
  private String saveDirectory;
  private boolean saveOnClose;
  protected final List<NeuralNetwork> networks;
  protected final double partitionRate;
  protected final boolean partitioning;
  protected int outputSize = -1;
  protected int trainCount = 0;

  public AbstractMultiModelWrapper(List<NeuralNetwork> networks, boolean partitioning, double partitionRate) {
    this.networks = networks;
    this.partitionRate = Math.max(
      0,
      Math.min(1, partitionRate)
    );
    this.partitioning = partitioning;
  }

  public AbstractMultiModelWrapper(String name, List<NeuralNetwork> networks, boolean partitioning, double partitionRate, int outputSize, int trainCount) {
    this(networks, partitioning, partitionRate);
    this.name = name;
    this.outputSize = outputSize;
    this.trainCount = trainCount;
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
    return "1.0.0";
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

  @Override
  public String serialize() {
    JSONObject json = new JSONObject();
    List<Serializer> serializers = networks.stream()
      .map(ModelConvertible::toModel)
      .collect(Collectors.toList());

    JSONArray array = new JSONArray();
    for (Serializer serializer : serializers)
      array.put(serializer.serialize());

    json.put("networks", array);
    json.put("partitionRate", partitionRate);
    json.put("partitioning", partitioning);
    json.put("outputSize", outputSize);
    json.put("trainCount", trainCount);
    json.put("name", name);
    return json.toString();
  }
}
