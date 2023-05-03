package gg.acai.aurora.model;

import gg.acai.acava.Requisites;
import gg.acai.aurora.GsonSpec;
import gg.acai.aurora.exception.InvalidModelException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.function.Consumer;

/**
 * A flexible model loader for machine learning models.
 *
 * @author Clouke
 * @since 02.03.2023 13:13
 * © Aurora - All Rights Reserved
 */
public class ModelLoader implements AutoCloseable {

  private String json;

  public ModelLoader(File file) throws InvalidModelException {
    try (FileReader reader = new FileReader(file)) {
      StringBuilder builder = new StringBuilder();
      BufferedReader bufferedReader = new BufferedReader(reader);
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        builder.append(line);
      }
      this.json = builder.toString();
    } catch (Exception e) {
      throw new InvalidModelException("Could not import model from file " + file.getName(), e);
    }
  }

  public ModelLoader(URL url) throws InvalidModelException {
    try (InputStreamReader reader = new InputStreamReader(url.openStream())) {
      StringBuilder builder = new StringBuilder();
      BufferedReader bufferedReader = new BufferedReader(reader);
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        builder.append(line);
      }
      this.json = builder.toString();
    } catch (Exception e) {
      throw new InvalidModelException("Could not import model from url " + url.toString(), e);
    }
  }

  public ModelLoader(String json) {
    Requisites.requireNonNull(json, "json cannot be null");
    this.json = json;
  }

  @SuppressWarnings("unchecked")
  public <M extends Model> M load(Class<? extends Model> modelClass, Consumer<ModelRemapperBuilder> remapper) {
    Requisites.requireNonNull(modelClass, "modelClass cannot be null");
    Model model = GsonSpec
      .standard()
      .fromJson(json, modelClass);

    Requisites.requireNonNull(model, "Could not import model with json");
    if (remapper != null) {
      ModelRemapperBuilder builder = new ModelRemapperBuilder();
      remapper.accept(builder);
      ModelRemapper opts = builder.build();
      model.name(opts.name())
        .saveOnClose(opts.saving())
        .saveDirectoryPath(opts.saveDirectory());
    }

    return (M) model;
  }

  public <M extends Model> M load(Class<? extends Model> modelClass) {
    return load(modelClass, null);
  }

  @Override
  public void close() {
    this.json = null; // Call GC
  }
}
