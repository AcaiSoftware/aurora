package gg.acai.aurora;

import gg.acai.acava.Requisites;
import gg.acai.aurora.ml.Model;
import gg.acai.aurora.universal.Compatibility;
import gg.acai.aurora.exception.IncompatibleModelException;
import gg.acai.aurora.exception.InvalidModelException;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A flexible model loader for machine learning models.
 *
 * @author Clouke
 * @since 02.03.2023 13:13
 * © Aurora - All Rights Reserved
 */
public class ModelLoader {

  private Model model;
  private final Class<? extends Model> modelClass;
  private ModelLoaderOptions options;
  private boolean imported;

  public ModelLoader(Class<? extends Model> modelClass) {
    this.modelClass = modelClass;
  }

  public ModelLoader(Supplier<Class<? extends Model>> modelClass) {
    this(modelClass.get());
  }

  public ModelLoader importWithJson(String json) {
    ensureImport();
    try {
      this.model = GsonSpec.standard().fromJson(json, modelClass);
      mark();
    } catch (Exception e) {
      throw new InvalidModelException("Could not import model with json", e);
    }
    return this;
  }

  public ModelLoader retrieveFromHttp(String url) {
    return retrieveFromHttp(url, null);
  }

  public ModelLoader retrieveFromHttp(String url, String auth) {
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
      this.model = GsonSpec.standard().fromJson(new InputStreamReader(connection.getInputStream()), modelClass);
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

  public ModelLoader options(Consumer<ModelLoaderOptionsBuilder> options) {
    ModelLoaderOptionsBuilder builder = new ModelLoaderOptionsBuilder();
    options.accept(builder);
    this.options = builder.build();
    return this;
  }

  public ModelLoader importFromFile(String path) {
    String fixedPath = path;
    if (!fixedPath.endsWith(".json"))
      fixedPath += ".json";
    return importFromFile(new File(fixedPath));
  }

  public ModelLoader importFromFile(File file) {
    ensureImport();
    Requisites.checkArgument(file.exists(), "Provided file does not exist: " + file.getName());
    try (FileReader reader = new FileReader(file)) {
      this.model = GsonSpec.standard().fromJson(reader, modelClass);
      mark();
    } catch (Exception e) {
      throw new InvalidModelException("Could not import model from file " + file.getName(), e);
    }
    return this;
  }

  @SuppressWarnings("unchecked")
  public <M extends Model> M build() {
    Requisites.checkArgument(imported, "model has not been imported!");

    boolean ignoreVersions = false;
    if (options != null) {
      model.setModel(options.name())
        .setSaveDirectory(options.saveDirectory())
        .setSaveOnClose(options.saving());
      ignoreVersions = options.ignored();
    }

    String currentVersion = ignoreVersions ? null : Aurora.version();
    if (currentVersion != null) {
      boolean compat = Compatibility.isCompatible(model.getClass());
      if (!compat) {
        throw new IncompatibleModelException(
          "Incompatible model version, Current version: " + currentVersion + ", Model version: " + model.getVersion() +
          ", Compatible versions: " + Compatibility.getCompatibleVersions(model.getClass()) +
          ". \nUse ModelLoader#ignoreVersionCheck() to ignore this check."
        );
      }
    }

    try {
      return (M) model;
    } catch (Exception e) {
      throw new InvalidModelException("Could not cast model to " + modelClass.getSimpleName(), e);
    }
  }

  private void ensureImport() {
    if (imported) throw new IllegalStateException("Model has already been imported");
  }

  private void mark() {
    imported = true;
  }

}
