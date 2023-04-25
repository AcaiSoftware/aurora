package gg.acai.aurora.model;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

/**
 * @author Clouke
 * @since 18.04.2023 17:57
 * © Aurora - All Rights Reserved
 */
public class ModelArchive {

  private static final String MODEL_ARCHIVE = "model-archive";
  private static final String DATE_FORMAT = "yyyy-MM-dd_HH-mm";

  @SuppressWarnings("ResultOfMethodCallIgnored")
  public static void saveModel(Model model, Consumer<? super Exception> failure) {
    String directory = model.saveDirectoryPath();
    String name = model.nameOpt()
      .orElseGet(() -> {
        String date = new SimpleDateFormat(DATE_FORMAT).format(new Date());
        return MODEL_ARCHIVE + "-" + date;
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
