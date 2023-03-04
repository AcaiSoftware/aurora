package gg.acai.aurora.ml.nn;

import gg.acai.acava.Requisites;
import gg.acai.acava.collect.Mutability;
import gg.acai.acava.commons.graph.Graph;
import gg.acai.aurora.Aurora;
import gg.acai.aurora.Compatibility;
import gg.acai.aurora.GsonSpec;
import gg.acai.aurora.exception.IncompatibleModelException;
import gg.acai.aurora.exception.InvalidModelException;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Clouke
 * @since 02.03.2023 13:13
 * © Aurora - All Rights Reserved
 */
public class ModelBuilder {

  private NeuralNetworkModel model;
  private boolean imported;
  private String saveDirectory;
  private String name;
  private boolean saveOnClose;
  private boolean ignoreVersionCheck;
  private Graph<Double> graph;

  public ModelBuilder from(AbstractNeuralNetwork nn) {
    ensureImport();
    this.model = new NeuralNetworkModel(nn.wrap());
    mark();
    return this;
  }

  public ModelBuilder importWithJson(String json) {
    ensureImport();
    try {
      this.model = GsonSpec.standard().fromJson(json, NeuralNetworkModel.class);
      mark();
    } catch (Exception e) {
      throw new InvalidModelException("Could not import model with json!", e);
    }
    return this;
  }

  public ModelBuilder retrieveFromHttp(String url) {
    return retrieveFromHttp(url, null);
  }

  public ModelBuilder retrieveFromHttp(String url, String auth) {
    ensureImport();
    HttpURLConnection connection = null;
    try {
      connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Content-Type", "application/json");
      if (auth != null) {
        connection.setRequestProperty("Authorization", auth);
      }
      connection.setDoOutput(true);
      connection.setDoInput(true);
      connection.connect();
      this.model = GsonSpec.standard().fromJson(new InputStreamReader(connection.getInputStream()), NeuralNetworkModel.class);
      mark();
    } catch (Exception e) {
      throw new InvalidModelException("Could not retrieve model from url " + url + "!", e);
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
    return this;
  }

  public ModelBuilder importFromFile(String path) {
    String fixedPath = path;
    if (!fixedPath.endsWith(".json"))
      fixedPath += ".json";
    return importFromFile(new File(fixedPath));
  }

  public ModelBuilder importFromFile(File file) {
    ensureImport();
    Requisites.checkArgument(file.exists(), "Provided file does not exist: " + file.getName());
    FileReader reader;
    try {
      reader = new FileReader(file);
      this.model = GsonSpec.standard().fromJson(reader, NeuralNetworkModel.class);
      mark();
    } catch (Exception e) {
      throw new InvalidModelException("Could not import model from file " + file.getName() + "!", e);
    }
    return this;
  }

  public ModelBuilder saveDirectoryPath(String saveDirectory) {
    this.saveDirectory = saveDirectory;
    return this;
  }

  public ModelBuilder ignoreVersionCheck() {
    this.ignoreVersionCheck = true;
    return this;
  }

  public ModelBuilder name(String name) {
    this.name = name;
    return this;
  }

  public ModelBuilder saveOnClose() {
    this.saveOnClose = true;
    return this;
  }

  public ModelBuilder graph(Graph<Double> graph) {
    this.graph = graph;
    return this;
  }

  public ModelBuilder createGraph() {
    this.graph = Graph.newBuilder()
      .setMutability(Mutability.MUTABLE)
      .setHeight(15)
      .setMaxDisplayValue(40)
      .build();
    return this;
  }

  public NeuralNetworkModel build() {
    Requisites.checkArgument(imported, "Model has not been imported!");
    if (model == null)
      throw new IllegalStateException();

    if (name != null) model.setModel(name);
    if (saveDirectory != null) model.setSaveDirectory(saveDirectory);
    model.setSaveOnClose(saveOnClose);

    String currentVersion = ignoreVersionCheck ? null : Aurora.version();
    if (currentVersion != null) {
      boolean compat = Compatibility.isCompatible(model.getClass());
      if (!compat) {
        throw new IncompatibleModelException(
          "Incompatible model version, Current version: " + currentVersion + ", Model version: " + model.getVersion() +
          ", Compatible versions: " + Compatibility.getCompatibleVersions(model.getClass()) +
          ". \nUse ModelBuilder#ignoreVersionCheck() to ignore this check."
        );
      }
    }

    return model;
  }

  private void ensureImport() {
    if (imported) throw new IllegalStateException("Model has already been imported!");
  }

  private void mark() {
    imported = true;
  }

}
