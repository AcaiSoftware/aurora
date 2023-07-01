package gg.acai.aurora.model;

import gg.acai.acava.Requisites;
import gg.acai.aurora.GsonSpec;
import gg.acai.aurora.exception.InvalidModelException;
import gg.acai.aurora.publics.io.bin.BinaryFileIO;
import gg.acai.aurora.publics.io.bin.BinaryWrapper;

import java.io.*;
import java.net.URL;
import java.util.function.Consumer;

/**
 * A flexible model loader for machine learning models.
 * <p> Example Usage:
 * In this example we will load a neural network model.
 * <pre>{@code
 *  NeuralNetworkModel model = null;
 *  try (ModelLoader loader = new ModelLoader(new File("model.json"))) { // your model file
 *    model = loader.load(NeuralNetworkModel.class);
 *  }
 * }</pre>
 *
 * <p>Loading a model with a remapper from a URL:
 * <pre>{@code
 *  NeuralNetworkModel model = null;
 *  try (ModelLoader loader = new ModelLoader(new URL("my_model_url"))) { // https://my_model_url.com/model.json
 *    model = loader.load(NeuralNetworkModel.class, remapper -> remapper
 *     .name("renamed_model")
 *     .enableAutoSave());
 *  } catch (MalformedURLException e) {
 *    throw new RuntimeException(e);
 *  }
 * }</pre>
 * <strong>Note:</strong> Remapping is optional - and will not be applied if the model is already saved
 * <p>Loading a model from a json string:
 * <pre>{@code
 *   NeuralNetworkModel model = null;
 *   try (ModelLoader loader = new ModelLoader("serialized_json_here")) {
 *     model = loader.load(NeuralNetworkModel.class);
 *   }
 * }</pre>
 *
 *
 * <p>This model loader can be used for any {@link Model} implementation
 * such as
 * <ul>
 *   <li>NeuralNetworkModel</li>
 *   <li>LinearRegressionModel</li>
 *   <li>LogisticRegressionModel</li>
 *   <li>Cluster Models</li>
 *   <li>Custom Models</li>
 *   <li>And more...</li>
 * </ul>
 *
 * <p><strong>Note:</strong> Closing this model loader is not necessary, but is <strong>recommended</strong> for large model imports.
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

  public ModelLoader(BinaryWrapper path) {
    Requisites.requireNonNull(path, "path cannot be null");
    Requisites.requireNonNull(path.composed(), "path.composed() cannot be null");
    try {
      this.json = BinaryFileIO.loadFromBin(path.composed());
    } catch (IOException e) {
      throw new RuntimeException("Could not import model from binary file " + path.composed());
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
