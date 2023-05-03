package gg.acai.aurora.model;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

/**
 * A shared tool for saving models to a directory.
 *
 * @author Clouke
 * @since 18.04.2023 17:57
 * © Aurora - All Rights Reserved
 */
public class ModelArchive {

  private static final String MODEL_ARCHIVE = "model-archive";
  private static final String DATE_FORMAT = "yyyy-MM-dd_HH-mm";

  /**
   * Saves the model to the model directory.
   *
   * @param model The model to save.
   * @param failure The failure consumer to accept exceptions.
   */
  @SuppressWarnings("ResultOfMethodCallIgnored")
  public static void saveModel(Model model, Consumer<? super Exception> failure) {
    String directory = model.saveDirectoryPath();
    String name = model.nameOpt()
      .orElseGet(() -> {
        String date = new SimpleDateFormat(DATE_FORMAT).format(new Date());
        String type = "unknown";
        if (model instanceof MLContextProvider)
          type = ((MLContextProvider) model)
            .context()
            .toShort();
        return type + MODEL_ARCHIVE + "-" + date;
      });
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
