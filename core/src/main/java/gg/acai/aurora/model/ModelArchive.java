package gg.acai.aurora.model;

import java.io.File;
import java.io.FileWriter;
import java.util.function.Consumer;

/**
 * @author Clouke
 * @since 18.04.2023 17:57
 * © Aurora - All Rights Reserved
 */
public class ModelArchive {

  private static final String MODEL_ARCHIVE = "model-archive";

  @SuppressWarnings("ResultOfMethodCallIgnored")
  public static void saveModel(Model model, Consumer<? super Exception> failure) {
    String directory = model.saveDirectoryPath();
    String name = model.nameOpt().orElse(MODEL_ARCHIVE);
    File file = new File(directory);
    if (!file.exists()) {
      file.mkdirs();
    }

    File modelFile = new File(file, name + ".json");
    try (FileWriter writer = new FileWriter(modelFile)) {
      writer.write(model.serialize());
    } catch (Exception e) {
      failure.accept(e);
    }
  }

}
